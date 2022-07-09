package simple.nodes.stmts;

import simple.nodes.Node;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;

import java.util.LinkedList;

public class SimpleFcpBuiltin {
    static public ListNode getBinOpNode(String symbol, Node firsNode, Node secondNode) {
        LinkedList<Node> nodeList = new LinkedList<>();

        nodeList.add(new VarNode(symbol));
        nodeList.add(firsNode);
        nodeList.add(secondNode);

        return new ListNode(nodeList);
    }
}
