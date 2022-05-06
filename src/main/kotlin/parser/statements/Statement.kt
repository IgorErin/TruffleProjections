package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node

abstract class Statement: Node() {
    //fun <T> accept(visitor: StmtVisitor<T>): T
    abstract fun execute(frame: VirtualFrame): Int
}
