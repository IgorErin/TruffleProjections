package interpreter.nodes.expressions

import interpreter.Environment

class BinExpNode(val leftNode: Expression, val rightNode: Expression) : Expression {
    override fun execute(env: Environment): Any {
        val leftNodeResult = leftNode.execute(env) as? Int ?: TODO("cast to Int error")
        val rightNodeResult = leftNode.execute(env) as? Int ?: TODO("cast to Int error")

        return leftNodeResult + rightNodeResult
    }
}
