package simple.nodes.exps;

import simple.Environment;
import simple.nodes.Node;

public class AddNode extends BinExpNode {
    public AddNode(Node leftNode, Node rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    public Object eval(Environment env) {
        Object leftResult = leftExp.eval(env);
        Object rightResult = rightExp.eval(env);

        if (leftResult instanceof Integer && rightResult instanceof Integer) {
            return (Integer) leftResult + (Integer) rightResult;
        }

        if (leftResult instanceof Boolean && rightResult instanceof Boolean) {
            return (Boolean) leftResult || (Boolean) rightResult;
        }

        throw new RuntimeException("cannot add various types");
    }
}
