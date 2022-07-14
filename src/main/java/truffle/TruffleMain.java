package truffle;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.RootNode;
import simple.SimpleFcpParser;
import simple.nodes.Node;
import truffle.nodes.TFNode;
import truffle.nodes.TFRootNode;
import truffle.nodes.builtin.Builtin;
import truffle.parser.ArgArray;
import truffle.parser.LexicalScope;

import java.util.List;

public class TruffleMain {
    static public void main(String[] args) {
        SimpleFcpParser newParser = new SimpleFcpParser();

        List<Node> nodeList = newParser.getAst("src/main/program.fcp");
        TFNode[] nodes = new TFNode[nodeList.size()];
        FrameDescriptor.Builder newBuilder = FrameDescriptor.newBuilder();
        LexicalScope newScope = new LexicalScope(null);

        setBuiltins(newBuilder, newScope);

        for (int index = 0; index < nodeList.size(); index++) {
            nodes[index] = nodeList.get(index).convert(newBuilder, newScope);
        }

        RootNode rootNode = new TFRootNode(nodes, newBuilder.build());
        DirectCallNode directCall = Truffle.getRuntime().createDirectCallNode(rootNode.getCallTarget());
        Frame frame = getTopFrame(newBuilder.build(), newScope);

        System.out.println(directCall.call(frame.materialize(), new ArgArray(new TFNode[] {})));
    }

    static private void setBuiltins(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
        putSlot(descriptorBuilder, scope, "-");
        putSlot(descriptorBuilder, scope, "+");
        putSlot(descriptorBuilder, scope, "*");
        putSlot(descriptorBuilder, scope, "println");
        putSlot(descriptorBuilder, scope, "now");
    }

    static private Frame getTopFrame(FrameDescriptor descriptor, LexicalScope scope) {
        Frame frame = Truffle.getRuntime().createVirtualFrame(new Object[] {}, descriptor);

        frame.setObject(scope.find("-"), Builtin.getMinusFun(descriptor));
        frame.setObject(scope.find("+"), Builtin.getPlusFun(descriptor));
        frame.setObject(scope.find("*"), Builtin.getMulFun(descriptor));
        frame.setObject(scope.find("println"), Builtin.getPrintFun(descriptor));
        frame.setObject(scope.find("now"), Builtin.getTimeFun(descriptor));

        return frame;
    }

    static private void putSlot(
            FrameDescriptor.Builder descriptorBuilder,
            LexicalScope scope,
            String name
    ) {
        int slot = descriptorBuilder.addSlot(FrameSlotKind.Object, name, name);
        scope.locals.put(name, slot);
    }
}
