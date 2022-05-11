package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement

class InvokeNode(val name: String) : Statement {
    override fun execute(env: Environment): Any {
        return env.getValue(name) as? LabelNode ?: TODO("not label")
    }
}