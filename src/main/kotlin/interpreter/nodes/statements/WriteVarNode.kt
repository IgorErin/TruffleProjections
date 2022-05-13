package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement
import interpreter.nodes.expressions.Expression

class WriteVarNode(private val name: String, private val exp: Expression) : Statement{
    override fun execute(env: Environment): Any {
        return env.setValue(name, exp.execute(env))
    }
}
