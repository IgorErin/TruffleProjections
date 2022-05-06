package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame

class LabelStatement(@Child val stmt: Statement) : Statement() {
    override fun execute(frame: VirtualFrame): Int {
        return stmt.execute(frame)
    }
}