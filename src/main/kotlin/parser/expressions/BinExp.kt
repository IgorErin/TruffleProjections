package parser.expressions

import lexer.Token

class BinExp(left: Expression, operator: Token, right: Expression) : Expression {
    var left: Expression
    var operator: Token
    var right: Expression

    init {
        this.left = left
        this.right = right
        this.operator = operator
    }

    override fun <K> accept(visitor: Visitor<K>): K {
        return visitor.visitBinExp(this)
    }
}
