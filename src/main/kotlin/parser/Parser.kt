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

    fun readProgram(): Statement {
        readVariables()

        return searchLabel(current().name)
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

    private fun searchPositionOfLabel(name: String): Int {
        val pos = tokens.indexOf(tokens.first { it.value == LABEL && it.name == name})

        if(pos != -1) {
            return pos
        }

        throw TODO("not label 2")
    }

    private fun readStatements(): Statement {
       return when {
            find(VAR) -> readAssign()
            find(IF) -> readIf()
            find(GOTO) -> readJump()
            find(RETURN) -> readReturn()
            else -> throw ParserException(current().name, current().line)
        }
    }

    private fun readReturn(): Statement {
        return ReturnStatement(readExp())
    }

    private fun searchLabel(name: String): Statement {
        println(name)

        val savePosition = position + 1
        position = searchPositionOfLabel(name)
        move()

        val label = readStatements()
        position = savePosition

        mapOfLabel[name] = label

        return label
    }

    private fun readIf(): Statement {
        val exp = readExp()

        if (!find(GOTO)) {
            throw TODO("add exception for readIF")
        }

        val firstJump = readJump()

        if(!find(ELSE)) {
            throw TODO("${current()} add exception for readIF")
        }

        val secondJump = readJump()

        return IfStatement(exp, firstJump, secondJump)
    }

    private fun readJump(): JumpStatement {
        val name = current().name

        return JumpStatement(name, inMapOfLabel(name) )
    }

    private fun inMapOfLabel(name: String): Statement {
        println(mapOfLabel.keys)

        if (mapOfLabel[name] == null) {
            return searchLabel(name)
        }

        move()

        return mapOfLabel[name] ?: throw TODO("not implemented label")
    }

    private fun readAssign(): Statement {
        val token = previousToken()

        if (!find(ASSIGN)) {
            throw ParserException("not match ${current().name}", current().line)
        }

        val exp = readExp()

        return AssignStatement(listOfVariables.indexOf(token.name), exp, readStatements())
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
                val index = listOfVariables.indexOf(previousToken().name)

                if (index == -1) {
                    throw ParserException(current().name, current().line)
                }

                exp = VarNode(index)
            }
            find(INT) -> {
                exp = ValNode(previousToken().name.toInt())
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
