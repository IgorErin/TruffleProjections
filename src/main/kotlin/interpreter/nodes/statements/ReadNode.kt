package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement

class ReadNode(private val listOfVar: MutableList<WriteVarNode>) : Statement{
    override fun execute(env: Environment): Any {
        for (i in listOfVar) {
            i.execute(env)
        }

        return Unit
    }
}