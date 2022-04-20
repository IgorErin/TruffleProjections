package parser.expressions

import com.oracle.truffle.api.nodes.Node
import exceptions.ParserException
import lexer.SemanticValue
import lexer.Token

class BinExp(@Child val left: Expression, val operator: Token, @Child val right: Expression) : Expression, Node() {
    override fun <T> accept(visitor: ExpVisitor<T>): T {
        return visitor.visitBinExp(this)
    }

    override fun execute(args: IntArray): Int {
        when (operator.value) {
            SemanticValue.EQ -> {
                if (left.execute(args) == right.execute(args)){
                    return 1
                }

                return 0
            }
            SemanticValue.PLUS -> {
                return left.execute(args) + right.execute(args)
            }
            SemanticValue.MINUS -> {
                return left.execute(args) - right.execute(args)
            }
            SemanticValue.ASTER -> {
                return left.execute(args) * right.execute(args)
            }
            SemanticValue.GT -> {
                return if (left.execute(args) > right.execute(args)) {
                    1
                } else {
                    0
                }
            }
            SemanticValue.LT -> {
                return if (left.execute(args) < right.execute(args)) {
                    1
                } else {
                    0
                }
            }
            SemanticValue.GOEQ -> {
                return if (left.execute(args) >= right.execute(args)) {
                    1
                } else {
                    0
                }
            }
            SemanticValue.LOEQ -> {
                return if (left.execute(args) <= right.execute(args)) {
                    1
                } else {
                    0
                }
            }
            else -> {}
        }

        throw ParserException(operator.name, operator.line)
    }
}
