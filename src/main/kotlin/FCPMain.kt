import interpreter.Environment
import lexer.FCPLexer
import lexer.Token
import parsers.SimpleParser

// LCD
// read x, y;
//            def first: if x == y goto 7 else 2
//            def 2: if x < y goto 5 else 3
//            def 3: x := x - y goto first
//            def 5: y := y - x goto first
//            def 7: return x
//            first

//
//

fun main() {
    for (i in 1..100) {
        val startTime = System.nanoTime()
        run(
            """ def init: x := 4 y := 8 goto 1
            def 1: if x == y goto 7 else 2
            def 2: if x < y goto 5 else 3
            def 3: x := x - y goto 1
            def 5: y := y - x goto 1
            def 7: return x
            init""".trimIndent()
        )
        val totalTime = System.nanoTime() - startTime

        println("time: $totalTime")
    }
}

fun run(source: String) {
    val lexer = FCPLexer(source)
    lexer.parseString()

    val parser = SimpleParser(lexer.listOfTokens)

    val mainLabel = parser.readProgram()

    val env = Environment()
    var result: Any = Unit

    for (i in mainLabel) {
        result = i.execute(env)
    }

    println("result: $result")
}
