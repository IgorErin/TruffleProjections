package interpreter.nodes.statements

import interpreter.Environment
import interpreter.nodes.Statement

class SymbolNode(val name: String) : Statement {
    override fun execute(env: Environment): Any {
        return name
    }
}