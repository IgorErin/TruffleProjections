package parser.expressions

import lexer.Token

class UnExp(var operator: Token, var right: Expression) : Expression {
    override fun <K> accept(visitor: Visitor<K>): K {
        return visitor.visitUnExp(this)
    }
}
