package parser.statements

interface StmtVisitor<T> {
    fun visitExpStatement(stmt: ExpStatement): T
    fun visitReturnStatement(stmt: ReturnStatement): T
    fun visitJumpStatement(stmt: JumpStatement): T
    fun visitIfStatement(stmt: IfStatement): T
    fun visitAssignStatement(stmt: AssignStatement): T
}