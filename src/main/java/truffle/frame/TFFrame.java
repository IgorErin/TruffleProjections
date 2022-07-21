package truffle.frame;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.*;
import truffle.TFLang;
import truffle.nodes.TFNode;
import truffle.nodes.builtin.Builtin;
import truffle.parser.LexicalScope;

public class TFFrame {
    static public void setBuiltins(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
        putSlot(descriptorBuilder, scope, "-");
        putSlot(descriptorBuilder, scope, "+");
        putSlot(descriptorBuilder, scope, "*");
        putSlot(descriptorBuilder, scope, "println");
        putSlot(descriptorBuilder, scope, "now");
        putSlot(descriptorBuilder, scope, ">");
        putSlot(descriptorBuilder, scope, ">=");
        putSlot(descriptorBuilder, scope, "<");
        putSlot(descriptorBuilder, scope, "<=");
        putSlot(descriptorBuilder, scope, "=");
    }

    static public VirtualFrame getTopFrame(FrameDescriptor descriptor, LexicalScope scope) {
        VirtualFrame frame = Truffle.getRuntime().createVirtualFrame(new Object[] {}, descriptor);

        frame.setObject(scope.find("-"), Builtin.getMinusFun(descriptor));
        frame.setObject(scope.find("+"), Builtin.getPlusFun(descriptor));
        frame.setObject(scope.find("*"), Builtin.getMulFun(descriptor));
        frame.setObject(scope.find("println"), Builtin.getPrintFun(descriptor));
        frame.setObject(scope.find("now"), Builtin.getTimeFun(descriptor));
        frame.setObject(scope.find(">"), Builtin.getGFun(descriptor));
        frame.setObject(scope.find(">="), Builtin.getGOEFun(descriptor));
        frame.setObject(scope.find("<"), Builtin.getLFun(descriptor));
        frame.setObject(scope.find("<="), Builtin.getLOEFun(descriptor));
        frame.setObject(scope.find("="), Builtin.getEFun(descriptor));

        return frame;
    }

    private static void putSlot(
            FrameDescriptor.Builder descriptorBuilder,
            LexicalScope scope,
            String name
    ) {
        int slot = descriptorBuilder.addSlot(FrameSlotKind.Object, name, name);
        scope.locals.put(name, slot);
    }
}
