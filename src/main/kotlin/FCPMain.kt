import interpreter.Environment
import lexer.FCPLexer
import lexer.Token
import parsers.SimpleParser

//import parser.Parser

// read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x

fun main() {
    val startTime = System.nanoTime()
    run("""read x, y;
            def first: if x == y goto 7 else 2
            def 2: if x < y goto 5 else 3
            def 3: x := x - y goto first
            def 5: y := y - x goto first
            def 7: return x
            first""".trimIndent())
    val totalTime = System.nanoTime() - startTime

    println(totalTime)
}

fun run(source: String) {
    val lexer = FCPLexer(source)
    lexer.parseString()

    val parser = SimpleParser(lexer.listOfTokens)

    val label = parser.readProgram()

    val env = Environment()

    print(label.execute(env))
}
