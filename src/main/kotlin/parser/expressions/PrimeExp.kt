package parser.expressions

import lexer.Token

class PrimeExp(`var`: Token) : Expression {
    var name: String?
    var value = 0

    init {
        name = `var`.name
    }

    override fun <K> accept(visitor: Visitor<K>): K {
        return visitor.visitPrimeExp(this)
    }
}
