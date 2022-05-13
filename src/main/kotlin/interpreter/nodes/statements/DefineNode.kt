package interpreter.nodes.statements

import exceptions.EvalException
import interpreter.Environment
import interpreter.nodes.Statement

class DefineNode(private val labelNode: LabelNode) : Statement {
    override fun execute(env: Environment): Any {
        return env.setValue(labelNode.name, labelNode.execute(env))
    }
}
