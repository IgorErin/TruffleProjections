import com.oracle.truffle.api.Truffle
import com.oracle.truffle.api.frame.FrameDescriptor
import lexer.LexerForFCP
import lexer.Token
//import parser.Parser

// read x, y; 1: if x == y goto 7 else 2 2: if x < y goto 5 else 3 3: x := x - y goto 1 5: y := y - x goto 1 7: return x

fun main() {
    val startTime = System.nanoTime()
    run("read x, y; 1: x := y - 1 if x == z goto 2 else 3 2: return x 3: x := x + 1 goto 2")
    val totalTime = System.nanoTime() - startTime

    //println(totalTime)
}

fun run(source: String) {
    val lexer = LexerForFCP(source)
    lexer.parseString()

    val tokens: List<Token> = lexer.listOfTokens

    //val parser = Parser(tokens)
    //val tree = parser.getAst()

    //------------------------------------------

    val frameDescriptor = FrameDescriptor()

    frameDescriptor.addFrameSlot("lol")
    val frame = Truffle.getRuntime().createVirtualFrame(arrayOf(), frameDescriptor)

    val materializedFrame = frame.materialize()

    //print("result: ${tree.execute(parser.frame)}")

}
