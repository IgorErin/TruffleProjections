package parser

import java.lang.StringBuilder
import lexer.Token
import parser.expressions.*

class Printer : Visitor<String?> {
    fun print(exp: Expression?): String {
        return exp?.accept(this) ?: "null"
    }

    override fun visitBinExp(exp: BinExp): String {
        return printNested(exp.operator, exp.left, exp.right)
    }

    override fun visitUnExp(exp: UnExp): String {
        return printNested(exp.operator, exp.right)
    }

    override fun visitPrimeExp(exp: PrimeExp): String? {
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
}
