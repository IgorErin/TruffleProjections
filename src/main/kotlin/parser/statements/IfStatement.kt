package parser.statements

import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class IfStatement(@Child val  exp: Expression,
                  @Child val firstJumpLabel: JumpStatement,
                  @Child val secondJumpLabel: JumpStatement) : Statement, Node() {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitIfStatement(this)
    }

    override fun execute(args: IntArray): Int {
        return if (exp.execute(args) == 1) {
            firstJumpLabel.execute(args)
        } else {
            secondJumpLabel.execute(args)
        }
    }
}
