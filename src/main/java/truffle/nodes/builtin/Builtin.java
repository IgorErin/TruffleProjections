package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.FrameDescriptor;
import truffle.nodes.TFNode;
import truffle.nodes.TFRootNode;
import truffle.types.TFFunction;

public class Builtin {
    static TFFunction getPlusFun(TFNode left, TFNode right, FrameDescriptor descriptor) {
        AddNode newAddNode = AddNodeGen.create(left, right);
        TFRootNode rootNode = new TFRootNode(new TFNode[] {newAddNode}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    static TFFunction getMinusFun(TFNode left, TFNode right, FrameDescriptor descriptor) {
        MinusNode newMinNode = MinusNodeGen.create(left, right);
        TFRootNode rootNode = new TFRootNode(new TFNode[] {newMinNode}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    static TFFunction getMulFun(TFNode left, TFNode right, FrameDescriptor descriptor) {
        MulNode newMulNode = MulNodeGen.create(left, right);
        TFRootNode rootNode = new TFRootNode(new TFNode[] {newMulNode}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    static TFFunction getTimeFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new TimeNode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    static TFFunction getPrintFun(TFNode node, FrameDescriptor descriptor) {
        PrintNode printNode = PrintNodeGen.create(node);
        TFRootNode rootNode = new TFRootNode(new TFNode[] {printNode}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }
}
