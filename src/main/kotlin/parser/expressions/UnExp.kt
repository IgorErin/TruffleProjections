package parser.expressions

import com.oracle.truffle.api.nodes.Node
import lexer.Token

class UnExp(val operator: Token? = null,@Child  val right: Expression) : Expression, Node() {
    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitUnExp(this)
    }
}
