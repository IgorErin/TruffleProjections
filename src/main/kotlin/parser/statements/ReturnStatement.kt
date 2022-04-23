package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class ReturnStatement(@Child var exp: Expression) : Statement, Node() {
    override fun execute(virtualFrame: VirtualFrame): Int {
        return exp.execute(virtualFrame)
    }
}
