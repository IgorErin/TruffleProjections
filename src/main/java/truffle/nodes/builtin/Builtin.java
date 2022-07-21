package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.FrameDescriptor;
import truffle.nodes.TFNode;
import truffle.nodes.TFRootNode;
import truffle.nodes.builtin.arithmetic.AddNode;
import truffle.nodes.builtin.arithmetic.MinusNode;
import truffle.nodes.builtin.arithmetic.MulNode;
import truffle.nodes.builtin.log.*;
import truffle.nodes.stmt.TFDefNode;
import truffle.nodes.stmt.TFDefNodeGen;
import truffle.nodes.stmt.TFLambdaNodeGen;
import truffle.parser.LexicalScope;
import truffle.types.TFFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Builtin {
    public final static List<String> names = Arrays.asList("+", "-", "*", "=", ">", "<", ">=", "<=", "println", "now");
    public final static int count = names.size();

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

    public static TFFunction getGFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new GNode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    public static TFFunction getGOEFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new GOENode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    public static TFFunction getLFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new LNode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    public static TFFunction getLOEFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new LOENode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    public static TFFunction getEFun(FrameDescriptor descriptor) {
        TFRootNode rootNode = new TFRootNode(new TFNode[] {new ENode()}, descriptor);

        return new TFFunction(rootNode.getCallTarget());
    }

    public static TFNode[] getBuiltinNodes(FrameDescriptor descriptor, LexicalScope scope) {
        ArrayList<TFNode> nodes = new ArrayList<>();

        nodes.add(getDefNode(Builtin.getPlusFun(descriptor), scope.find("+")));
        nodes.add(getDefNode(Builtin.getMinusFun(descriptor), scope.find("-")));
        nodes.add(getDefNode(Builtin.getMulFun(descriptor), scope.find("*")));
        nodes.add(getDefNode(Builtin.getEFun(descriptor), scope.find("=")));
        nodes.add(getDefNode(Builtin.getGFun(descriptor), scope.find(">")));
        nodes.add(getDefNode(Builtin.getLFun(descriptor), scope.find("<")));
        nodes.add(getDefNode(Builtin.getGOEFun(descriptor), scope.find(">=")));
        nodes.add(getDefNode(Builtin.getLOEFun(descriptor), scope.find("<=")));
        nodes.add(getDefNode(Builtin.getPrintFun(descriptor), scope.find("println")));
        nodes.add(getDefNode(Builtin.getTimeFun(descriptor), scope.find("now")));

        return nodes.toArray(new TFNode[] {});
    }

    private static TFDefNode getDefNode(TFFunction fun, int slot) {
        return TFDefNodeGen.create(TFLambdaNodeGen.create(fun), slot);
    }
}
