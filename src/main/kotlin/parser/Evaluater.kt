package parser

import lexer.SemanticValue.*
import parser.expressions.*
import parser.statements.*

class Evaluater : ExpVisitor<Int>, StmtVisitor<Unit> {
    fun eval(stmt: Statement) {
        stmt.accept(this)
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
        if (exp.value == INT) {
            return exp.name.toInt()
        }

        //TODO(for variable)
        throw ParserException(exp.name, exp.line)
    }

    override fun visitExpStatement(stmt: ExpStatement): Unit {
        stmt.exp.accept(this)
    }

    override fun visitReturnStatement(stmt: ReturnStatement): Unit {
        println(stmt.exp.accept(this))
    }

    override fun visitJumpStatement(exp: JumpStatement) {
        TODO("Not yet implemented")
    }

    override fun visitIfStatement(exp: IfStatement) {
        TODO("Not yet implemented")
    }

    override fun visitVarStatement(exp: VarStatement) {
        TODO("Not yet implemented")
    }

    override fun visitVarStatement(exp: AssignStatement) {
        TODO("Not yet implemented")
    }
}