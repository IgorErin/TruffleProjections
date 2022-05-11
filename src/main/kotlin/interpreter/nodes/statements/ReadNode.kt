package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement
import java.io.IOException

class ReadNode(private val listOfNodes: MutableList<VarNode>) : Statement {
    override fun execute(env: Environment): Any {
        for (i in listOfNodes) {
            val input = readLine()

            env.setValue(i.name, input?.toInt() ?: throw IOException("$input must be number"))
        }

        return Unit
    }
}