package interpreter.nodes.expressions

import interpreter.Environment

class BooleanNode(val value: Boolean) : Expression {
    override fun execute(env: Environment): Any {
        return value
    }
}
