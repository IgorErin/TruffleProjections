package simple.nodes.stmts;

import simple.nodes.Node;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;

import java.util.LinkedList;

public class SimpleFcpStatement {
    static public Node getDefNode(Node fistNode, Node secondNode) {
        LinkedList<Node> list = new LinkedList<Node>();
        list.add(new VarNode("define"));
        list.add(fistNode);
        list.add(secondNode);

        return Statement.check(list);
    }

    static public Node getIfNode(Node condNode, Node ifNode, Node elseNode) {
        LinkedList<Node> list = new LinkedList<Node>();
        list.add(new VarNode("if"));
        list.add(condNode);
        list.add(ifNode);
        list.add(elseNode);

        return Statement.check(list);
    }

    static public Node getLambdaNode(ListNode argsListNode, ListNode bodyListNode) {
        LinkedList<Node> list = new LinkedList<Node>();
        list.add(new VarNode("lambda"));
        list.add(argsListNode);
        list.add(bodyListNode);

        return Statement.check(list);
    }
}
