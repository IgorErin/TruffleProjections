package parser.statements

import parser.expressions.Expression

class ReturnStatement(val exp: Expression) : Statement {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitReturnStatement(this)
    }
}
