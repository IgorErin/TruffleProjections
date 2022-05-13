package interpreter.types

import interpreter.Environment
import interpreter.Executable
import interpreter.nodes.Statement

class Function(private val bodyNodes: MutableList<Statement>) : Executable {
    override fun execute(env: Environment): Any {
        for (i in 0..bodyNodes.size - 2) {
            bodyNodes[i].execute(env)
        }

        return bodyNodes.last().execute(env)
    }
}