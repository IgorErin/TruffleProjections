package parser.expressions

import com.oracle.truffle.api.frame.VirtualFrame

interface Expression {
    //fun <T> accept(visitor: ExpVisitor<T>): T
    fun execute(virtualFrame: VirtualFrame): Int
}
