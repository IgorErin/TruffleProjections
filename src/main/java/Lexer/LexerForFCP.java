package Lexer;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerForFCP {
    public List<Token> listOfTokens = new LinkedList<>();
    private int position = 0;
    private int line = 1;

    private static String stringOfCode;

    public LexerForFCP(String stringOfCode) {
        LexerForFCP.stringOfCode = stringOfCode;
    }

    public void parseString() {
        int len = stringOfCode.length();

        for (;position < len; position++) {
            char aChar = stringOfCode.charAt(position);

            switch (aChar) {
                case ' ', '\r' ,'\t' -> {}
                case '\n' -> {
                    line++;
                }
                case ',' -> {
                    addNewToken(SemanticValue.COMMA, ",");
                }
                case ';' -> {
                    addNewToken(SemanticValue.SEMCOL, ";");
                }
                case '>' -> {
                    if (peek() == '=') {
                        addNewToken(SemanticValue.GOEQ, ">=");
                        position++;
                    }
                    else {
                        addNewToken(SemanticValue.GT, ">");
                    }
                }
                case '<' -> {
                    if (peek() == '=') {
                        addNewToken(SemanticValue.LOEQ, "<=");
                        position++;
                    }
                    else {
                        addNewToken(SemanticValue.LT, "<");
                    }
                }
                case ':' -> {
                    if (peek() == '=') {
                        addNewToken(SemanticValue.ASSIGN, ":=");
                        position++;
                    }
                    else {
                        addNewToken(SemanticValue.WRONG, ":");
                    }

                }
                case '=' -> {
                    if (peek() == '=') {
                        addNewToken(SemanticValue.EQ, "==");
                        position++;
                    }
                    else {
                        addNewToken(SemanticValue.WRONG, "=");
                    }
                }
                case '(' -> {
                    addNewToken(SemanticValue.LPAREN, "(");
                }
                case ')' -> {
                    addNewToken(SemanticValue.RPAREN, ")");
                }
                case '-' -> {
                    addNewToken(SemanticValue.MINUS, "-");
                }
                case '+' -> {
                    addNewToken(SemanticValue.PLUS, "+");
                }
                case '*' -> {
                    addNewToken(SemanticValue.ASTER, "*");
                }
                default -> {
                    if (Character.isDigit(aChar)) {
                        addNewToken(SemanticValue.INT, readNumber(stringOfCode.substring(position)));
                    } else if (Character.isAlphabetic(aChar)) {
                        String name = readChars(stringOfCode.substring(position));
                        if (name != null) {
                            match(name);
                        }
                    } else {
                        addNewToken(SemanticValue.WRONG, Character.toString(aChar));
                    }
                }
            }
        }

        creatingLabels();
        line++;
        addNewToken(SemanticValue.END, "END");
    }

    boolean isEnd() {
        return position + 1 >= stringOfCode.length();
    }

    void addNewToken(SemanticValue value, String name) {
        listOfTokens.add(new Token(value, name, line));
    }

    private char peek() {
        if (isEnd()) return '\0';
        return stringOfCode.charAt(position + 1);
    }

    String readNumber(String stringOfCode) {
        Pattern pForNumber = Pattern.compile("[0-9]+");

        Matcher matcher = pForNumber.matcher(stringOfCode);
        String name;

        if (matcher.find()) {
            name  = matcher.group();
            position += name.length() - 1;
            return name;
        }

        return null;
    }

     String readChars(String stringOfCode) {
        Pattern pForLetter = Pattern.compile("[a-zA-Z]+");

        Matcher matcher = pForLetter.matcher(stringOfCode);
        String name;

        if (matcher.find()) {
            name  = matcher.group();
            position += name.length() - 1;
            return name;
        }

        return null;
    }

    void match(String name) {
        switch (name) {
            case "return" -> {
                addNewToken(SemanticValue.RETURN, name);
            }
            case "if" -> {
                addNewToken(SemanticValue.IF, name);
            }
            case "else" -> {
                addNewToken(SemanticValue.ELSE, name);
            }
            case "goto" -> {
                addNewToken(SemanticValue.GOTO, name);
            }
            case "read" -> {
                addNewToken(SemanticValue.READ, name);
            }
            default -> {
                addNewToken(SemanticValue.VAR, name);
            }
        }
    }

    void creatingLabels() {
        for (int index = 0; index < listOfTokens.size(); index++) {
            String name;
            if (index > 0 && listOfTokens.get(index).value == SemanticValue.COLON) {
                listOfTokens.remove(index);
                name = listOfTokens.remove(index - 1).name;

                listOfTokens.add(index - 1, new Token(SemanticValue.LABLE, name, line));
            }
        }
    }
}
