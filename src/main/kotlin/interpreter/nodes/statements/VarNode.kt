package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement
import interpreter.nodes.expressions.Expression

class VarNode(val name: String) : Statement, Expression {
    override fun execute(env: Environment): Any {
        return env.getValue(name)
    }
}
