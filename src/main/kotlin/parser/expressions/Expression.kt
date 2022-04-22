package parser.expressions

interface Expression {
    //fun <T> accept(visitor: ExpVisitor<T>): T
    fun execute(args: IntArray): Int
}
