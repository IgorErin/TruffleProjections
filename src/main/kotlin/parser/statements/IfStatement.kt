package parser.statements

import parser.expressions.Expression

class IfStatement(val  exp: Expression,
                  val firstJumpLabel: JumpStatement,
                  val secondJumpLabel: JumpStatement
                  ) : Statement {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitIfStatement(this)
    }
}
