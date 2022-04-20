package parser.statements

import com.oracle.truffle.api.nodes.Node
import exceptions.ParserException

class JumpStatement(val name: String, val line: Int, @Child var label: Statement? = null) : Statement, Node() {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitJumpStatement(this)
    }

    override fun execute(args: IntArray): Int {
        return label?.execute(args) ?: throw ParserException(name, line)
    }
}
