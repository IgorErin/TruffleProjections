package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import parser.expressions.Expression

class AssignStatement(private val index: Int, @Child var exp: Expression, @Child val rightExp: Statement) : Statement() {
    override fun execute(frame: VirtualFrame): Int {
        frame.setInt(index, exp.execute(frame))
        return rightExp.execute(frame)
    }
}
