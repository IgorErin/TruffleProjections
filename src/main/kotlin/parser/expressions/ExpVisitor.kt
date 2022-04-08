package parser.expressions

interface ExpVisitor<T> {
    fun visitBinExp(exp: BinExp): T
    fun visitUnExp(exp: UnExp): T
    fun visitPrimeExp(exp: PrimeExp): T
}
