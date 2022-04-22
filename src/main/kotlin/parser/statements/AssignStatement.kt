package parser.statements

import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class AssignStatement(private val index: Int, @Child var exp: Expression, @Child val rightExp: Statement) : Statement, Node() {
    override fun execute(args: IntArray): Int {
        args[index] = exp.execute(args)
        return rightExp.execute(args)
    }
}
