package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node.*

class LabelStatement(@Child val stmt: Statement) : Statement {
    override fun execute(virtualFrame: VirtualFrame): Int {
        return stmt.execute(virtualFrame)
    }
}