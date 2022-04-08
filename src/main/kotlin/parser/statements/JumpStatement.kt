package parser.statements

import parser.expressions.Expression

class JumpStatement(val exp: Expression) : Statement {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitJumpStatement(this)
    }
}