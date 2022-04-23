package parser.expressions

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node
import exceptions.ParserException
import lexer.SemanticValue
import lexer.Token

class BinExp(@Child val left: Expression, val operator: Token, @Child val right: Expression) : Expression, Node() {
    override fun execute(virtualFrame: VirtualFrame): Int {
        when (operator.value) {
            SemanticValue.EQ -> {
                if (left.execute(virtualFrame) == right.execute(virtualFrame)){
                    return 1
                }

                return 0
            }
            SemanticValue.PLUS -> {
                return left.execute(virtualFrame) + right.execute(virtualFrame)
            }
            SemanticValue.MINUS -> {
                return left.execute(virtualFrame) - right.execute(virtualFrame)
            }
            SemanticValue.ASTER -> {
                return left.execute(virtualFrame) * right.execute(virtualFrame)
            }
            SemanticValue.GT -> {
                return if (left.execute(virtualFrame) > right.execute(virtualFrame)) {
                    1
                } else {
                    0
                }
            }
            SemanticValue.LT -> {
                return if (left.execute(virtualFrame) < right.execute(virtualFrame)) {
                    1
                } else {
                    0
                }
            }
            SemanticValue.GOEQ -> {
                return if (left.execute(virtualFrame) >= right.execute(virtualFrame)) {
                    1
                } else {
                    0
                }
            }
            SemanticValue.LOEQ -> {
                return if (left.execute(virtualFrame) <= right.execute(virtualFrame)) {
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
