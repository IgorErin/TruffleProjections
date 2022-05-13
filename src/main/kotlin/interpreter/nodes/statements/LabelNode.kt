package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement
import interpreter.types.Function

class LabelNode(private val listOfNodes: MutableList<Statement>, val name: String) : Statement {
    override fun execute(env: Environment): Any {
        return Function(listOfNodes)
    }
}
