package parser.expressions

interface Visitor<K> {
    fun visitBinExp(exp: BinExp): K
    fun visitUnExp(exp: UnExp): K
    fun visitPrimeExp(exp: PrimeExp): K
}
