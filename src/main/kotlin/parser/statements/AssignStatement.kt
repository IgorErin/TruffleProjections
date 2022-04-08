package parser.statements

import parser.expressions.Expression

class AssignStatement(variable: VarStatement, exp: Expression) : Statement {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitAssignStatement(this)
    }
}