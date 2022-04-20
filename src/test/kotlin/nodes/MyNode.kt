package nodes

import com.oracle.truffle.api.nodes.Node

abstract class MyNode : Node() {
    abstract fun execute(arg: IntArray): Int
}