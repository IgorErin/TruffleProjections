import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.RootNode;
import exceptions.ParserException;
import lexer.FCPLexer;
import lexer.Token;
import truffle.nodes.FCPNode;
import truffle.nodes.FCPRootNode;
import truffle.parser.Parser;
import truffle.types.FCPFunction;

import java.util.Dictionary;
import java.util.List;

public class FCPMain {
    public static void main(String[] args) throws ParserException {
        System.out.println(pars("read x, y;" +
                "def 1: if x == y goto 7 else 2" +
        "def 2: if x < y goto 5 else 3" +
        "def 3: x := x - y goto 1" +
        "def 5: y := y - x goto 1" +
        "def 7: return x" +
        "1"));
    }

    private static Object pars(String stringOfCode) throws ParserException {
        FCPLexer lexer = new FCPLexer(stringOfCode);
        lexer.parseString();
        List<Token> tokens = lexer.getListOfTokens();

        System.out.println(tokens);

        Parser parser = new Parser(tokens);

        FrameDescriptor frameDescriptor = new FrameDescriptor();
        FCPNode[] nodes = parser.readProgram(frameDescriptor);
        VirtualFrame frame = Truffle.getRuntime().createVirtualFrame(new Object[] {}, frameDescriptor);

        FCPRootNode rootNode = new FCPRootNode(nodes, frameDescriptor);
        FCPFunction function = new FCPFunction(rootNode.getCallTarget());
        DirectCallNode directCallNode = Truffle.getRuntime().createDirectCallNode(function.callTarget);

        return directCallNode.call(frame);
    }
}
