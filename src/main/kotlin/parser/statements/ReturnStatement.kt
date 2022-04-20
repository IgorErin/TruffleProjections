package parser.statements

import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class ReturnStatement(@Child val exp: Expression) : Statement, Node() {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitReturnStatement(this)
    }

    override fun execute(args: IntArray): Int {
        println(exp.execute(args))
        return 0
    }
}
