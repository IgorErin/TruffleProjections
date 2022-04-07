package parser

import lexer.SemanticValue
import lexer.Token
import parser.expressions.BinExp
import parser.expressions.Expression
import parser.expressions.PrimeExp
import parser.expressions.UnExp

class Parser(var tokens: List<Token>) {
    private var position = 0

    fun parse(): Expression {

        return exp()
    }

    private fun exp(): Expression {
        return equality()
    }

    private fun equality(): Expression {
        var exp: Expression = readCompare()
        while (find(SemanticValue.EQ, SemanticValue.NOTQE)) {
            val operation = previousToken()
            val rightExp: Expression = readCompare()
            exp = BinExp(exp, operation, rightExp)
        }

        return exp
    }

    private fun readCompare(): Expression {
        var leftExp: Expression = readTerm()
        while (find(SemanticValue.GT, SemanticValue.LT, SemanticValue.LOEQ, SemanticValue.GOEQ)) {
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

    private fun readValue(): Expression {
        var exp: Expression? = null

        if (find(SemanticValue.VAR, SemanticValue.INT)) {
            exp = PrimeExp(previousToken())
        }

        if (find(SemanticValue.LPAREN)) {
            exp = exp()
        }


        return exp ?: throw ParserException("invalid syntax")
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
        if (current().value != SemanticValue.END) {
            position++
        }
    }
}