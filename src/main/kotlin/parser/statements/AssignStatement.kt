package parser.statements

import parser.expressions.Expression

class AssignStatement(val nameOfVariable: String, val exp: Expression) : Statement {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitAssignStatement(this)
    }
}
