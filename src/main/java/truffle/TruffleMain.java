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
import truffle.parser.LexicalScope;
import truffle.types.TFFunction;

import javax.management.Descriptor;
import java.util.List;

public class TruffleMain {
    static public void main(String[] args) {
        SimpleFcpParser newParser = new SimpleFcpParser();

        List<Node> nodeList = newParser.getAst("src/test/java/defTest.fcp");
        TFNode[] nodes = new TFNode[nodeList.size()];
        FrameDescriptor.Builder newBuilder = FrameDescriptor.newBuilder();
        LexicalScope newScope = new LexicalScope(null);

        System.out.println(newBuilder);
        //topFrame(newBuilder);

        for (int index = 0; index < nodeList.size(); index++) {
            nodes[index] = nodeList.get(index).convert(newBuilder, newScope);
        }

        RootNode rootNode = new TFRootNode(nodes, newBuilder.build());
        DirectCallNode directCall = Truffle.getRuntime().createDirectCallNode(rootNode.getCallTarget());

        int[] array = new int[] {1, 2, 3};
        directCall.call(array);
    }

    /*static private Frame topFrame(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
        Frame frame = Truffle.getRuntime().createVirtualFrame(new Object[] {}, descriptorBuilder.build());

        frame.setObject(descriptorBuilder.addSlot(FrameSlotKind.Object, "+", "+"), TODO());
        frame.setObject(descriptorBuilder.addSlot(FrameSlotKind.Object, "-", "+"), TODO());
        frame.setObject(descriptorBuilder.addSlot(FrameSlotKind.Object, "*", "+"), TODO());
        frame.setObject(descriptorBuilder.addSlot(FrameSlotKind.Object, "print", "+"), TODO());
        frame.setObject(descriptorBuilder.addSlot(FrameSlotKind.Object, "println", "+"), TODO());

        return frame;
    }*/

    static private void putSlot(
            FrameDescriptor.Builder descriptorBuilder,
            Frame frame,
            LexicalScope scope,
            Object function
    ) {
        int slot = descriptorBuilder.addSlot(FrameSlotKind.Object, "+", "+");
        scope.locals.put("+", slot);
        frame.setObject(slot, function);
    }
}
