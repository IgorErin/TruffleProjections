package parser

import exceptions.ParserException
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
    val listOfVariables = mutableListOf<String>()
    val listOfLabels = mutableListOf<String>()
    val mapOfBlocks = mutableMapOf<String, MutableList<Statement>>()

    fun read() {
        readVariables()
        readLabels()
    }

    fun readLabels(): MutableMap<String, MutableList<Statement>> {
        while (find(LABEL)) {
            listOfLabels.add(previousToken().name)
            mapOfBlocks[previousToken().name] = readBasicBlock()
        }

        return mapOfBlocks
    }

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

    private fun readBasicBlock(): MutableList<Statement> {
        val basicBloc =  mutableListOf<Statement>()

        while (find(VAR)) {
            basicBloc.add(readAssignment())
        }

        if (current().value != END && current().value != LABEL) {
            basicBloc.add(readJump())
        }

        return basicBloc
    }

    private fun readAssignment(): Statement {
        val variable = previousToken()

        if (!find(ASSIGN)) {
            throw ParserException(current().name, current().line)
        }

        val exp = readExp()

        return AssignStatement(variable.name, exp)
    }

    private fun readJump(): Statement {
        if (find(GOTO)) {
            val exp = readValue()
            return JumpStatement(exp.name, exp.line)
        }

        if (find(IF)) {
            return readIfState()
        }

        if (find(RETURN)) {
            return ReturnStatement(readExp())
        }

        throw ParserException(current().name, current().line)
    }

    private fun readIfState(): Statement {
        val exp: Expression = readExp()


        if (!find(GOTO)) {
            throw ParserException(current().name, current().line)
        }

        var jumpExp = readValue()

        val firstJumpLabel = JumpStatement(jumpExp.name, jumpExp.line)

        if (!find(ELSE)) {
            throw ParserException(current().name, current().line)
        }

        jumpExp = readValue()
        val secondJumpLabel = JumpStatement(jumpExp.name, jumpExp.line)

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

    private fun readValue(): PrimeExp {
        var exp: PrimeExp? = null

        if (find(VAR, INT)) {
            val token = previousToken()
            exp = PrimeExp(token.line, token.name, token.value)
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
