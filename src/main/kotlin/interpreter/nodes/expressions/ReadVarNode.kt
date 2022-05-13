package interpreter.nodes.expressions

import interpreter.Environment
import interpreter.nodes.expressions.Expression

class ReadVarNode(val name: String) : Expression {
    override fun execute(env: Environment): Any {
        return env.getValue(name)
    }
}