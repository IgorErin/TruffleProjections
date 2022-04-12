package parser.expressions

import lexer.Token

class BinExp(val left: Expression, val operator: Token, val right: Expression) : Expression {
    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitBinExp(this)
    }
}
