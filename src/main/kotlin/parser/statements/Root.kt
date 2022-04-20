package parser.statements

import com.oracle.truffle.api.nodes.Node.*

class Root(@Child val stmt: Statement) : Statement {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return stmt.accept(visitor)
    }

    override fun execute(args: IntArray): Int {
        TODO("Not yet implemented")
    }
}