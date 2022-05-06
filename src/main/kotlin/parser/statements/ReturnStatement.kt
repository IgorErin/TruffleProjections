package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import parser.expressions.Expression

class ReturnStatement(@Child var exp: Expression) : Statement() {
    override fun execute(frame: VirtualFrame): Int {
        return exp.execute(frame)
    }
}
