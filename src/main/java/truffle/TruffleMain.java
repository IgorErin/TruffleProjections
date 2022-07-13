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
import truffle.parser.LexicalScope;

import java.util.List;

public class TruffleMain {
    static public void main(String[] args) {
        SimpleFcpParser newParser = new SimpleFcpParser();

        List<Node> nodeList = newParser.getAst("src/test/java/defTest.fcp");
        TFNode[] nodes = new TFNode[nodeList.size()];
        FrameDescriptor.Builder newBuilder = FrameDescriptor.newBuilder();
        LexicalScope newScope = new LexicalScope(null);

        for (int index = 0; index < nodeList.size(); index++) {
            nodes[index] = nodeList.get(index).convert(newBuilder, newScope);
        }

        RootNode rootNode = new TFRootNode(nodes, newBuilder.build());
        DirectCallNode directCall = Truffle.getRuntime().createDirectCallNode(rootNode.getCallTarget());
        Frame frame = topFrame(newBuilder, newScope);

        directCall.call(frame);
    }

    static private Frame topFrame(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
        Frame frame = Truffle.getRuntime().createVirtualFrame(new Object[] {}, descriptorBuilder.build());

        putSlot(descriptorBuilder, frame, scope, Builtin.getPlusFun(descriptorBuilder.build()), "+");
        putSlot(descriptorBuilder, frame, scope, Builtin.getMinusFun(descriptorBuilder.build()), "-");
        putSlot(descriptorBuilder, frame, scope, Builtin.getMulFun(descriptorBuilder.build()), "*");
        putSlot(descriptorBuilder, frame, scope, Builtin.getPrintFun(descriptorBuilder.build()), "println");
        putSlot(descriptorBuilder, frame, scope, Builtin.getTimeFun(descriptorBuilder.build()), "time");

        return frame;
    }

    static private void putSlot(
            FrameDescriptor.Builder descriptorBuilder,
            Frame frame,
            LexicalScope scope,
            Object function,
            String name
    ) {
        int slot = descriptorBuilder.addSlot(FrameSlotKind.Object, name, name);
        scope.locals.put(name, slot);
        frame.setObject(slot, function);
    }
}
