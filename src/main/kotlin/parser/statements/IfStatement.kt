package parser.statements

import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class IfStatement(val  exp: Expression,
                  val firstJumpLabel: JumpStatement,
                  val secondJumpLabel: JumpStatement
                  ) : Statement, Node() {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitIfStatement(this)
    }
}
