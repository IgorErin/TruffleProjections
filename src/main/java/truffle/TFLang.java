package truffle;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.RootNode;
import simple.SimpleFcpParser;
import simple.nodes.Node;
import truffle.frame.TFFrame;
import truffle.nodes.TFNode;
import truffle.nodes.TFRootNode;
import truffle.nodes.builtin.Builtin;
import truffle.parser.LexicalScope;

import java.io.Reader;
import java.util.List;

@TruffleLanguage.Registration(id = "tf", name = "TF")
public class TFLang extends TruffleLanguage<Void> {
    private final  FrameDescriptor.Builder newBuilder = FrameDescriptor.newBuilder();
    private final LexicalScope newScope = new LexicalScope(null);

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        Reader fileName = request.getSource().getReader();

        SimpleFcpParser newParser = new SimpleFcpParser();
        List<Node> nodeList = newParser.getAstFromReader(fileName);

        TFFrame.setBuiltins(newBuilder, newScope);
        //System.out.println(newBuilder.build());

        TFNode[] tfNodes = getTFNodes(nodeList);
        tfNodes = addBuiltins(tfNodes);

        RootNode rootNode = new TFRootNode(tfNodes, newBuilder.build());

        return rootNode.getCallTarget();
    }

    private TFNode[] getTFNodes(List<Node> nodes) {
        TFNode[] tFNodes = new TFNode[nodes.size()];

        for (int index = 0; index < nodes.size(); index++) {
            tFNodes[index] = nodes.get(index).convert(newBuilder, newScope);
        }

        return tFNodes;
    }

    private TFNode[] addBuiltins(TFNode[] nodes) {
        TFNode[] tFNodes = new TFNode[nodes.length + Builtin.count];
        TFNode[] builtins = Builtin.getBuiltinNodes(newBuilder.build(), newScope);

        if (Builtin.count >= 0)
            System.arraycopy(builtins, 0, tFNodes, 0, Builtin.count);

        if (tFNodes.length - Builtin.count >= 0)
            System.arraycopy(nodes, 0, tFNodes, Builtin.count, nodes.length);

        return tFNodes;
    }

    @Override
    protected Void createContext(Env env) {
        return null;
    }
}
