import lexer.LexerForFCP
import lexer.Token
import parser.Evaluater
import parser.Parser
import java.io.File
import java.io.BufferedReader
import java.security.CodeSource

// read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x

fun main() {
    run("read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x")
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
