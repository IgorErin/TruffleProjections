package parser.statements

import parser.expressions.Expression

class ExpStatement(val exp: Expression) : Statement {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitExpStatement(this)
    }
}
