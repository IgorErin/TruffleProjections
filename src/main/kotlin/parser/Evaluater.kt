package parser

import exceptions.ParserException
import environments.EnvironmentForVar
import exceptions.EvalException
import lexer.SemanticValue.*
import parser.expressions.*
import parser.statements.*
import java.io.IOException

class Evaluater(parser: Parser) : ExpVisitor<Int>, StmtVisitor<Unit> {
    private val mapOfBlocks = parser.mapOfBlocks
    private val listOfLabels = parser.listOfLabels
    private val listOfVar = parser.listOfVariables

    private val environment = EnvironmentForVar()

    fun eval() {
        val list = mutableListOf<Int>()
        for (name in listOfVar) {
            val value = readLine()?.toIntOrNull() ?: throw IOException()
            list.add(value)
            environment.assign(name, value)
        }


        evalLabel(listOfLabels[0])
    }

    private fun evalLabel(name: String) {
        for (stmt in mapOfBlocks[name] ?: throw IndexOutOfBoundsException()) {
            stmt.accept(this)
        }
    }

    override fun visitBinExp(exp: BinExp): Int {
        when (exp.operator.value) {
            EQ -> {
                if (exp.left.accept(this) == exp.right.accept(this)){
                    return 1
                }

                return 0
            }
            PLUS -> {
                return exp.left.accept(this) + exp.right.accept(this)
            }
            MINUS -> {
                return exp.left.accept(this) - exp.right.accept(this)
            }
            ASTER -> {
                return exp.left.accept(this) * exp.right.accept(this)
            }
            GT -> {
                return if (exp.left.accept(this) > exp.right.accept(this)) {
                    1
                } else {
                    0
                }
            }
            LT -> {
                return if (exp.left.accept(this) < exp.right.accept(this)) {
                    1
                } else {
                    0
                }
            }
            GOEQ -> {
                return if (exp.left.accept(this) >= exp.right.accept(this)) {
                    1
                } else {
                    0
                }
            }
            LOEQ -> {
                return if (exp.left.accept(this) <= exp.right.accept(this)) {
                    1
                } else {
                    0
                }
            }
            else -> {}
        }

        throw ParserException(exp.operator.name, exp.operator.line)
    }

    override fun visitUnExp(exp: UnExp): Int {
        if (exp.operator?.value == null) {
            return exp.right.accept(this)
        }else if (exp.operator.value == MINUS) {
            return -exp.right.accept(this)
        }

        throw ParserException(exp.operator.name, exp.operator.line)
    }

    override fun visitPrimeExp(exp: PrimeExp): Int {
        return when (exp.value) {
            INT -> exp.name.toInt()
            VAR -> environment.get(exp.name) ?: throw EvalException("var is not bound", exp.name, exp.line)
            else -> throw ParserException(exp.name, exp.line)
        }
    }

    override fun visitExpStatement(stmt: ExpStatement) {
        stmt.exp.accept(this)
    }

    override fun visitReturnStatement(stmt: ReturnStatement) {
        println(stmt.exp.accept(this))
    }

    override fun visitJumpStatement(stmt: JumpStatement) {
        if (stmt.name in listOfLabels) {
            evalLabel(stmt.name)
            return
        }

        throw EvalException("label is not bound", stmt.name, stmt.line)
    }

    override fun visitIfStatement(stmt: IfStatement) {
        when (stmt.exp.accept(this)) {
            0 -> stmt.secondJumpLabel.accept(this)
            else -> stmt.firstJumpLabel.accept(this)
        }
    }

    override fun visitAssignStatement(stmt: AssignStatement) {
        environment.assign(stmt.nameOfVariable, stmt.exp.accept(this))
    }
}
