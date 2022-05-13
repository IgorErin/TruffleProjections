package truffle.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import truffle.types.Label;

import java.util.Arrays;
/*

public class InvokeNode extends FCPNode {
    @Children FCPNode[] arguments;
    @Child FCPNode labelNode;
    @Child IndirectCallNode callNode;

    public InvokeNode(FCPNode[] arguments, FCPNode funNode) {
        this.arguments = arguments;
        this.labelNode = funNode;
        this.callNode = Truffle.getRuntime().createIndirectCallNode();
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frame) {
        Label function = evalFunction(frame);

        CompilerAsserts.compilationConstant(this.arguments.length);

        Object[] valArguments = new Object[this.arguments.length + 1]; // arguments ?
        valArguments[0] = function.getLexicalScope();

        for (int i = 0; i < this.arguments.length + 1; i++) {
            valArguments[i + 1] = this.arguments[i].execute(frame);
        }

        return this.callNode.call(function.callTarget,  frame, valArguments);
    }

    private Label evalFunction(VirtualFrame frame) {
        try {
            return this.labelNode.executeLabel(frame);
        } catch (UnexpectedResultException e) {
            throw new UnsupportedSpecializationException(this, new Node[] {this.labelNode}, e);
        }
    }

    @Override
    public String toString() {
        return "Invoke " + this.labelNode + "with " + Arrays.toString(this.arguments);
    }
}
*/