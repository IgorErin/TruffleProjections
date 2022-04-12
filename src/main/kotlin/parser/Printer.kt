package parser

import java.lang.StringBuilder
import lexer.Token
import parser.expressions.*
import parser.statements.*

class Printer : ExpVisitor<String>, StmtVisitor<String?> {

    fun print(stmt: Statement?): String {
        return stmt?.accept(this) ?: "null"
    }

    override fun visitBinExp(exp: BinExp): String {
        return printNested(exp.operator, exp.left, exp.right)
    }

    override fun visitUnExp(exp: UnExp): String {
        return printNested(exp.operator, exp.right)
    }

    override fun visitPrimeExp(exp: PrimeExp): String {
        return exp.name
    }

    private fun printNested(operator: Token?, vararg exps: Expression): String {
        val string = StringBuilder()
        string.append("(").append(operator!!.name).append(" (")
        for (exp in exps) {
            string.append(exp.accept(this)).append(" ")
        }
        string.append("))")
        return string.toString()
    }

    override fun visitExpStatement(stmt: ExpStatement): String {
        return stmt.exp.accept(this)
    }

    override fun visitReturnStatement(stmt: ReturnStatement): String {
        return "return ${stmt.exp.accept(this)}"
    }

    override fun visitJumpStatement(stmt: JumpStatement): String {
        return "jump to ${stmt.name}"
    }

    override fun visitIfStatement(stmt: IfStatement): String {
        return "if ${stmt.exp.accept(this)} ${stmt.firstJumpLabel.name} else ${stmt.firstJumpLabel.name}"
    }

    /*override fun visitVarStatement(stmt: VarStatement): String {
        return stmt.name
    }*/

    override fun visitAssignStatement(stmt: AssignStatement): String {
        return "${stmt.nameOfVariable} = ${stmt.exp.accept(this)}"
    }
}
