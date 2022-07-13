package truffle.nodes.builtin;

public abstract class MulNode extends BinNode {
    protected int mylInt(int left, int right) {
        return left * right;
    }

    protected boolean mylBoolean(boolean left, boolean right) {
        return left && right;
    }
}
