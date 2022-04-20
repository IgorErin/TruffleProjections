package nodes

import com.oracle.truffle.api.TruffleLanguage
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.RootNode

class FunNode(@Child var body: MyNode, language: TruffleLanguage<*>? = null) : RootNode(language) {
    override fun execute(frame: VirtualFrame?): Any {
        return body.execute(frame?.arguments?.get(0) as? IntArray ?: intArrayOf())
    }
}