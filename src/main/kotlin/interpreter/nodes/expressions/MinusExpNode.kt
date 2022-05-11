package interpreter.nodes.expressions

import interpreter.Environment

class MinusExpNode(val expNode: Expression) : Expression {
    override fun execute(env: Environment): Any {
        return - (expNode.execute(env) as? Int ?: TODO("cast to Int exception"))
    }
}
