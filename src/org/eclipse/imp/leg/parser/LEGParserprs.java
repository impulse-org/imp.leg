package org.eclipse.imp.leg.parser;

public class LEGParserprs implements lpg.runtime.ParseTable, LEGParsersym {
    public final static int ERROR_SYMBOL = 32;
    public final int getErrorSymbol() { return ERROR_SYMBOL; }

    public final static int SCOPE_UBOUND = 3;
    public final int getScopeUbound() { return SCOPE_UBOUND; }

    public final static int SCOPE_SIZE = 4;
    public final int getScopeSize() { return SCOPE_SIZE; }

    public final static int MAX_NAME_LENGTH = 19;
    public final int getMaxNameLength() { return MAX_NAME_LENGTH; }

    public final static int NUM_STATES = 46;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 32;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 296;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 56;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 25;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 57;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 60;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 29;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 29;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 239;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 240;
    public final int getErrorAction() { return ERROR_ACTION; }

    public final static boolean BACKTRACK = false;
    public final boolean getBacktrack() { return BACKTRACK; }

    public final int getStartSymbol() { return lhs(0); }
    public final boolean isValidForParser() { return LEGParsersym.isValidForParser; }


    public interface IsNullable {
        public final static byte isNullable[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,1,0,0,
            0,1,0,1,1,0,0
        };
    };
    public final static byte isNullable[] = IsNullable.isNullable;
    public final boolean isNullable(int index) { return isNullable[index] != 0; }

    public interface ProsthesesIndex {
        public final static byte prosthesesIndex[] = {0,
            7,23,22,11,20,10,5,13,14,15,
            16,17,18,19,21,2,3,4,6,8,
            9,12,24,25,1
        };
    };
    public final static byte prosthesesIndex[] = ProsthesesIndex.prosthesesIndex;
    public final int prosthesesIndex(int index) { return prosthesesIndex[index]; }

    public interface IsKeyword {
        public final static byte isKeyword[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0
        };
    };
    public final static byte isKeyword[] = IsKeyword.isKeyword;
    public final boolean isKeyword(int index) { return isKeyword[index] != 0; }

    public interface BaseCheck {
        public final static byte baseCheck[] = {0,
            0,2,2,5,0,1,1,3,2,0,
            2,1,1,1,1,1,1,1,1,3,
            2,4,1,1,1,1,1,4,1,5,
            7,5,3,3,3,3,3,3,3,3,
            3,1,1,1,1,1,1,1,4,2,
            0,1,1,3,1,3,-7,0,0,-1,
            0,-6,0,0,0,0,0,0,0,0,
            0,0,-41,0,0,0,0,-9,0,0,
            0,0,0,0,0,0,0,0,-42,0,
            0,-10,0,-12,0,0,0,0,0,0,
            0,0,0,0,-46,0,0,-13,0,-15,
            0,0,0,0,0,0,0,0,0,0,
            -22,0,0,0,-3,0,-4,0,-2,-8,
            -16,0,0,0,-17,0,-11,0,0,0,
            -19,0,-20,0,0,0,0,0,-26,0,
            0,-18,0,0,0,-35,0,-21,0,0,
            0,-36,0,-23,0,0,0,-37,0,-24,
            0,0,0,-38,0,-43,0,0,0,-39,
            0,-27,0,0,0,-28,0,0,0,-29,
            0,0,0,-30,0,0,0,-31,0,0,
            0,-32,0,0,0,-33,0,0,0,-34,
            0,0,0,-25,-5,-14,0,0,-40,0,
            -44,-45,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0
        };
    };
    public final static byte baseCheck[] = BaseCheck.baseCheck;
    public final int baseCheck(int index) { return baseCheck[index]; }
    public final static byte rhs[] = baseCheck;
    public final int rhs(int index) { return rhs[index]; };

    public interface BaseAction {
        public final static char baseAction[] = {
            16,16,16,17,18,20,20,21,21,6,
            22,22,8,8,8,8,8,8,8,8,
            7,9,9,19,19,4,4,4,10,10,
            11,11,13,12,5,5,5,5,5,5,
            5,5,5,3,3,3,3,3,3,2,
            14,23,23,24,24,1,15,1,108,78,
            7,216,113,110,17,11,12,13,14,15,
            16,18,29,5,108,78,129,216,3,110,
            17,32,12,13,14,15,16,18,29,5,
            108,78,148,216,151,110,17,221,12,13,
            14,15,16,18,29,5,108,78,136,216,
            124,110,17,31,12,13,14,15,16,18,
            29,128,143,48,42,143,174,9,62,110,
            53,52,3,23,216,91,7,116,143,48,
            42,30,141,152,162,168,2,125,127,40,
            131,135,116,143,48,42,54,149,116,143,
            48,42,153,156,101,143,48,42,130,180,
            116,143,48,42,90,219,116,143,48,42,
            70,222,116,143,48,41,116,143,48,40,
            116,143,48,39,116,143,48,38,116,143,
            48,37,116,143,48,36,116,143,48,35,
            116,143,48,34,129,8,9,9,216,80,
            8,155,100,240,240,240,240,240,240,240,
            240,240,240,240,240,240,240,57,240,240
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,1,0,1,0,1,0,0,0,0,
            10,11,12,13,10,11,12,13,10,0,
            20,21,22,23,20,21,22,23,28,0,
            1,2,3,4,5,6,7,8,9,0,
            0,2,3,4,5,6,7,8,9,0,
            0,0,0,0,15,2,3,4,5,6,
            7,8,9,11,12,13,15,0,15,0,
            1,2,3,4,5,6,7,8,9,0,
            1,2,3,4,5,6,7,8,9,0,
            0,2,3,4,5,6,7,8,9,0,
            0,2,3,4,5,6,7,8,9,0,
            10,0,0,0,24,0,16,17,18,19,
            11,12,13,0,1,10,14,0,0,0,
            30,16,17,18,19,0,27,10,29,11,
            12,13,0,16,17,18,19,0,25,14,
            0,0,0,24,0,0,0,0,0,0,
            25,14,20,0,14,14,0,15,0,0,
            0,0,0,0,0,0,0,0,0,0,
            26,0,0,0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            240,259,240,290,240,259,1,10,240,240,
            295,265,266,267,295,265,266,267,295,240,
            215,94,137,92,215,94,137,92,260,240,
            273,210,206,202,198,194,190,186,182,240,
            240,210,206,202,198,194,190,186,182,240,
            240,240,5,240,73,210,206,202,198,194,
            190,186,182,265,266,267,244,240,89,240,
            268,210,206,202,198,194,190,186,182,240,
            262,210,206,202,198,194,190,186,182,53,
            6,210,206,202,198,194,190,186,182,54,
            240,210,206,202,198,194,190,186,182,240,
            295,240,240,240,214,240,286,285,283,284,
            265,266,267,240,261,295,130,51,240,52,
            296,286,285,283,284,240,264,295,239,265,
            266,267,240,286,285,283,284,240,170,121,
            240,47,240,176,30,240,240,240,240,240,
            164,152,215,240,158,121,240,289,240,240,
            240,240,240,240,240,240,240,240,240,240,
            105
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }

    public interface Asb {
        public final static byte asb[] = {0,
            1,1,20,58,61,8,61,22,38,8,
            54,8,7,58,10,51,27,54,30,40,
            54,13,53,54,68,43,54,54,54,54,
            54,54,54,54,43,51,27,42,30,30,
            62,62,54,60,42,62
        };
    };
    public final static byte asb[] = Asb.asb;
    public final int asb(int index) { return asb[index]; }

    public interface Asr {
        public final static byte asr[] = {0,
            11,12,13,27,29,0,25,14,0,1,
            25,0,15,18,19,17,16,10,0,20,
            0,15,11,12,13,0,15,24,0,2,
            3,4,5,6,7,8,9,1,0,1,
            14,24,2,3,4,5,6,7,8,9,
            15,0,30,18,19,17,16,10,0,26,
            28,10,21,22,23,20,1,11,12,13,
            0
        };
    };
    public final static byte asr[] = Asr.asr;
    public final int asr(int index) { return asr[index]; }

    public interface Nasb {
        public final static byte nasb[] = {0,
            7,13,9,11,20,6,1,17,6,6,
            11,6,6,11,6,6,6,11,6,6,
            11,11,11,11,4,6,11,11,11,11,
            11,11,11,11,6,6,6,6,6,6,
            1,1,11,6,6,1
        };
    };
    public final static byte nasb[] = Nasb.nasb;
    public final int nasb(int index) { return nasb[index]; }

    public interface Nasr {
        public final static byte nasr[] = {0,
            1,2,8,4,6,0,16,0,7,0,
            1,0,19,18,17,0,21,4,0,22,
            0
        };
    };
    public final static byte nasr[] = Nasr.nasr;
    public final int nasr(int index) { return nasr[index]; }

    public interface TerminalIndex {
        public final static byte terminalIndex[] = {0,
            2,3,4,6,7,8,9,10,11,27,
            17,18,22,12,13,20,24,28,29,14,
            21,23,26,1,5,19,25,15,30,32,
            31,33
        };
    };
    public final static byte terminalIndex[] = TerminalIndex.terminalIndex;
    public final int terminalIndex(int index) { return terminalIndex[index]; }

    public interface NonterminalIndex {
        public final static byte nonterminalIndex[] = {0,
            38,45,44,41,43,40,36,42,0,0,
            0,0,0,0,0,0,34,35,37,0,
            39,0,0,46,0
        };
    };
    public final static byte nonterminalIndex[] = NonterminalIndex.nonterminalIndex;
    public final int nonterminalIndex(int index) { return nonterminalIndex[index]; }

    public interface ScopePrefix {
        public final static byte scopePrefix[] = {
            12,6,1,21
        };
    };
    public final static byte scopePrefix[] = ScopePrefix.scopePrefix;
    public final int scopePrefix(int index) { return scopePrefix[index]; }

    public interface ScopeSuffix {
        public final static byte scopeSuffix[] = {
            18,10,4,24
        };
    };
    public final static byte scopeSuffix[] = ScopeSuffix.scopeSuffix;
    public final int scopeSuffix(int index) { return scopeSuffix[index]; }

    public interface ScopeLhs {
        public final static byte scopeLhs[] = {
            11,2,15,7
        };
    };
    public final static byte scopeLhs[] = ScopeLhs.scopeLhs;
    public final int scopeLhs(int index) { return scopeLhs[index]; }

    public interface ScopeLa {
        public final static byte scopeLa[] = {
            26,15,30,28
        };
    };
    public final static byte scopeLa[] = ScopeLa.scopeLa;
    public final int scopeLa(int index) { return scopeLa[index]; }

    public interface ScopeStateSet {
        public final static byte scopeStateSet[] = {
            16,1,16,21
        };
    };
    public final static byte scopeStateSet[] = ScopeStateSet.scopeStateSet;
    public final int scopeStateSet(int index) { return scopeStateSet[index]; }

    public interface ScopeRhs {
        public final static byte scopeRhs[] = {0,
            25,33,0,30,0,55,14,33,0,15,
            0,40,15,37,14,21,0,26,40,0,
            54,20,0,28,0
        };
    };
    public final static byte scopeRhs[] = ScopeRhs.scopeRhs;
    public final int scopeRhs(int index) { return scopeRhs[index]; }

    public interface ScopeState {
        public final static char scopeState[] = {0,
            176,210,206,202,198,194,190,186,182,170,
            164,121,158,152,137,105,89,73,57,0,
            105,89,73,57,125,0
        };
    };
    public final static char scopeState[] = ScopeState.scopeState;
    public final int scopeState(int index) { return scopeState[index]; }

    public interface InSymb {
        public final static byte inSymb[] = {0,
            0,48,50,51,20,33,54,14,34,23,
            22,21,33,36,38,52,53,14,37,33,
            14,14,25,25,24,37,9,8,7,6,
            5,4,3,2,37,55,56,37,37,37,
            15,15,24,40,37,26
        };
    };
    public final static byte inSymb[] = InSymb.inSymb;
    public final int inSymb(int index) { return inSymb[index]; }

    public interface Name {
        public final static String name[] = {
            "",
            ",",
            ";",
            "+",
            "-",
            "=",
            "*",
            "/",
            ">",
            "<",
            "==",
            "!=",
            "(",
            ")",
            "{",
            "}",
            "$empty",
            "boolean",
            "double",
            "else",
            "false",
            "if",
            "int",
            "return",
            "true",
            "void",
            "while",
            "IDENTIFIER",
            "NUMBER",
            "DoubleLiteral",
            "EOF_TOKEN",
            "SINGLE_LINE_COMMENT",
            "MissingExpression",
            "ERROR_TOKEN",
            "functionDeclaration",
            "functionHeader",
            "block",
            "Type",
            "identifier",
            "parameterList",
            "declaration",
            "primitiveType",
            "statement",
            "expression",
            "term",
            "functionCall",
            "expressionList"
        };
    };
    public final static String name[] = Name.name;
    public final String name(int index) { return name[index]; }

    public final int originalState(int state) {
        return -baseCheck[state];
    }
    public final int asi(int state) {
        return asb[originalState(state)];
    }
    public final int nasi(int state) {
        return nasb[originalState(state)];
    }
    public final int inSymbol(int state) {
        return inSymb[originalState(state)];
    }

    /**
     * assert(! goto_default);
     */
    public final int ntAction(int state, int sym) {
        return baseAction[state + sym];
    }

    /**
     * assert(! shift_default);
     */
    public final int tAction(int state, int sym) {
        int i = baseAction[state],
            k = i + sym;
        return termAction[termCheck[k] == sym ? k : i];
    }
    public final int lookAhead(int la_state, int sym) {
        int k = la_state + sym;
        return termAction[termCheck[k] == sym ? k : la_state];
    }
}
