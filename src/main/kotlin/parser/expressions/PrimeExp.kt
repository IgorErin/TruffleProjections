package parser.expressions

import com.oracle.truffle.api.nodes.Node
import lexer.SemanticValue

class PrimeExp(val line: Int, val name: String, val value: SemanticValue, val index: Int = 0) : Expression, Node() {
    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitPrimeExp(this)
    }

    override fun execute(args: IntArray): Int {
        return args[index]
    }
}
