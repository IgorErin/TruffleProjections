package lexer

import java.util.LinkedList
import java.util.regex.Pattern

class LexerForFCP(stringOfCode: String?) {
    @JvmField
    var listOfTokens: MutableList<Token> = LinkedList()
    private var position = 0
    private var line = 1

    init {
        Companion.stringOfCode = stringOfCode
    }

    fun parseString() {
        val len = stringOfCode!!.length
        while (position < len) {
            val aChar = stringOfCode!![position]
            when (aChar) {
                ' ', '\r', '\t' -> {}
                '\n' -> {
                    line++
                }
                ',' -> {
                    addNewToken(SemanticValue.COMMA, ",")
                }
                ';' -> {
                    addNewToken(SemanticValue.SEMCOL, ";")
                }
                '>' -> {
                    if (peek() == '=') {
                        addNewToken(SemanticValue.GOEQ, ">=")
                        position++
                    } else {
                        addNewToken(SemanticValue.GT, ">")
                    }
                }
                '<' -> {
                    if (peek() == '=') {
                        addNewToken(SemanticValue.LOEQ, "<=")
                        position++
                    } else {
                        addNewToken(SemanticValue.LT, "<")
                    }
                }
                ':' -> {
                    if (peek() == '=') {
                        addNewToken(SemanticValue.ASSIGN, ":=")
                        position++
                    } else {
                        addNewToken(SemanticValue.WRONG, ":")
                    }
                }
                '=' -> {
                    if (peek() == '=') {
                        addNewToken(SemanticValue.EQ, "==")
                        position++
                    } else {
                        addNewToken(SemanticValue.WRONG, "=")
                    }
                }
                '(' -> {
                    addNewToken(SemanticValue.LPAREN, "(")
                }
                ')' -> {
                    addNewToken(SemanticValue.RPAREN, ")")
                }
                '-' -> {
                    addNewToken(SemanticValue.MINUS, "-")
                }
                '+' -> {
                    addNewToken(SemanticValue.PLUS, "+")
                }
                '*' -> {
                    addNewToken(SemanticValue.ASTER, "*")
                }
                else -> {
                    if (Character.isDigit(aChar)) {
                        addNewToken(SemanticValue.INT, readNumber(stringOfCode!!.substring(position))!!)
                    } else if (Character.isAlphabetic(aChar.code)) {
                        val name = readChars(stringOfCode!!.substring(position))
                        name?.let { match(it) }
                    } else {
                        addNewToken(SemanticValue.WRONG, Character.toString(aChar))
                    }
                }
            }
            position++
        }
        creatingLabels()
        line++
        addNewToken(SemanticValue.END, "END")
    }

    val isEnd: Boolean
        get() = position + 1 >= stringOfCode!!.length

    fun addNewToken(value: SemanticValue, name: String) {
        listOfTokens.add(Token(value, name, line))
    }

    private fun peek(): Char {
        return if (isEnd) '\u0000' else stringOfCode!![position + 1]
    }

    fun readNumber(stringOfCode: String?): String? {
        val pForNumber = Pattern.compile("[0-9]+")
        val matcher = pForNumber.matcher(stringOfCode)
        val name: String
        if (matcher.find()) {
            name = matcher.group()
            position += name.length - 1
            return name
        }
        return null
    }

    fun readChars(stringOfCode: String?): String? {
        val pForLetter = Pattern.compile("[a-zA-Z]+")
        val matcher = pForLetter.matcher(stringOfCode)
        val name: String
        if (matcher.find()) {
            name = matcher.group()
            position += name.length - 1
            return name
        }
        return null
    }

    fun match(name: String) {
        when (name) {
            "return" -> {
                addNewToken(SemanticValue.RETURN, name)
            }
            "if" -> {
                addNewToken(SemanticValue.IF, name)
            }
            "else" -> {
                addNewToken(SemanticValue.ELSE, name)
            }
            "goto" -> {
                addNewToken(SemanticValue.GOTO, name)
            }
            "read" -> {
                addNewToken(SemanticValue.READ, name)
            }
            else -> {
                addNewToken(SemanticValue.VAR, name)
            }
        }
    }

    fun creatingLabels() {
        for (index in listOfTokens.indices) {
            var name: String?
            if (index > 0 && listOfTokens[index].value == SemanticValue.COLON) {
                listOfTokens.removeAt(index)
                name = listOfTokens.removeAt(index - 1).name
                listOfTokens.add(index - 1, Token(SemanticValue.LABLE, name, line))
            }
        }
    }

    companion object {
        private var stringOfCode: String? = null
    }
}