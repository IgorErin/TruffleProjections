package parser.expressions

import lexer.Token

class BinExp(var left: Expression, operator: Token, right: Expression) : Expression {
    var operator: Token
    var right: Expression

    init {
        this.right = right
        this.operator = operator
    }

    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitBinExp(this)
    }
}
