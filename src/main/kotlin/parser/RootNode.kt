package parser

import com.oracle.truffle.api.TruffleLanguage
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.RootNode
import parser.statements.Statement

class MyRootNode(private val stmt: Statement, language: TruffleLanguage<*>? = null) : RootNode(language) {
    override fun execute(frame: VirtualFrame?): Any {
        return stmt.execute(frame ?: throw TODO())
    }
}