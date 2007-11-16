%options package=org.eclipse.imp.leg.parser
%options template=LexerTemplate.gi
%options filter=LEGKWLexer.gi
--
-- This is just a sample lexer and not a real lexer for LEG
--

%Globals
    /.import java.util.*;
    import org.eclipse.imp.parser.ILexer;
    ./
%End

%Define
    $additional_interfaces /., ILexer./
    $kw_lexer_class /.$LEGKWLexer./
%End

%Include
    LexerBasicMap.gi
%End

%Export
    --
    -- List all the token types the lexer will directly process
    -- and export to the parser. If a keyword lexer is used as
    -- a filter for this lexer, it may export a set of keywords
    -- that will also be passed along to the parser.
    -- 
    -- For example:
    --
        SINGLE_LINE_COMMENT
        IDENTIFIER 
        NUMBER
        DoubleLiteral
        COMMA
        SEMICOLON
        PLUS
        MINUS
        TIMES
        DIVIDE
        GREATER
        LESS
        EQUAL
        NOTEQUAL
        ASSIGN
        LEFTPAREN
        RIGHTPAREN
        LEFTBRACE
        RIGHTBRACE
        METAVARIABLE_functionDeclaration
        METAVARIABLE_expression
        METAVARIABLE_expressions
        METAVARIABLE_statement
        METAVARIABLE_statements
        METAVARIABLE_parameters
        METAVARIABLE_Type
        METAVARIABLE_identifier
	METAVARIABLE_parameterList
	METAVARIABLE_declaration
	METAVARIABLE_functionDeclarations
	METAVARIABLE_term
%End

%Terminals
    CtlCharNotWS

    LF   CR   HT   FF

    a    b    c    d    e    f    g    h    i    j    k    l    m
    n    o    p    q    r    s    t    u    v    w    x    y    z
    _

    A    B    C    D    E    F    G    H    I    J    K    L    M
    N    O    P    Q    R    S    T    U    V    W    X    Y    Z

    0    1    2    3    4    5    6    7    8    9

    AfterASCII   ::= '\u0080..\ufffe'
    Space        ::= ' '
    LF           ::= NewLine
    CR           ::= Return
    HT           ::= HorizontalTab
    FF           ::= FormFeed
    DoubleQuote  ::= '"'
    SingleQuote  ::= "'"
    Percent      ::= '%'
    VerticalBar  ::= '|'
    Exclamation  ::= '!'
    AtSign       ::= '@'
    BackQuote    ::= '`'
    Tilde        ::= '~'
    Sharp        ::= '#'
    DollarSign   ::= '$'
    Ampersand    ::= '&'
    Caret        ::= '^'
    Colon        ::= ':'
    SemiColon    ::= ';'
    BackSlash    ::= '\'
    LeftBrace    ::= '{'
    RightBrace   ::= '}'
    LeftBracket  ::= '['
    RightBracket ::= ']'
    QuestionMark ::= '?'
    Comma        ::= ','
    Dot          ::= '.'
    LessThan     ::= '<'
    GreaterThan  ::= '>'
    Plus         ::= '+'
    Minus        ::= '-'
    Slash        ::= '/'
    Star         ::= '*'
    LeftParen    ::= '('
    RightParen   ::= ')'
    Equal        ::= '='
%End

%Start
    Token
%End


%Rules
    Token ::= '$' 'f' 'u' 'n' 'c' number
         /.
      $BeginJava
         makeToken($_METAVARIABLE_functionDeclaration);
      $EndJava
     ./
        
    Token ::= '$' 'f' 'u' 'n' 'c' 's' number
         /.
      $BeginJava
         makeToken($_METAVARIABLE_functionDeclarations);
      $EndJava
     ./
     
    Token ::= '$' 'e' 'x' 'p' 'r' number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_expression);
      $EndJava
     ./
     
      Token ::= '$' 's' 't' 'a' 't' number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_statement);
      $EndJava
     ./
     
       Token ::= '$' 's' 't' 'a' 't' 's' number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_statements);
      $EndJava
     ./
     
     Token ::= '$' 'e' 'x' 'p' 'r' 's' number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_expressions);
      $EndJava
     ./
     
       Token ::= '$' 't' 'e' 'r' 'm'  number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_term);
      $EndJava
     ./
     
     Token ::= '$' 'i' 'd' number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_identifier);
      $EndJava
     ./
     
       Token ::= '$' 't' 'y' 'p' 'e' number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_Type);
      $EndJava
     ./
     
        Token ::= '$' 'p' 'a' 'r' 'a' 'm' 's' number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_parameters);
      $EndJava
     ./
     
          Token ::= '$' 'p' 'a' 'r' 'a' 'm' 'l' 'i' 's' 't'  number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_parameterList);
      $EndJava
     ./
     
       
          Token ::= '$' 'd' 'e' 'c' 'l' number
       /.
      $BeginJava
         makeToken($_METAVARIABLE_declaration);
      $EndJava
     ./
     
     
    Token ::= identifier
        /.$BeginJava
                    checkForKeyWord();
          $EndJava
        ./
    Token ::= number
        /.$BeginJava
                    makeToken($_NUMBER);
          $EndJava
        ./
    Token ::= DoubleLiteral
        /.$BeginJava
                    makeToken($_DoubleLiteral);
          $EndJava
        ./
    Token ::= white
        /.$BeginJava
                    skipToken();
          $EndJava
        ./
    Token ::= slc
        /.$BeginJava
                    makeComment($_SINGLE_LINE_COMMENT);
          $EndJava
        ./
    Token ::= ';'
        /.$BeginJava
                    makeToken($_SEMICOLON);
          $EndJava
        ./

    Token ::= ','
        /.$BeginJava
                    makeToken($_COMMA);
          $EndJava
        ./

    Token ::= '+'
        /.$BeginJava
                    makeToken($_PLUS);
          $EndJava
        ./

    Token ::= '-'
        /.$BeginJava
                    makeToken($_MINUS);
          $EndJava
        ./

    Token ::= '='
        /.$BeginJava
                    makeToken($_ASSIGN);
          $EndJava
        ./

    Token ::= '('
        /.$BeginJava
                    makeToken($_LEFTPAREN);
          $EndJava
        ./

    Token ::= ')'
        /.$BeginJava
                    makeToken($_RIGHTPAREN);
          $EndJava
        ./

    Token ::= '{'
        /.$BeginJava
                    makeToken($_LEFTBRACE);
          $EndJava
        ./

    Token ::= '}'
        /.$BeginJava
                    makeToken($_RIGHTBRACE);
          $EndJava
        ./

    Token ::= '*'
        /.$BeginJava
                    makeToken($_TIMES);
          $EndJava
        ./

    Token ::= '/'
        /.$BeginJava
                    makeToken($_DIVIDE);
          $EndJava
        ./

    Token ::= '>'
        /.$BeginJava
                    makeToken($_GREATER);
          $EndJava
        ./

    Token ::= '<'
        /.$BeginJava
                    makeToken($_LESS);
          $EndJava
        ./

    Token ::= '=' '='
        /.$BeginJava
                    makeToken($_EQUAL);
          $EndJava
        ./

    Token ::= '!' '='
        /.$BeginJava
                    makeToken($_NOTEQUAL);
          $EndJava
        ./

    identifier -> letter
                | identifier letter
                | identifier digit

    number ::= digit
             | number digit

    DoubleLiteral ::= Decimal
                    | Decimal Exponent
                    | number Exponent
                    
    Exponent ::= LetterEe number
               | LetterEe '-' number
               | LetterEe '+' number

    LetterEe ::= 'e'
               | 'E'

    Decimal ::= '.' number
              | number '.'
              | number '.' number
    
    white ::= whiteChar
            | white whiteChar

    slc ::= '/' '/'
          | slc notEOL

    digit ::= 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9

    aA ::= a | A
    bB ::= b | B
    cC ::= c | C
    dD ::= d | D
    eE ::= e | E
    fF ::= f | F
    gG ::= g | G
    hH ::= h | H
    iI ::= i | I
    jJ ::= j | J
    kK ::= k | K
    lL ::= l | L
    mM ::= m | M
    nN ::= n | N
    oO ::= o | O
    pP ::= p | P
    qQ ::= q | Q
    rR ::= r | R
    sS ::= s | S
    tT ::= t | T
    uU ::= u | U
    vV ::= v | V
    wW ::= w | W
    xX ::= x | X
    yY ::= y | Y
    zZ ::= z | Z

    letter ::= aA | bB | cC | dD | eE | fF | gG | hH | iI | jJ | kK | lL | mM | nN | oO | pP | qQ | rR | sS | tT | uU | vV | wW | xX | yY | zZ

    -- any ::= letter | digit | special | white

    whiteChar ::= Space | LF | CR | HT | FF

    special ::= '+' | '-' | '(' | ')' | '"' | '!' | '@' | '`' | '~' | '.' |
                '%' | '&' | '^' | ':' | ';' | "'" | '\' | '|' | '{' | '}' |
                '[' | ']' | '?' | ',' | '<' | '>' | '=' | '#' | '*' | '_' |
                '/' | '$'

    notEOL ::= letter | digit | special | Space | HT | FF
%End
