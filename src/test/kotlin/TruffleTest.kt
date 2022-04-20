import nodes.AddNode
import nodes.FunNode
import nodes.IntNode

fun main() {
    val func = FunNode(AddNode(IntNode(0), AddNode(IntNode(1), IntNode(2))))
    val target = func.callTarget

    var startTime = System.nanoTime()
    target.call(intArrayOf(1, 2, 3))
    var totalTime = System.nanoTime() - startTime

    println(totalTime)

    for (i in 1..100) {
        startTime = System.nanoTime()
        target.call(intArrayOf(1, 2, 3))
        totalTime = System.nanoTime() - startTime

        println(totalTime)
    }
}