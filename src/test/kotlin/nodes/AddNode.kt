package nodes

class AddNode(@Child var right: MyNode, @Child var left: MyNode) : MyNode() {
    override fun execute(arg: IntArray): Int {
        return right.execute(arg) + left.execute(arg)
    }
}