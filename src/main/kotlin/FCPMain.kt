import lexer.LexerForScheme
import lexer.Token
//import parser.Parser

// read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x

fun change(name: String, map: MutableMap<String, Int>) {
    map[name] = 1;
}

fun main() {
    /*val startTime = System.nanoTime()
    run("read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x")
    val totalTime = System.nanoTime() - startTime

    //println(totalTime)*/

    val map = mutableMapOf<String, Int>()

    map["lol"] = 2

    println(map["lol"])

    change("lol", map)

    println(map["lol"])
}

fun run(source: String) {
    val lexer = LexerForScheme(source)
    lexer.parseString()


    val tokens: List<Token> = lexer.listOfTokens

    print(tokens)
}
