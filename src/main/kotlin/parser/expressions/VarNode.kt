package parser.expressions

import com.oracle.truffle.api.frame.FrameSlot
import com.oracle.truffle.api.frame.VirtualFrame

class VarNode(private val slot: FrameSlot) : PrimeExp() {
    /*override fun execute(virtualFrame: VirtualFrame): Int {
        return virtualFrame.getInt(index)
    }*/

    override fun execute(virtualFrame: VirtualFrame): Int {
        return virtualFrame.getInt(slot)
    }
}
