package nodes

class IntNode(private val index: Int) : MyNode() {
    override fun execute(arg: IntArray): Int {
        return arg[index]
    }
}