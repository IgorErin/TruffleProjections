package simple.nodes.exps;

import simple.nodes.Node;

public abstract class BinExpNode implements Node {
    Node leftExp;
    Node rightExp;
    public  BinExpNode(Node leftNode, Node rightNode) {
        this.leftExp = leftNode;
        this.rightExp = rightNode;
    }
}
