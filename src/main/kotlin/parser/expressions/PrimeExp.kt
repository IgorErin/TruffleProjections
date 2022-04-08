package parser.expressions

import lexer.SemanticValue
import lexer.Token

class PrimeExp(token : Token) : Expression {
    var line: Int
    var name: String
    var value: SemanticValue

    init {
        this.line = token.line
        this.name = token.name
        this.value = token.value
    }

    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitPrimeExp(this)
    }
}
