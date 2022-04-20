import lexer.LexerForFCP
import lexer.Token
import parser.Evaluater
import parser.Parser
import java.io.File
import java.io.BufferedReader

// read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x

fun main() {
    for (i in 1..1000) {
        val startTime = System.nanoTime()
        run("0: x := 542 y := 143 goto 1 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x")
        val totalTime = System.nanoTime() - startTime

        println(totalTime)
    }
}

fun run(source: String) {
    val lexer = LexerForFCP(source)
    lexer.parseString()

    val tokens: List<Token> = lexer.listOfTokens

    val parser = Parser(tokens)
    parser.read()

    val eval = Evaluater(parser)
    eval.eval()
}

fun readFile() {
    val bufferedReader: BufferedReader = File("example.txt").bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    println(inputString)
}
