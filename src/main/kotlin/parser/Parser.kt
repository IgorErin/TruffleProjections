package parser

import lexer.SemanticValue.*
import lexer.SemanticValue
import lexer.Token
import parser.expressions.BinExp
import parser.expressions.Expression
import parser.expressions.PrimeExp
import parser.expressions.UnExp
import parser.statements.*

class Parser(var tokens: List<Token>) {
    private var position = 0

    private fun readLabels(): MutableMap<String, MutableList<Statement>> {
        val mapOfBlocks = mutableMapOf<String, MutableList<Statement>>()

        while (find(LABEL)) {
            mapOfBlocks[current().name] = readBasicBlock()
        }

        return mapOfBlocks
    }

    private fun readBasicBlock(): MutableList<Statement> {
        val basicBloc =  mutableListOf<Statement>()

        while (find(VAR)) {
            basicBloc.add(readAssignment())
        }
        basicBloc.add(readJump())

        return basicBloc
    }

    private fun readAssignment(): Statement {
        val variable = VarStatement(previousToken().name)

        if (!find(ASSIGN)) {
            throw ParserException(current().name, current().line)
        }

        val exp = readExp()

        return AssignStatement(variable, exp)
    }

    private fun readJump(): Statement {
        if (find(GOTO)) {
            return JumpStatement(readValue())
        }

        if (find(IF)) {
            return readIfState()
        }

        if (find(RETURN)) {
            return ReturnStatement(readExp());
        }

        throw ParserException(current().name, current().line)
    }

    private fun readIfState(): Statement {
        val exp: Expression = readExp()


        if (!find(GOTO)) {
            throw ParserException(current().name, current().line)
        }

        val firstJumpLabel = JumpStatement(readValue())

        if (!find(ELSE)) {
            throw ParserException(current().name, current().line)
        }

        val secondJumpLabel = JumpStatement(readValue())

        return IfStatement(exp, firstJumpLabel, secondJumpLabel)
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

    private fun readValue(): Expression {
        var exp: Expression? = null

        if (find(VAR, INT)) {
            exp = PrimeExp(previousToken())
        }


        return exp ?: throw ParserException("invalid syntax", current().line)
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
