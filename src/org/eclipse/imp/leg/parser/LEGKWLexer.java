package org.eclipse.imp.leg.parser;

import lpg.runtime.*;

public class LEGKWLexer extends LEGKWLexerprs
{
    private char[] inputChars;
    private final int keywordKind[] = new int[10 + 1];

    public int[] getKeywordKinds() { return keywordKind; }

    public int lexer(int curtok, int lasttok)
    {
        int current_kind = getKind(inputChars[curtok]),
            act;

        for (act = tAction(START_STATE, current_kind);
             act > NUM_RULES && act < ACCEPT_ACTION;
             act = tAction(act, current_kind))
        {
            curtok++;
            current_kind = (curtok > lasttok
                                   ? LEGKWLexersym.Char_EOF
                                   : getKind(inputChars[curtok]));
        }

        if (act > ERROR_ACTION)
        {
            curtok++;
            act -= ERROR_ACTION;
        }

        return keywordKind[act == ERROR_ACTION  || curtok <= lasttok ? 0 : act];
    }

    public void setInputChars(char[] inputChars) { this.inputChars = inputChars; }


    final static int tokenKind[] = new int[128];
    static
    {
        tokenKind['$'] = LEGKWLexersym.Char_DollarSign;
        tokenKind['%'] = LEGKWLexersym.Char_Percent;
        tokenKind['_'] = LEGKWLexersym.Char__;
        
        tokenKind['a'] = LEGKWLexersym.Char_a;
        tokenKind['b'] = LEGKWLexersym.Char_b;
        tokenKind['c'] = LEGKWLexersym.Char_c;
        tokenKind['d'] = LEGKWLexersym.Char_d;
        tokenKind['e'] = LEGKWLexersym.Char_e;
        tokenKind['f'] = LEGKWLexersym.Char_f;
        tokenKind['g'] = LEGKWLexersym.Char_g;
        tokenKind['h'] = LEGKWLexersym.Char_h;
        tokenKind['i'] = LEGKWLexersym.Char_i;
        tokenKind['j'] = LEGKWLexersym.Char_j;
        tokenKind['k'] = LEGKWLexersym.Char_k;
        tokenKind['l'] = LEGKWLexersym.Char_l;
        tokenKind['m'] = LEGKWLexersym.Char_m;
        tokenKind['n'] = LEGKWLexersym.Char_n;
        tokenKind['o'] = LEGKWLexersym.Char_o;
        tokenKind['p'] = LEGKWLexersym.Char_p;
        tokenKind['q'] = LEGKWLexersym.Char_q;
        tokenKind['r'] = LEGKWLexersym.Char_r;
        tokenKind['s'] = LEGKWLexersym.Char_s;
        tokenKind['t'] = LEGKWLexersym.Char_t;
        tokenKind['u'] = LEGKWLexersym.Char_u;
        tokenKind['v'] = LEGKWLexersym.Char_v;
        tokenKind['w'] = LEGKWLexersym.Char_w;
        tokenKind['x'] = LEGKWLexersym.Char_x;
        tokenKind['y'] = LEGKWLexersym.Char_y;
        tokenKind['z'] = LEGKWLexersym.Char_z;
    };

    final int getKind(int c)
    {
        return ((c & 0xFFFFFF80) == 0 /* 0 <= c < 128? */ ? tokenKind[c] : 0);
    }


    public LEGKWLexer(char[] inputChars, int identifierKind)
    {
        this.inputChars = inputChars;
        keywordKind[0] = identifierKind;

        //
        // Rule 1:  Keyword ::= b o o l e a n
        //
        keywordKind[1] = (LEGParsersym.TK_boolean);

        //
        // Rule 2:  Keyword ::= d o u b l e
        //
        keywordKind[2] = (LEGParsersym.TK_double);

        //
        // Rule 3:  Keyword ::= e l s e
        //
        keywordKind[3] = (LEGParsersym.TK_else);

        //
        // Rule 4:  Keyword ::= f a l s e
        //
        keywordKind[4] = (LEGParsersym.TK_false);

        //
        // Rule 5:  Keyword ::= i f
        //
        keywordKind[5] = (LEGParsersym.TK_if);

        //
        // Rule 6:  Keyword ::= i n t
        //
        keywordKind[6] = (LEGParsersym.TK_int);

        //
        // Rule 7:  Keyword ::= v o i d
        //
        keywordKind[7] = (LEGParsersym.TK_void);

        //
        // Rule 8:  Keyword ::= r e t u r n
        //
        keywordKind[8] = (LEGParsersym.TK_return);

        //
        // Rule 9:  Keyword ::= t r u e
        //
        keywordKind[9] = (LEGParsersym.TK_true);

        //
        // Rule 10:  Keyword ::= w h i l e
        //
        keywordKind[10] = (LEGParsersym.TK_while);

        for (int i = 0; i < keywordKind.length; i++)
        {
            if (keywordKind[i] == 0)
                keywordKind[i] = identifierKind;
        }
    }
}

