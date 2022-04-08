package parser.expressions

import lexer.Token

class UnExp(val operator: Token? = null, val right: Expression) : Expression {
    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitUnExp(this)
    }
}
