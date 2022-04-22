package parser.statements

import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class IfStatement(@Child var  exp: Expression,
                  @Child var firstJumpLabel: JumpStatement,
                  @Child var secondJumpLabel: JumpStatement) : Statement, Node() {
    override fun execute(args: IntArray): Int {
        return if (exp.execute(args) == 1) {
            firstJumpLabel.execute(args)
        } else {
            secondJumpLabel.execute(args)
        }
    }
}
