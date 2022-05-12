package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement

class InvokeNode(val name: String) : Statement {
    override fun execute(env: Environment): Any {
        println("label: $name")
        return (env.getValue(name) as? LabelNode ?: TODO("cast to label exception")).execute(env)
    }
}
