package parser.expressions

import com.oracle.truffle.api.nodes.Node
import lexer.SemanticValue

interface PrimeExp : Expression {
    override fun execute(args: IntArray): Int
}
