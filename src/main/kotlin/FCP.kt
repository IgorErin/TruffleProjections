import lexer.LexerForFCP
import lexer.Token
import parser.Evaluater
import parser.Parser

fun main() {
    run("1 + 2 * 3 + 1 + 4 - 2")
}

fun run(stringOfCode: String) {
    /*val lexer = LexerForFCP(" 1 + 2 * 3 + 1 + 4 - 2")
    lexer.parseString()
    val tokens: List<Token> = lexer.listOfTokens
    print("tokens: ")
    for (i in tokens) {
        print(i.name)
        print(" ")
    }
    val parser = Parser(tokens)
    val exp = parser.parse()
    val printer = Evaluater()
    println(printer.eval(exp))*/
}

fun error(string: Int, message: String) {
    report(string, "", message)
}

fun report(string: Int, where: String, massage: String) {
    println("Error occurred in " + string + "line, in" + where + ": " + massage)
    val hadError = true
}
