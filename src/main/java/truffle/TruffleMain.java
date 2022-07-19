package truffle;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.RootNode;
import org.graalvm.polyglot.Context;
import simple.SimpleFcpParser;
import simple.nodes.Node;
import truffle.frame.FrameStack;
import truffle.frame.TFFrame;
import truffle.nodes.TFNode;
import truffle.nodes.TFRootNode;
import truffle.parser.ArgArray;
import truffle.parser.LexicalScope;

import java.util.List;

public class TruffleMain {
    static public void main(String[] args) {
        try {
            Context context = Context.create("TF");

            context.eval("TF", "(+ (- 3))");
        } catch (Exception e) {
            System.out.println("Execute exception: " + e.getMessage());
        }

        execute("src/main/localVarProgram.fcp");
    }

    static public Object execute(String fileName) {
        SimpleFcpParser newParser = new SimpleFcpParser();
        List<Node> nodeList = newParser.getAstFromFile(fileName);

        TFNode[] nodes = new TFNode[nodeList.size()];
        FrameDescriptor.Builder newBuilder = FrameDescriptor.newBuilder();
        LexicalScope newScope = new LexicalScope(null);

        TFFrame.setBuiltins(newBuilder, newScope);

        for (int index = 0; index < nodeList.size(); index++) {
            nodes[index] = nodeList.get(index).convert(newBuilder, newScope);
        }

        RootNode rootNode = new TFRootNode(nodes, newBuilder.build());
        DirectCallNode directCall = Truffle.getRuntime().createDirectCallNode(rootNode.getCallTarget());

        Frame frame = TFFrame.getTopFrame(newBuilder.build(), newScope);

        try {
            return directCall.call(new FrameStack(frame.materialize(), null), new ArgArray(new TFNode[]{}));
        } catch (Exception e) {
            System.out.println("Exception in eval: " + e.getMessage());
            return null;
        }
    }
}
