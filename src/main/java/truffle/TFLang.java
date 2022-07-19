package truffle;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.RootNode;
import org.antlr.runtime.CharStream;
import simple.SimpleFcpParser;
import simple.nodes.Node;
import truffle.frame.TFFrame;
import truffle.nodes.TFNode;
import truffle.nodes.TFRootNode;
import truffle.parser.LexicalScope;

import java.io.Reader;
import java.util.List;

@TruffleLanguage.Registration(id = "TF", name = "TF")
public class TFLang extends TruffleLanguage<Void> {
    private final  FrameDescriptor.Builder newBuilder = FrameDescriptor.newBuilder();
    private final LexicalScope newScope = new LexicalScope(null);

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        Reader fileName = request.getSource().getReader();
        System.out.println(fileName);

        SimpleFcpParser newParser = new SimpleFcpParser();
        List<Node> nodeList = newParser.getAstFromReader(fileName);

        TFFrame.setBuiltins(newBuilder, newScope);

        TFNode[] tfNodes = getTFNodes(nodeList);

        RootNode rootNode = new TFRootNode(tfNodes, newBuilder.build());
        //DirectCallNode directCall = Truffle.getRuntime().createDirectCallNode(rootNode.getCallTarget());

        //Frame frame = TFFrame.getTopFrame(newBuilder.build(), newScope);

        return rootNode.getCallTarget(); //directCall.call(new FrameStack(frame.materialize(), null), new ArgArray(new TFNode[]{}));TODO()
    }

    private TFNode[] getTFNodes(List<Node> nodes) {
        TFNode[] tfNodes = new TFNode[nodes.size()];

        for (int index = 0; index < nodes.size(); index++) {
            tfNodes[index] = nodes.get(index).convert(newBuilder, newScope);
        }

        return tfNodes;
    }

    @Override
    protected Void createContext(Env env) {
        return null;
    }
}
