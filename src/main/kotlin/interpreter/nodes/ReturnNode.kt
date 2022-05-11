package interpreter.nodes

import interpreter.Environment
import interpreter.nodes.expressions.Expression

class ReturnNode(val node: Expression) : Statement {
    override fun execute(env: Environment): Any {
        return node.execute(env)
    }
}
