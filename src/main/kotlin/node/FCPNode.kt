package node

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node
import FCPTypes
import com.oracle.truffle.api.dsl.TypeSystemReference
import com.oracle.truffle.api.instrumentation.GenerateWrapper
import com.oracle.truffle.sl.nodes.SLTypesGen
import type.FCPFunction

@TypeSystemReference(FCPTypes::class)
@GenerateWrapper
abstract class FCPNode: Node() {
     abstract fun execute(frame: VirtualFrame?): Any

     fun executeInt(frame: VirtualFrame): Int {
         return FCPTypesGen.expectLong(this.execute(frame))

         return this.execute(frame) as? Int ?: TODO()
     }

    fun executeFunction(frame: VirtualFrame): FCPFunction {
        return this.execute(frame) as? FCPFunction ?: TODO()
    }
}