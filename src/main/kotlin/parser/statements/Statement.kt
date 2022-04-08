package parser.statements

interface Statement {
    fun <T> accept(visitor: StmtVisitor<T>): T
}