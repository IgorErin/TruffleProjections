package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class AssignStatement(private val index: Int, @Child var exp: Expression, @Child val rightExp: Statement) : Statement, Node() {
    override fun execute(virtualFrame: VirtualFrame): Int {
        virtualFrame.setInt(index, exp.execute(virtualFrame))
        return rightExp.execute(virtualFrame)
    }
}
