package simple.nodes.exps;

import simple.Environment;
import simple.nodes.Node;
import simple.types.Function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListNode implements Node {
    private final List<Node> nodeList;

    public ListNode(List<Node> nodeList) {
        this.nodeList = nodeList;
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

        List<Object> args = new ArrayList<Object>();
        for (Node i : nodeList.subList(1, nodeList.size())) {
            args.add(i.eval(env));
        }

        return lambda.execute(args);
    }
}
