package parser.statements

import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class ExpStatement(@Child val exp: Expression) : Statement, Node() {
    override fun execute(args: IntArray): Int {
        return exp.execute(args)
    }
}
