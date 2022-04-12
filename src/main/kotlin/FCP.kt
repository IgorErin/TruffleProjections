import lexer.LexerForFCP
import lexer.Token
import parser.Evaluater
import parser.Parser

// read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x

fun main() {
    run("read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x")
}

fun run(stringOfCode: String) {
    val lexer = LexerForFCP(stringOfCode)
    lexer.parseString()
    val tokens: List<Token> = lexer.listOfTokens

    val parser = Parser(tokens)
    parser.read()
    println(parser.listOfVariables)
    println(parser.listOfLabels)

    for (i in lexer.listOfTokens) {
        println("${i.name} ${i.value}")
    }

    val eval = Evaluater(parser.mapOfBlocks, parser.listOfLabels, parser.listOfVariables)
    eval.eval()
}
