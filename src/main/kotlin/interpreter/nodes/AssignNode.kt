package interpreter.nodes

import interpreter.Environment
import interpreter.nodes.expressions.Expression

class AssignNode(val name: String, val expNode: Expression) : Statement {
    override fun execute(env: Environment): Any {
        return env.setValue(name, expNode.execute(env))
    }
}
