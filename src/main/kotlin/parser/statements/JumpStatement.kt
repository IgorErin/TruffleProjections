package parser.statements

import com.oracle.truffle.api.nodes.Node

class JumpStatement(val name: String, val line: Int) : Statement, Node() {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitJumpStatement(this)
    }
}
