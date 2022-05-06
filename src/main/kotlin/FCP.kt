import com.oracle.truffle.api.Truffle
import com.oracle.truffle.api.frame.MaterializedFrame
import com.oracle.truffle.api.frame.VirtualFrame
import lexer.LexerForFCP
import lexer.Token
import parser.MyRootNode
import parser.Parser
import java.io.File
import java.io.BufferedReader

// read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x

fun main() {
    //for (i in 1..1000) {
        val startTime = System.nanoTime()
        run("read x, y; 1: x := y - 1 if x == y goto 2 else 3 2: return x 3: x := x + 1 goto 2")
        val totalTime = System.nanoTime() - startTime

        println(totalTime)
    //}
}

fun run(source: String) {
    val lexer = LexerForFCP(source)
    lexer.parseString()

    val tokens: List<Token> = lexer.listOfTokens

    val parser = Parser(tokens)
    val tree = parser.getAst()


    val inputData = mutableListOf<Int>()

    for (i in 1..parser.listOfVariables.size) {
        inputData.add(readLine()?.toInt() ?: TODO("incorrect input"))
    }

    println(tree.execute())
}

fun readFile() {
    val bufferedReader: BufferedReader = File("example.txt").bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    println(inputString)
}
