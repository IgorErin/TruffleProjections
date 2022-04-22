package parser.statements

import com.oracle.truffle.api.nodes.Node
import exceptions.ParserException

class JumpStatement(val name: String, @Child var label: Statement? = null) : Statement, Node() {
    override fun execute(args: IntArray): Int {
        return label?.execute(args) ?: throw ParserException(name, TODO())
    }
}
