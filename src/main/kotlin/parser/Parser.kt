package parser

import exceptions.ParserException
import lexer.SemanticValue.*
import lexer.SemanticValue
import lexer.Token
import parser.expressions.*
import parser.statements.*

class Parser(var tokens: List<Token>) {
    private var position = 0
    val listOfVariables = mutableListOf<String>()
    val mapOfLabel = mutableMapOf<String, Statement>()

    private fun readVariables() {
        if (find(READ)) {
            while (find(VAR)) {
                listOfVariables.add(previousToken().name)

                when {
                    find(COMMA) -> {}
                    find(SEMCOL) -> break
                    else -> throw ParserException(current().name, current().line)
                }
            }
        }
    }

    private fun readStatement(): Statement {
       return when {
            find(VAR) -> readAssign()
            find(IF) -> readIF()
            find(GOTO) -> readJump()
            else -> throw ParserException("not match", current().line)
        }
    }

    private fun readJump(): Statement {
        val name = current().name

        return JumpStatement(name)
    }

    private fun readAssign(): Statement {
        val token = previousToken()

        if (!find(ASSIGN)) {
            throw ParserException("not match ${current().name}", current().line)
        }

        val exp = readExp()

        return AssignStatement(listOfVariables.indexOf(token.name), exp, readStatement())
    }

    private fun readExp(): Expression {
        return equality()
    }

    private fun equality(): Expression {
        var exp: Expression = readCompare()
        while (find(EQ, NOTQE)) {
            val operation = previousToken()
            val rightExp: Expression = readCompare()
            exp = BinExp(exp, operation, rightExp)
        }

        return exp
    }

    private fun readCompare(): Expression {
        var leftExp: Expression = readTerm()

        while (find(GT, LT, LOEQ, GOEQ)) {
            val operation = previousToken()
            val rightTExp: Expression = readCompare()
            leftExp = BinExp(leftExp, operation, rightTExp)
        }

        return leftExp
    }

    private fun readTerm(): Expression {
        var leftExp: Expression = readFactor()

        while (find(MINUS, PLUS)) {
            val operation = previousToken()
            val rightExp: Expression = readFactor()
            leftExp = BinExp(leftExp, operation, rightExp)
        }

        return leftExp
    }

    private fun readFactor(): Expression {
        var leftExp: Expression = readUnar()

        while (find(ASTER)) {
            val operation = previousToken()
            val rightExp: Expression = readUnar()
            leftExp = BinExp(leftExp, operation, rightExp)
        }

        return leftExp
    }

    private fun readUnar(): Expression {
        if (find(MINUS)) {
            val operation = previousToken()
            val value: Expression = readValue()

            return UnExp(operation, value)
        }

        return readValue()
    }

    private fun readValue(): PrimeExp {
        var exp: PrimeExp? = null
        val token = previousToken()

        when {
            find(VAR) -> {
                val index = listOfVariables.indexOf(token.name)

                if (index == -1) {
                    throw ParserException(current().name, current().line)
                }

                exp = VarNode(listOfVariables.indexOf(token.name))
            }
            find(INT) -> {
                exp = ValNode(token.name.toInt())
            }
        }

        return exp ?: throw ParserException(current().name, current().line)
    }

    private fun find(vararg values: SemanticValue?): Boolean {
        for (value in values) {
            if (current().value == value) {
                move()

                return true
            }
        }

        return false
    }

    private fun previousToken(): Token {
        return tokens[position - 1]
    }

    private fun current(): Token {
        return tokens[position]
    }

    private fun move() {
        if (current().value != END) {
            position++
        }
    }
}
