package parser.expressions

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node
import lexer.SemanticValue
import lexer.Token

class UnExp(val operator: Token? = null, @Child  val right: Expression) : Expression, Node() {
    override fun execute(virtualFrame: VirtualFrame): Int {
        return if (operator?.value == SemanticValue.MINUS) {
            -right.execute(virtualFrame)
        } else {
            right.execute(virtualFrame)
        }
    }
}
