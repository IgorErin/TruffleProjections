package parser.expressions

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node
import lexer.SemanticValue

abstract class  PrimeExp : Expression() {
     abstract override fun execute(frame: VirtualFrame): Int
}
