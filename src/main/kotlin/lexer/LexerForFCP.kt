package lexer

import java.util.regex.Pattern
import lexer.SemanticValue.*

class LexerForFCP(private val stringOfCode: String) {
    var listOfTokens = mutableListOf<Token>()
    private var position = 0
    private var line = 1

    fun parseString() {
        while (position in stringOfCode.indices) {
            when (val aChar = stringOfCode[position]) {
                ' ', '\r', '\t' -> {}
                '\n' -> {
                    line++
                }
                ',' -> {
                    addNewToken(COMMA, ",")
                }
                ';' -> {
                    addNewToken(SEMCOL, ";")
                }
                '>' -> {
                    if (peek() == '=') {
                        addNewToken(GOEQ, ">=")
                        position++
                    } else {
                        addNewToken(GT, ">")
                    }
                }
                '<' -> {
                    if (peek() == '=') {
                        addNewToken(LOEQ, "<=")
                        position++
                    } else {
                        addNewToken(LT, "<")
                    }
                }
                ':' -> {
                    if (peek() == '=') {
                        addNewToken(ASSIGN, ":=")
                        position++
                    } else {
                        addNewToken(COLON, ":")
                    }
                }
                '=' -> {
                    if (peek() == '=') {
                        addNewToken(EQ, "==")
                        position++
                    } else {
                        addNewToken(WRONG, "=")
                    }
                }
                '(' -> {
                    addNewToken(LPAREN, "(")
                }
                ')' -> {
                    addNewToken(RPAREN, ")")
                }
                '-' -> {
                    addNewToken(MINUS, "-")
                }
                '+' -> {
                    addNewToken(PLUS, "+")
                }
                '*' -> {
                    addNewToken(ASTER, "*")
                }
                else -> {
                    if (Character.isDigit(aChar)) {
                        addNewToken(INT, readNumber(stringOfCode.substring(position))!!)
                    } else if (Character.isAlphabetic(aChar.code)) {
                        val name = readChars(stringOfCode.substring(position))
                        name?.let { match(it) }
                    } else {
                        addNewToken(WRONG, aChar.toString())
                    }
                }
            }
            position++
        }

        creatingLabels()
        line++
        addNewToken(END, "END")
    }

    private val isEnd: Boolean
        get() = position + 1 >= stringOfCode.length

    private fun addNewToken(value: SemanticValue, name: String) {
        listOfTokens.add(Token(value, name, line))
    }

    private fun peek(): Char? {
        return if (isEnd) null else stringOfCode[position + 1]
    }

    private fun readNumber(stringOfCode: String): String? {
        val pForNumber = Pattern.compile("[0-9]+")
        val matcher = pForNumber.matcher(stringOfCode)

        if (matcher.find()) {
            val name = matcher.group()
            position += name.length - 1
            return name
        }

        return null
    }

    private fun readChars(stringOfCode: String): String? {
        val pForLetter = Pattern.compile("[a-zA-Z]+")
        val matcher = pForLetter.matcher(stringOfCode)

        if (matcher.find()) {
            val name = matcher.group()
            position += name.length - 1
            return name
        }

        return null
    }

    private fun match(name: String) {
        when (name) {
            "return" -> {
                addNewToken(RETURN, name)
            }
            "if" -> {
                addNewToken(IF, name)
            }
            "else" -> {
                addNewToken(ELSE, name)
            }
            "goto" -> {
                addNewToken(GOTO, name)
            }
            "read" -> {
                addNewToken(READ, name)
            }
            else -> {
                addNewToken(VAR, name)
            }
        }
    }

    private fun creatingLabels() {
        for (index in listOfTokens.indices) {
            if (index > 0 && index < listOfTokens.size && listOfTokens[index].value == COLON) {
                listOfTokens.removeAt(index)
                val name = listOfTokens.removeAt(index - 1).name
                listOfTokens.add(index - 1, Token(LABEL, name, line))
            }
        }
    }
}