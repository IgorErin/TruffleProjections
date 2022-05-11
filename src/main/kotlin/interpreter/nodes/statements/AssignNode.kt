package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement
import interpreter.nodes.expressions.Expression

class AssignNode(private val variable: VarNode, val expNode: Expression) : Statement {
    override fun execute(env: Environment): Any {
        return env.setValue(variable.name, expNode.execute(env))
    }
}
