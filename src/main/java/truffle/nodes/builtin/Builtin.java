package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.FrameDescriptor;
import truffle.nodes.TFNode;
import truffle.nodes.TFRootNode;
import truffle.nodes.builtin.arithmetic.AddNode;
import truffle.nodes.builtin.arithmetic.MinusNode;
import truffle.nodes.builtin.arithmetic.MulNode;
import truffle.types.TFFunction;

public class Builtin {
    public static TFFunction getPlusFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new AddNode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    public static TFFunction getMinusFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new MinusNode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    public static TFFunction getMulFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new MulNode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    public static TFFunction getTimeFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new TimeNode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    public static TFFunction getPrintFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new PrintNode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }
}
