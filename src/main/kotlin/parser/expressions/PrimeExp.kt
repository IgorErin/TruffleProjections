package parser.expressions

import lexer.SemanticValue

class PrimeExp(val line: Int, val name: String, val value: SemanticValue) : Expression {
    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitPrimeExp(this)
    }
}
