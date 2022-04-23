package parser.expressions

import com.oracle.truffle.api.frame.VirtualFrame

class ValNode(private val value: Int) : PrimeExp {
    override fun execute(virtualFrame: VirtualFrame): Int {
        return value
    }
}