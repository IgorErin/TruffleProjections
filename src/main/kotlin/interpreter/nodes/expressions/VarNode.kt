package interpreter.nodes.expressions

import interpreter.Environment

class VarNode(val name: String) : Expression {
    override fun execute(env: Environment): Any {
        return env.getValue(name);
    }
}
