package interpreter.nodes.expressions

import exceptions.ParserException
import interpreter.Environment

class BinExpNode(val leftNode: Expression, val operation: String, val rightNode: Expression) : Expression {
    override fun execute(env: Environment): Any {
        val leftNodeResult = leftNode.execute(env) as? Int ?: TODO("cast to Int error")
        val rightNodeResult = rightNode.execute(env) as? Int ?: TODO("cast to Int error")

        return when(operation) {
            "+" -> leftNodeResult + rightNodeResult
            "-" -> leftNodeResult - rightNodeResult
            "*" -> leftNodeResult * rightNodeResult
            "==" -> leftNodeResult == rightNodeResult
            "!=" -> leftNodeResult != rightNodeResult
            ">" -> leftNodeResult > rightNodeResult
            "<" -> leftNodeResult < rightNodeResult
            ">=" -> leftNodeResult >= rightNodeResult
            "<=" -> leftNodeResult <= rightNodeResult

            else -> throw ParserException(operation)
        }
    }
}
