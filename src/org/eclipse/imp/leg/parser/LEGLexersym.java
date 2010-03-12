package org.eclipse.imp.leg.parser;

public interface LEGLexersym {
    public final static int
      Char_CtlCharNotWS = 101,
      Char_LF = 71,
      Char_CR = 72,
      Char_HT = 14,
      Char_FF = 15,
      Char_a = 16,
      Char_b = 17,
      Char_c = 18,
      Char_d = 19,
      Char_e = 11,
      Char_f = 20,
      Char_g = 21,
      Char_h = 22,
      Char_i = 23,
      Char_j = 24,
      Char_k = 25,
      Char_l = 26,
      Char_m = 27,
      Char_n = 28,
      Char_o = 29,
      Char_p = 30,
      Char_q = 31,
      Char_r = 32,
      Char_s = 33,
      Char_t = 34,
      Char_u = 35,
      Char_v = 36,
      Char_w = 37,
      Char_x = 38,
      Char_y = 39,
      Char_z = 40,
      Char__ = 83,
      Char_A = 41,
      Char_B = 42,
      Char_C = 43,
      Char_D = 44,
      Char_E = 12,
      Char_F = 45,
      Char_G = 46,
      Char_H = 47,
      Char_I = 48,
      Char_J = 49,
      Char_K = 50,
      Char_L = 51,
      Char_M = 52,
      Char_N = 53,
      Char_O = 54,
      Char_P = 55,
      Char_Q = 56,
      Char_R = 57,
      Char_S = 58,
      Char_T = 59,
      Char_U = 60,
      Char_V = 61,
      Char_W = 62,
      Char_X = 63,
      Char_Y = 64,
      Char_Z = 65,
      Char_0 = 1,
      Char_1 = 2,
      Char_2 = 3,
      Char_3 = 4,
      Char_4 = 5,
      Char_5 = 6,
      Char_6 = 7,
      Char_7 = 8,
      Char_8 = 9,
      Char_9 = 10,
      Char_AfterASCII = 102,
      Char_Space = 66,
      Char_DoubleQuote = 84,
      Char_SingleQuote = 85,
      Char_Percent = 86,
      Char_VerticalBar = 87,
      Char_Exclamation = 73,
      Char_AtSign = 88,
      Char_BackQuote = 89,
      Char_Tilde = 90,
      Char_Sharp = 91,
      Char_DollarSign = 92,
      Char_Ampersand = 93,
      Char_Caret = 94,
      Char_Colon = 95,
      Char_SemiColon = 74,
      Char_BackSlash = 96,
      Char_LeftBrace = 75,
      Char_RightBrace = 76,
      Char_LeftBracket = 97,
      Char_RightBracket = 98,
      Char_QuestionMark = 99,
      Char_Comma = 77,
      Char_Dot = 67,
      Char_LessThan = 78,
      Char_GreaterThan = 79,
      Char_Plus = 68,
      Char_Minus = 69,
      Char_Slash = 70,
      Char_Star = 80,
      Char_LeftParen = 81,
      Char_RightParen = 82,
      Char_Equal = 13,
      Char_EOF = 100;

    public final static String orderedTerminalSymbols[] = {
                 "",
                 "0",
                 "1",
                 "2",
                 "3",
                 "4",
                 "5",
                 "6",
                 "7",
                 "8",
                 "9",
                 "e",
                 "E",
                 "Equal",
                 "HT",
                 "FF",
                 "a",
                 "b",
                 "c",
                 "d",
                 "f",
                 "g",
                 "h",
                 "i",
                 "j",
                 "k",
                 "l",
                 "m",
                 "n",
                 "o",
                 "p",
                 "q",
                 "r",
                 "s",
                 "t",
                 "u",
                 "v",
                 "w",
                 "x",
                 "y",
                 "z",
                 "A",
                 "B",
                 "C",
                 "D",
                 "F",
                 "G",
                 "H",
                 "I",
                 "J",
                 "K",
                 "L",
                 "M",
                 "N",
                 "O",
                 "P",
                 "Q",
                 "R",
                 "S",
                 "T",
                 "U",
                 "V",
                 "W",
                 "X",
                 "Y",
                 "Z",
                 "Space",
                 "Dot",
                 "Plus",
                 "Minus",
                 "Slash",
                 "LF",
                 "CR",
                 "Exclamation",
                 "SemiColon",
                 "LeftBrace",
                 "RightBrace",
                 "Comma",
                 "LessThan",
                 "GreaterThan",
                 "Star",
                 "LeftParen",
                 "RightParen",
                 "_",
                 "DoubleQuote",
                 "SingleQuote",
                 "Percent",
                 "VerticalBar",
                 "AtSign",
                 "BackQuote",
                 "Tilde",
                 "Sharp",
                 "DollarSign",
                 "Ampersand",
                 "Caret",
                 "Colon",
                 "BackSlash",
                 "LeftBracket",
                 "RightBracket",
                 "QuestionMark",
                 "EOF",
                 "CtlCharNotWS",
                 "AfterASCII"
             };

    public final static int numTokenKinds = orderedTerminalSymbols.length;
    public final static boolean isValidForParser = true;
}