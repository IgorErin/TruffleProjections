package parser.expressions

class ValNode(private val value: Int) : PrimeExp {
    override fun execute(args: IntArray): Int {
        return value
    }
}