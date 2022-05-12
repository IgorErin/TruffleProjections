package interpreter.nodes.statements

import exceptions.EvalException
import interpreter.Environment
import interpreter.nodes.Statement

class DefineNode(private val listOfNodes: MutableList<Statement>) : Statement {
    override fun execute(env: Environment): Any {
        val firsNode = listOfNodes[0] as? SymbolNode ?: throw EvalException("cast to VarNode")
        val newLabel = LabelNode(listOfNodes.subList(1, listOfNodes.size), firsNode.name)

        return env.setValue(firsNode.name, newLabel)
    }
}
