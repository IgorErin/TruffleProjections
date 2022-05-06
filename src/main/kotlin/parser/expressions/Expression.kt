package parser.expressions

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node

abstract class Expression : Node() {
    //fun <T> accept(visitor: ExpVisitor<T>): T
    abstract fun execute(frame: VirtualFrame): Int
}
