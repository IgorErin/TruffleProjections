package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import parser.expressions.Expression

class ExpStatement(@Child val exp: Expression) : Statement() {
    override fun execute(frame: VirtualFrame): Int {
        return exp.execute(frame)
    }
}
