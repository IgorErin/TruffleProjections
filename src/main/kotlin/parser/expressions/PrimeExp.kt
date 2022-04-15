package parser.expressions

import com.oracle.truffle.api.nodes.Node
import lexer.SemanticValue

class PrimeExp(val line: Int, val name: String, val value: SemanticValue) : Expression, Node() {
    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitPrimeExp(this)
    }
}
