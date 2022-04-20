package parser.statements

import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class AssignStatement(val nameOfVariable: String, @Child val exp: Expression) : Statement, Node() {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitAssignStatement(this)
    }

    override fun execute(args: IntArray): Int {
        TODO()
    }
}
