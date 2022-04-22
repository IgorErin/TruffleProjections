package parser.statements

import com.oracle.truffle.api.nodes.Node.*

class LabelStatement(@Child val stmt: Statement) : Statement {
    override fun execute(args: IntArray): Int {
        return stmt.execute(args)
    }
}