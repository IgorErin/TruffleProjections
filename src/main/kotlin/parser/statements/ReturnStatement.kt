package parser.statements

import com.oracle.truffle.api.nodes.Node
import parser.expressions.Expression

class ReturnStatement(val exp: Expression) : Statement, Node() {
    override fun <T> accept(visitor: StmtVisitor<T>): T {
        return visitor.visitReturnStatement(this)
    }
}
