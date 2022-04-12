package parser.statements

class JumpStatement(val name: String, val line: Int) : Statement {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitJumpStatement(this)
    }
}
