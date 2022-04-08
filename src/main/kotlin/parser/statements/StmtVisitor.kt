package parser.statements

interface StmtVisitor<T> {
    fun visitExpStatement(exp: ExpStatement): T
    fun visitReturnStatement(exp: ReturnStatement): T
    fun visitJumpStatement(exp: JumpStatement): T
    fun visitIfStatement(exp: IfStatement): T
    fun visitVarStatement(exp: VarStatement): T
    fun visitAssignStatement(exp: AssignStatement): T
}