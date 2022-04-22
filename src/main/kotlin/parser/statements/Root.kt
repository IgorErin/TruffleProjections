package parser.statements

import com.oracle.truffle.api.TruffleLanguage
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.Node.*
import com.oracle.truffle.api.nodes.RootNode

class Root(@Child var stmt: Statement, language: TruffleLanguage<*>? = null) : RootNode(language) {
    override fun execute(frame: VirtualFrame?): Any {
        return stmt.execute(frame?.arguments?.get(0) as? IntArray ?: intArrayOf())
    }
}