package org.eclipse.imp.leg.parser;

import lpg.runtime.*;
import java.util.*;
import org.eclipse.imp.parser.ILexer;

public class LEGLexer implements RuleAction, ILexer
{
    private LEGLexerLpgLexStream lexStream;
    
    private static ParseTable prs = new LEGLexerprs();
    public ParseTable getParseTable() { return prs; }

    private LexParser lexParser = new LexParser();
    public LexParser getParser() { return lexParser; }

    public int getToken(int i) { return lexParser.getToken(i); }
    public int getRhsFirstTokenIndex(int i) { return lexParser.getFirstToken(i); }
    public int getRhsLastTokenIndex(int i) { return lexParser.getLastToken(i); }

    public int getLeftSpan() { return lexParser.getToken(1); }
    public int getRightSpan() { return lexParser.getLastToken(); }

    public void resetKeywordLexer()
    {
        if (kwLexer == null)
              this.kwLexer = new LEGKWLexer(lexStream.getInputChars(), LEGParsersym.TK_IDENTIFIER);
        else this.kwLexer.setInputChars(lexStream.getInputChars());
    }

    public void reset(String filename, int tab) throws java.io.IOException
    {
        lexStream = new LEGLexerLpgLexStream(filename, tab);
        lexParser.reset((ILexStream) lexStream, prs, (RuleAction) this);
        resetKeywordLexer();
    }

    public void reset(char[] input_chars, String filename)
    {
        reset(input_chars, filename, 1);
    }
    
    public void reset(char[] input_chars, String filename, int tab)
    {
        lexStream = new LEGLexerLpgLexStream(input_chars, filename, tab);
        lexParser.reset((ILexStream) lexStream, prs, (RuleAction) this);
        resetKeywordLexer();
    }
    
    public LEGLexer(String filename, int tab) throws java.io.IOException 
    {
        reset(filename, tab);
    }

    public LEGLexer(char[] input_chars, String filename, int tab)
    {
        reset(input_chars, filename, tab);
    }

    public LEGLexer(char[] input_chars, String filename)
    {
        reset(input_chars, filename, 1);
    }

    public LEGLexer() {}

    public ILexStream getILexStream() { return lexStream; }

    /**
     * @deprecated replaced by {@link #getILexStream()}
     */
    public ILexStream getLexStream() { return lexStream; }

    private void initializeLexer(IPrsStream prsStream, int start_offset, int end_offset)
    {
        if (lexStream.getInputChars() == null)
            throw new NullPointerException("LexStream was not initialized");
        lexStream.setPrsStream(prsStream);
        prsStream.makeToken(start_offset, end_offset, 0); // Token list must start with a bad token
    }

    private void addEOF(IPrsStream prsStream, int end_offset)
    {
        prsStream.makeToken(end_offset, end_offset, LEGParsersym.TK_EOF_TOKEN); // and end with the end of file token
        prsStream.setStreamLength(prsStream.getSize());
    }

    public void lexer(IPrsStream prsStream)
    {
        lexer(null, prsStream);
    }
    
    public void lexer(Monitor monitor, IPrsStream prsStream)
    {
        initializeLexer(prsStream, 0, -1);
        lexParser.parseCharacters(monitor);  // Lex the input characters
        addEOF(prsStream, lexStream.getStreamIndex());
    }

    public void lexer(IPrsStream prsStream, int start_offset, int end_offset)
    {
        lexer(null, prsStream, start_offset, end_offset);
    }
    
    public void lexer(Monitor monitor, IPrsStream prsStream, int start_offset, int end_offset)
    {
        if (start_offset <= 1)
             initializeLexer(prsStream, 0, -1);
        else initializeLexer(prsStream, start_offset - 1, start_offset - 1);

        lexParser.parseCharacters(monitor, start_offset, end_offset);

        addEOF(prsStream, (end_offset >= lexStream.getStreamIndex() ? lexStream.getStreamIndex() : end_offset + 1));
    }

    /**
     * If a parse stream was not passed to this Lexical analyser then we
     * simply report a lexical error. Otherwise, we produce a bad token.
     */
    public void reportLexicalError(int startLoc, int endLoc) {
        IPrsStream prs_stream = lexStream.getPrsStream();
        if (prs_stream == null)
            lexStream.reportLexicalError(startLoc, endLoc);
        else {
            //
            // Remove any token that may have been processed that fall in the
            // range of the lexical error... then add one error token that spans
            // the error range.
            //
            for (int i = prs_stream.getSize() - 1; i > 0; i--) {
                if (prs_stream.getStartOffset(i) >= startLoc)
                     prs_stream.removeLastToken();
                else break;
            }
            prs_stream.makeToken(startLoc, endLoc, 0); // add an error token to the prsStream
        }        
    }

    //
    // The Lexer contains an array of characters as the input stream to be parsed.
    // There are methods to retrieve and classify characters.
    // The lexparser "token" is implemented simply as the index of the next character in the array.
    // The Lexer extends the abstract class LpgLexStream with an implementation of the abstract
    // method getKind.  The template defines the Lexer class and the lexer() method.
    // A driver creates the action class, "Lexer", passing an Option object to the constructor.
    //
    LEGKWLexer kwLexer;
    boolean printTokens;
    private final static int ECLIPSE_TAB_VALUE = 4;

    public int [] getKeywordKinds() { return kwLexer.getKeywordKinds(); }

    public LEGLexer(String filename) throws java.io.IOException
    {
        this(filename, ECLIPSE_TAB_VALUE);
        this.kwLexer = new LEGKWLexer(lexStream.getInputChars(), LEGParsersym.TK_IDENTIFIER);
    }

    /**
     * @deprecated function replaced by {@link #reset(char [] content, String filename)}
     */
    public void initialize(char [] content, String filename)
    {
        reset(content, filename);
    }
    
    final void makeToken(int left_token, int right_token, int kind)
    {
        lexStream.makeToken(left_token, right_token, kind);
    }
    
    final void makeToken(int kind)
    {
        int startOffset = getLeftSpan(),
            endOffset = getRightSpan();
        lexStream.makeToken(startOffset, endOffset, kind);
        if (printTokens) printValue(startOffset, endOffset);
    }

    final void makeComment(int kind)
    {
        int startOffset = getLeftSpan(),
            endOffset = getRightSpan();
        lexStream.getIPrsStream().makeAdjunct(startOffset, endOffset, kind);
    }

    final void skipToken()
    {
        if (printTokens) printValue(getLeftSpan(), getRightSpan());
    }
    
    final void checkForKeyWord()
    {
        int startOffset = getLeftSpan(),
            endOffset = getRightSpan(),
            kwKind = kwLexer.lexer(startOffset, endOffset);
        lexStream.makeToken(startOffset, endOffset, kwKind);
        if (printTokens) printValue(startOffset, endOffset);
    }
    
    //
    // This flavor of checkForKeyWord is necessary when the default kind
    // (which is returned when the keyword filter doesn't match) is something
    // other than _IDENTIFIER.
    //
    final void checkForKeyWord(int defaultKind)
    {
        int startOffset = getLeftSpan(),
            endOffset = getRightSpan(),
            kwKind = kwLexer.lexer(startOffset, endOffset);
        if (kwKind == LEGParsersym.TK_IDENTIFIER)
            kwKind = defaultKind;
        lexStream.makeToken(startOffset, endOffset, kwKind);
        if (printTokens) printValue(startOffset, endOffset);
    }
    
    final void printValue(int startOffset, int endOffset)
    {
        String s = new String(lexStream.getInputChars(), startOffset, endOffset - startOffset + 1);
        System.out.print(s);
    }

    //
    //
    //
    static class LEGLexerLpgLexStream extends LpgLexStream
    {
    public final static int tokenKind[] =
    {
        LEGLexersym.Char_CtlCharNotWS,    // 000    0x00
        LEGLexersym.Char_CtlCharNotWS,    // 001    0x01
        LEGLexersym.Char_CtlCharNotWS,    // 002    0x02
        LEGLexersym.Char_CtlCharNotWS,    // 003    0x03
        LEGLexersym.Char_CtlCharNotWS,    // 004    0x04
        LEGLexersym.Char_CtlCharNotWS,    // 005    0x05
        LEGLexersym.Char_CtlCharNotWS,    // 006    0x06
        LEGLexersym.Char_CtlCharNotWS,    // 007    0x07
        LEGLexersym.Char_CtlCharNotWS,    // 008    0x08
        LEGLexersym.Char_HT,              // 009    0x09
        LEGLexersym.Char_LF,              // 010    0x0A
        LEGLexersym.Char_CtlCharNotWS,    // 011    0x0B
        LEGLexersym.Char_FF,              // 012    0x0C
        LEGLexersym.Char_CR,              // 013    0x0D
        LEGLexersym.Char_CtlCharNotWS,    // 014    0x0E
        LEGLexersym.Char_CtlCharNotWS,    // 015    0x0F
        LEGLexersym.Char_CtlCharNotWS,    // 016    0x10
        LEGLexersym.Char_CtlCharNotWS,    // 017    0x11
        LEGLexersym.Char_CtlCharNotWS,    // 018    0x12
        LEGLexersym.Char_CtlCharNotWS,    // 019    0x13
        LEGLexersym.Char_CtlCharNotWS,    // 020    0x14
        LEGLexersym.Char_CtlCharNotWS,    // 021    0x15
        LEGLexersym.Char_CtlCharNotWS,    // 022    0x16
        LEGLexersym.Char_CtlCharNotWS,    // 023    0x17
        LEGLexersym.Char_CtlCharNotWS,    // 024    0x18
        LEGLexersym.Char_CtlCharNotWS,    // 025    0x19
        LEGLexersym.Char_CtlCharNotWS,    // 026    0x1A
        LEGLexersym.Char_CtlCharNotWS,    // 027    0x1B
        LEGLexersym.Char_CtlCharNotWS,    // 028    0x1C
        LEGLexersym.Char_CtlCharNotWS,    // 029    0x1D
        LEGLexersym.Char_CtlCharNotWS,    // 030    0x1E
        LEGLexersym.Char_CtlCharNotWS,    // 031    0x1F
        LEGLexersym.Char_Space,           // 032    0x20
        LEGLexersym.Char_Exclamation,     // 033    0x21
        LEGLexersym.Char_DoubleQuote,     // 034    0x22
        LEGLexersym.Char_Sharp,           // 035    0x23
        LEGLexersym.Char_DollarSign,      // 036    0x24
        LEGLexersym.Char_Percent,         // 037    0x25
        LEGLexersym.Char_Ampersand,       // 038    0x26
        LEGLexersym.Char_SingleQuote,     // 039    0x27
        LEGLexersym.Char_LeftParen,       // 040    0x28
        LEGLexersym.Char_RightParen,      // 041    0x29
        LEGLexersym.Char_Star,            // 042    0x2A
        LEGLexersym.Char_Plus,            // 043    0x2B
        LEGLexersym.Char_Comma,           // 044    0x2C
        LEGLexersym.Char_Minus,           // 045    0x2D
        LEGLexersym.Char_Dot,             // 046    0x2E
        LEGLexersym.Char_Slash,           // 047    0x2F
        LEGLexersym.Char_0,               // 048    0x30
        LEGLexersym.Char_1,               // 049    0x31
        LEGLexersym.Char_2,               // 050    0x32
        LEGLexersym.Char_3,               // 051    0x33
        LEGLexersym.Char_4,               // 052    0x34
        LEGLexersym.Char_5,               // 053    0x35
        LEGLexersym.Char_6,               // 054    0x36
        LEGLexersym.Char_7,               // 055    0x37
        LEGLexersym.Char_8,               // 056    0x38
        LEGLexersym.Char_9,               // 057    0x39
        LEGLexersym.Char_Colon,           // 058    0x3A
        LEGLexersym.Char_SemiColon,       // 059    0x3B
        LEGLexersym.Char_LessThan,        // 060    0x3C
        LEGLexersym.Char_Equal,           // 061    0x3D
        LEGLexersym.Char_GreaterThan,     // 062    0x3E
        LEGLexersym.Char_QuestionMark,    // 063    0x3F
        LEGLexersym.Char_AtSign,          // 064    0x40
        LEGLexersym.Char_A,               // 065    0x41
        LEGLexersym.Char_B,               // 066    0x42
        LEGLexersym.Char_C,               // 067    0x43
        LEGLexersym.Char_D,               // 068    0x44
        LEGLexersym.Char_E,               // 069    0x45
        LEGLexersym.Char_F,               // 070    0x46
        LEGLexersym.Char_G,               // 071    0x47
        LEGLexersym.Char_H,               // 072    0x48
        LEGLexersym.Char_I,               // 073    0x49
        LEGLexersym.Char_J,               // 074    0x4A
        LEGLexersym.Char_K,               // 075    0x4B
        LEGLexersym.Char_L,               // 076    0x4C
        LEGLexersym.Char_M,               // 077    0x4D
        LEGLexersym.Char_N,               // 078    0x4E
        LEGLexersym.Char_O,               // 079    0x4F
        LEGLexersym.Char_P,               // 080    0x50
        LEGLexersym.Char_Q,               // 081    0x51
        LEGLexersym.Char_R,               // 082    0x52
        LEGLexersym.Char_S,               // 083    0x53
        LEGLexersym.Char_T,               // 084    0x54
        LEGLexersym.Char_U,               // 085    0x55
        LEGLexersym.Char_V,               // 086    0x56
        LEGLexersym.Char_W,               // 087    0x57
        LEGLexersym.Char_X,               // 088    0x58
        LEGLexersym.Char_Y,               // 089    0x59
        LEGLexersym.Char_Z,               // 090    0x5A
        LEGLexersym.Char_LeftBracket,     // 091    0x5B
        LEGLexersym.Char_BackSlash,       // 092    0x5C
        LEGLexersym.Char_RightBracket,    // 093    0x5D
        LEGLexersym.Char_Caret,           // 094    0x5E
        LEGLexersym.Char__,               // 095    0x5F
        LEGLexersym.Char_BackQuote,       // 096    0x60
        LEGLexersym.Char_a,               // 097    0x61
        LEGLexersym.Char_b,               // 098    0x62
        LEGLexersym.Char_c,               // 099    0x63
        LEGLexersym.Char_d,               // 100    0x64
        LEGLexersym.Char_e,               // 101    0x65
        LEGLexersym.Char_f,               // 102    0x66
        LEGLexersym.Char_g,               // 103    0x67
        LEGLexersym.Char_h,               // 104    0x68
        LEGLexersym.Char_i,               // 105    0x69
        LEGLexersym.Char_j,               // 106    0x6A
        LEGLexersym.Char_k,               // 107    0x6B
        LEGLexersym.Char_l,               // 108    0x6C
        LEGLexersym.Char_m,               // 109    0x6D
        LEGLexersym.Char_n,               // 110    0x6E
        LEGLexersym.Char_o,               // 111    0x6F
        LEGLexersym.Char_p,               // 112    0x70
        LEGLexersym.Char_q,               // 113    0x71
        LEGLexersym.Char_r,               // 114    0x72
        LEGLexersym.Char_s,               // 115    0x73
        LEGLexersym.Char_t,               // 116    0x74
        LEGLexersym.Char_u,               // 117    0x75
        LEGLexersym.Char_v,               // 118    0x76
        LEGLexersym.Char_w,               // 119    0x77
        LEGLexersym.Char_x,               // 120    0x78
        LEGLexersym.Char_y,               // 121    0x79
        LEGLexersym.Char_z,               // 122    0x7A
        LEGLexersym.Char_LeftBrace,       // 123    0x7B
        LEGLexersym.Char_VerticalBar,     // 124    0x7C
        LEGLexersym.Char_RightBrace,      // 125    0x7D
        LEGLexersym.Char_Tilde,           // 126    0x7E

        LEGLexersym.Char_AfterASCII,      // for all chars in range 128..65534
        LEGLexersym.Char_EOF              // for '\uffff' or 65535 
    };
            
    public final int getKind(int i)  // Classify character at ith location
    {
        int c = (i >= getStreamLength() ? '\uffff' : getCharValue(i));
        return (c < 128 // ASCII Character
                  ? tokenKind[c]
                  : c == '\uffff'
                       ? LEGLexersym.Char_EOF
                       : LEGLexersym.Char_AfterASCII);
    }

    public String[] orderedExportedSymbols() { return LEGParsersym.orderedTerminalSymbols; }

    public LEGLexerLpgLexStream(String filename, int tab) throws java.io.IOException
    {
        super(filename, tab);
    }

    public LEGLexerLpgLexStream(char[] input_chars, String filename, int tab)
    {
        super(input_chars, filename, tab);
    }

    public LEGLexerLpgLexStream(char[] input_chars, String filename)
    {
        super(input_chars, filename, 1);
    }
    }

    public void ruleAction(int ruleNumber)
    {
        switch(ruleNumber)
        {

            //
            // Rule 1:  Token ::= identifier
            //
            case 1: { 
                checkForKeyWord();
            break;
            }
            //
            // Rule 2:  Token ::= number
            //
            case 2: { 
                makeToken(LEGParsersym.TK_NUMBER);
            break;
            }
            //
            // Rule 3:  Token ::= DoubleLiteral
            //
            case 3: { 
                makeToken(LEGParsersym.TK_DoubleLiteral);
            break;
            }
            //
            // Rule 4:  Token ::= white
            //
            case 4: { 
                skipToken();
            break;
            }
            //
            // Rule 5:  Token ::= slc
            //
            case 5: { 
                makeComment(LEGParsersym.TK_SINGLE_LINE_COMMENT);
            break;
            }
            //
            // Rule 6:  Token ::= ;
            //
            case 6: { 
                makeToken(LEGParsersym.TK_SEMICOLON);
            break;
            }
            //
            // Rule 7:  Token ::= ,
            //
            case 7: { 
                makeToken(LEGParsersym.TK_COMMA);
            break;
            }
            //
            // Rule 8:  Token ::= +
            //
            case 8: { 
                makeToken(LEGParsersym.TK_PLUS);
            break;
            }
            //
            // Rule 9:  Token ::= -
            //
            case 9: { 
                makeToken(LEGParsersym.TK_MINUS);
            break;
            }
            //
            // Rule 10:  Token ::= =
            //
            case 10: { 
                makeToken(LEGParsersym.TK_ASSIGN);
            break;
            }
            //
            // Rule 11:  Token ::= (
            //
            case 11: { 
                makeToken(LEGParsersym.TK_LEFTPAREN);
            break;
            }
            //
            // Rule 12:  Token ::= )
            //
            case 12: { 
                makeToken(LEGParsersym.TK_RIGHTPAREN);
            break;
            }
            //
            // Rule 13:  Token ::= {
            //
            case 13: { 
                makeToken(LEGParsersym.TK_LEFTBRACE);
            break;
            }
            //
            // Rule 14:  Token ::= }
            //
            case 14: { 
                makeToken(LEGParsersym.TK_RIGHTBRACE);
            break;
            }
            //
            // Rule 15:  Token ::= *
            //
            case 15: { 
                makeToken(LEGParsersym.TK_TIMES);
            break;
            }
            //
            // Rule 16:  Token ::= /
            //
            case 16: { 
                makeToken(LEGParsersym.TK_DIVIDE);
            break;
            }
            //
            // Rule 17:  Token ::= >
            //
            case 17: { 
                makeToken(LEGParsersym.TK_GREATER);
            break;
            }
            //
            // Rule 18:  Token ::= <
            //
            case 18: { 
                makeToken(LEGParsersym.TK_LESS);
            break;
            }
            //
            // Rule 19:  Token ::= = =
            //
            case 19: { 
                makeToken(LEGParsersym.TK_EQUAL);
            break;
            }
            //
            // Rule 20:  Token ::= ! =
            //
            case 20: { 
                makeToken(LEGParsersym.TK_NOTEQUAL);
            break;
            }
    
            default:
                break;
        }
        return;
    }
}

