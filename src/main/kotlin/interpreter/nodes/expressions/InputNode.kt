package interpreter.nodes.expressions

import interpreter.Environment
import java.io.IOException

class InputNode : Expression {
    override fun execute(env: Environment): Any {
        val input = readLine()

        return input?.toInt() ?: throw IOException("$input must be number")
    }
}
