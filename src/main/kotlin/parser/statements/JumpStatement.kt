package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node
import exceptions.ParserException

class JumpStatement(val name: String, @Child var label: Statement? = null) : Statement, Node() {
    override fun execute(virtualFrame: VirtualFrame): Int {
        return label?.execute(virtualFrame) ?: throw ParserException(name, TODO())
    }
}
