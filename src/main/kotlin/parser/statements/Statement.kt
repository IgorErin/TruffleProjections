package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame

interface Statement {
    //fun <T> accept(visitor: StmtVisitor<T>): T
    fun execute(virtualFrame: VirtualFrame): Int
}
