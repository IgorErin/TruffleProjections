package simple.nodes.exps;

import com.oracle.truffle.api.frame.FrameDescriptor;
import simple.Environment;
import simple.nodes.Node;
import simple.types.Function;
import truffle.nodes.TFNode;
import truffle.parser.LexicalScope;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListNode implements Node {
    private final List<Node> nodeList;

    public ListNode(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public ListNode(Node ...nodes) {
        this.nodeList = new LinkedList<>();

        Collections.addAll(this.nodeList, nodes);
    }

    public int size() {
        return nodeList.size();
    }

    public Iterator<Node> iterator() {
        return nodeList.iterator();
    }

    @Override
    public Object eval(Environment env) {
        if (nodeList.size() == 0 ) {
            return null;
        }

        Function lambda = (Function) nodeList.get(0).eval(env);
        List<Object> args = new LinkedList<Object>();

        for (Node i : nodeList.subList(1, nodeList.size())) {
            args.add(i.eval(env));
        }

        return lambda.execute(args);
    }

    @Override
    public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
        return null;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof ListNode anotherList) {
            int counter = 0;

            if (anotherList.size() != nodeList.size()) {
                return false;
            }

            for (Iterator<Node> it = anotherList.iterator(); it.hasNext();) {
                if (!it.next().equals(nodeList.get(counter))) {
                    return false;
                }

                counter++;
            }

            return true;
        }

        return false;
    }
}
