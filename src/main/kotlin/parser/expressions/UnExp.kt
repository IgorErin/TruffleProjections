package parser.expressions

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node
import lexer.SemanticValue
import lexer.Token

class UnExp(val operator: Token? = null, @Node.Child val right: Expression) : Expression() {
    override fun execute(frame: VirtualFrame): Int {
        return if (operator?.value == SemanticValue.MINUS) {
            -right.execute(frame)
        } else {
            right.execute(frame)
        }    }
}
