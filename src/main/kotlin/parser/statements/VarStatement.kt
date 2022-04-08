package parser.statements

class VarStatement(val name: String) : Statement {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitVarStatement(this)
    }
}