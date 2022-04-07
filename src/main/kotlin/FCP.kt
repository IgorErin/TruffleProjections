import java.nio.file.Files
import java.nio.file.Paths
import java.nio.charset.Charset
import java.io.BufferedReader
import lexer.LexerForFCP
import lexer.Token
import parser.Parser
import parser.Printer
import java.io.InputStreamReader
import kotlin.system.exitProcess

var hadError = false

fun main(args: Array<String>) {
    run("fefw")
    /*if (args.length > 1) {
        System.out.println("Too many arguments");
        System.exit(1);
    }
    else if (args.length == 1) {
        runFile(args[0]);
    }
    else {
        runREPL();
    }*/
}

private fun runFile(name: String) {
    val bytes = Files.readAllBytes(Paths.get(name))
    if (hadError) {
        exitProcess(1)
    }
    run(String(bytes, Charset.defaultCharset()))
}

fun runREPL() {
    val input = InputStreamReader(System.`in`)
    val reader = BufferedReader(input)
    while (true) {
        println(">")
        val line = reader.readLine() ?: break
        hadError = false
        run(line)
    }
}

fun run(stringOfCode: String?) {
    val lexer = LexerForFCP(" 1 + 2 * 3 == 1 + 4 - 2")
    lexer.parseString()
    val tokens: List<Token> = lexer.listOfTokens
    print("tokens: ")
    for (i in tokens) {
        print(i.name)
        print(" ")
    }
    val parser = Parser(tokens)
    val exp = parser.parse()
    val printer = Printer()
    println(printer.print(exp))
}

fun error(string: Int, message: String) {
    report(string, "", message)
}

fun report(string: Int, where: String, massage: String) {
    println("Error occurred in " + string + "line, in" + where + ": " + massage)
    val hadError = true
}
