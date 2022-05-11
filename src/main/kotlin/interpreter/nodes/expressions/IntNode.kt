package interpreter.nodes.expressions

import interpreter.Environment
import interpreter.nodes.Statement

class IntNode(val value: Int) : Expression, Statement {
    override fun execute(env: Environment): Any {
        return value
    }
}
