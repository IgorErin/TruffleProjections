package parser.expressions

interface Expression {
    fun <R> accept(visitor: Visitor<R>): R
}
