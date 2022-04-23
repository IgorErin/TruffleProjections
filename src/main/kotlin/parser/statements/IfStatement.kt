package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class IfStatement(@Child var  exp: Expression,
                  @Child var firstJumpLabel: JumpStatement,
                  @Child var secondJumpLabel: JumpStatement) : Statement, Node() {
    override fun execute(virtualFrame: VirtualFrame): Int {
        return if (exp.execute(virtualFrame) == 1) {
            print(exp.execute(virtualFrame))
            firstJumpLabel.execute(virtualFrame)
        } else {
            secondJumpLabel.execute(virtualFrame)
        }
    }
}
