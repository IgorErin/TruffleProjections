package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement
import interpreter.nodes.expressions.Expression

class IfNode(private val condition: Expression,
             private val thenNode: InvokeNode,
             private val elseNode: InvokeNode
             ) : Statement {
    override fun execute(env: Environment): Any {
        val conditionValue = condition.execute(env) as? Boolean ?: TODO("cast to Boolean exception")

        if (conditionValue) {
            return thenNode.execute(env)
        }

        return elseNode.execute(env)
    }
}
