package parser.expressions

import com.oracle.truffle.api.frame.VirtualFrame

class VarNode(private val index: Int) : PrimeExp {
    override fun execute(virtualFrame: VirtualFrame): Int {
        return virtualFrame.getInt(index)
    }
}
