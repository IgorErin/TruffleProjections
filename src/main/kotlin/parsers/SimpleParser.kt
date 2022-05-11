package parsers

import exceptions.ParserException
import interpreter.nodes.Statement
import interpreter.nodes.expressions.BinExpNode
import interpreter.nodes.expressions.Expression
import interpreter.nodes.expressions.MinusExpNode
import lexer.Values
import lexer.Values.*
import lexer.Token
import interpreter.nodes.expressions.IntNode
import interpreter.nodes.statements.*

class SimpleParser(private val tokens: List<Token>) {
    private var position = 0

    fun readProgram(): LabelNode {
        val listOfNodes = mutableListOf<Statement>()

        if (find(READ)) {
            listOfNodes.add(readReadBlock())
        }

        while (find(DEFINE)) {
            listOfNodes.add(readDefineBlock())
        }

        while(find(VAR)) {
            listOfNodes.add(VarNode(previousToken().name))
        }

        println("---------------------------------------------------------")

        return LabelNode(listOfNodes, "main")
    }

    private fun readReadBlock(): ReadNode {
        val listOfVarNodes = mutableListOf<VarNode>()

        while (find(VAR)) {
            listOfVarNodes.add(VarNode(previousToken().name))

            when {
                find(COMMA) -> {}
                find(SEMCOL) -> break
                else -> throw ParserException("strange token: ${current().name}")
            }
        }

        return ReadNode(listOfVarNodes)
    }

    private fun readDefineBlock(): DefineNode {
        if(!find(LABEL)) throw ParserException(TODO("define block"))

        val listOfStatements = mutableListOf<Statement>(VarNode(previousToken().name)) // add first statement

        while (find(VAR)) {
            listOfStatements.add(readAssignment())
        }

        val lastStatement = when {
            find(IF) -> readIfStatement()
            find(RETURN) -> ReturnNode(readValue())
            find(GOTO) -> readVariable()
            else -> throw ParserException(TODO("last stmt"))
        }
        listOfStatements.add(lastStatement)

        return DefineNode(listOfStatements)
    }

    private fun readAssignment(): Statement {
        val variable = VarNode(previousToken().name)

        if(!find(ASSIGN)) throw ParserException(TODO("assign"))

        val exp = readExp()

        return AssignNode(variable, exp)
    }

    private fun readIfStatement(): Statement {
        val exp = readExp()

        if (!find(GOTO)) throw ParserException(TODO("GOTO"))

        val thenNode = readVariable()

        if (!find(ELSE)) throw ParserException(TODO("ELSE"))

        val elseNode = readVariable()

        return IfNode(exp, thenNode, elseNode)
    }


    private fun readVariable(): VarNode {
        if (!find(VAR)) throw ParserException(TODO("readVar"))

        return VarNode(previousToken().name)
    }

    //---------------------------------------------

    fun readExp(): Expression {
        return equality()
    }

    private fun equality(): Expression {
        var exp: Expression = readCompare()
        while (find(EQ, NOTQE)) {
            val operation = previousToken()
            val rightExp: Expression = readCompare()
            exp = BinExpNode(exp, operation.name, rightExp)
        }

        return exp
    }

    private fun readCompare(): Expression {
        var leftExp: Expression = readTerm()

        while (find(GT, LT, LOEQ, GOEQ)) {
            val operation = previousToken()
            val rightTExp: Expression = readCompare()
            leftExp = BinExpNode(leftExp, operation.name, rightTExp)
        }

        return leftExp
    }

    private fun readTerm(): Expression {
        var leftExp: Expression = readFactor()

        while (find(MINUS, PLUS)) {
            val operation = previousToken()
            val rightExp: Expression = readFactor()
            leftExp = BinExpNode(leftExp, operation.name, rightExp)
        }

        return leftExp
    }

    private fun readFactor(): Expression {
        var leftExp: Expression = readMinus()

        while (find(ASTER)) {
            val operation = previousToken()
            val rightExp: Expression = readMinus()
            leftExp = BinExpNode(leftExp, operation.name, rightExp)
        }

        return leftExp
    }

    private fun readMinus(): Expression {
        if (find(MINUS)) {
            return MinusExpNode(readValue())
        }

        return readValue()
    }

    private fun readValue(): Expression {
        var exp: Expression? = null
        val token = current()

        when {
            find(VAR) -> {
                exp = VarNode(token.name)
            }
            find(INT) -> {
                exp = IntNode(token.name.toInt())
            }
        }

        return exp ?: throw ParserException(current().name)
    }

    private fun find(vararg values: Values?): Boolean {
        for (value in values) {
            if (current().value == value) {
                println(current().name)
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