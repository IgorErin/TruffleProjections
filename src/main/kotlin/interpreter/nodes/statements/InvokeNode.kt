package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement
import interpreter.types.Function

class InvokeNode(val name: String) : Statement {
    override fun execute(env: Environment): Any {
        return (env.getValue(name) as? Function ?: TODO("cast to Function exception")).execute(env)
    }
}
