package interpreter.nodes

import interpreter.Environment

class LabelNode(val listOfNodes: MutableList<SimpleNode>) : Statement{
    override fun execute(env: Environment): Any {
        for (i in listOfNodes.indices - 1) {
            listOfNodes[i].execute(env)
        }

        return listOfNodes.last().execute(env)
    }
}
