package interpreter.nodes

import interpreter.Environment
import interpreter.nodes.expressions.Expression

class IfNode(val condition: Expression, val thenNode: Expression, val elseNode: Expression) : Statement{
    override fun execute(env: Environment): Any {
        val conditionValue = condition.execute(env) as? Boolean ?: TODO("cast to Boolean exception")

        if (conditionValue) {
            return thenNode.execute(env)
        }

        return elseNode.execute(env)
    }
}
