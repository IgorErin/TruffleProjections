package parser.expressions

import com.oracle.truffle.api.nodes.Node
import lexer.Token

class BinExp(@Child val left: Expression, val operator: Token, @Child val right: Expression) : Expression, Node() {
    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitBinExp(this)
    }
}
