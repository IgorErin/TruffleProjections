package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement

class LabelNode(private val listOfNodes: MutableList<Statement>, val name: String) : Statement {
    override fun execute(env: Environment): Any {
        for (i in 0..listOfNodes.size - 2) {
            listOfNodes[i].execute(env)
        }

        return listOfNodes.last().execute(env)
    }
}
