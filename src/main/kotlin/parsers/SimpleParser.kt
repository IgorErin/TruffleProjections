package parsers

import exceptions.ParserException
import lexer.Values
import lexer.Values.*
import lexer.Token
import truffle.nodes.VarNode


/*class Parser(private val tokens: List<Token>) {
    private var position = 0

    //private val listOfVariables = mutableListOf<String>()
    private val mapOfLabel = mutableMapOf<String, Statement>()

    private fun readAssign(): Statement {
        val token = previousToken()

        if (!find(ASSIGN)) {
            throw ParserException("not match ${current().name}", current().line)
        }

        val exp = readExp()

        return AssignStatement(frameDescriptor.findFrameSlot(token.name), exp, readStatements())
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

        while (find(SemanticValue.MINUS, SemanticValue.PLUS)) {
            val operation = previousToken()
            val rightExp: Expression = readFactor()
            leftExp = BinExp(leftExp, operation, rightExp)
        }

        return leftExp
    }

    private fun readFactor(): Expression {
        var leftExp: Expression = readUnar()

        while (find(SemanticValue.ASTER)) {
            val operation = previousToken()
            val rightExp: Expression = readUnar()
            leftExp = BinExp(leftExp, operation, rightExp)
        }

        return leftExp
    }

    private fun readUnar(): Expression {
        if (find(SemanticValue.MINUS)) {
            val operation = previousToken()
            val value: Expression = readValue()

            return UnExp(operation, value)
        }

        return readValue()
    }

    private fun readValue(): PrimeExp {
        var exp: PrimeExp? = null
        val token = current()

        when {
            find(VAR) -> {
                exp = VarNode(
                    frameDescriptor.findFrameSlot(token.name) ?: throw ParserException(
                        "not init var: ${token.name}",
                        token.line
                    )
                )
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
}*/