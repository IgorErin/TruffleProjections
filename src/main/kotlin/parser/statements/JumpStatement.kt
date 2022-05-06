package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import exceptions.ParserException

class JumpStatement(val name: String, @Child var label: Statement? = null) : Statement() {
    override fun execute(frame: VirtualFrame): Int {
        return label?.execute(frame) ?: throw ParserException(name, TODO())
    }
}
