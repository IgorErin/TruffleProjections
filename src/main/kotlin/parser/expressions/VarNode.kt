package parser.expressions

class VarNode(private val index: Int) : PrimeExp {
    override fun execute(args: IntArray): Int {
        return args[index]
    }
}