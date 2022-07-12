package truffle.nodes.stmt;

import truffle.nodes.FCPNode;
import truffle.nodes.exps.InvokeNode;

import java.util.List;

public class Statement {
    public static InvokeNode check(List<FCPNode> nodeList) {
        if (nodeList.isEmpty()) {
            return null;
        } else if (nodeList.size() > 1) {
            List<FCPNode> subList = nodeList.subList(1, nodeList.size());

            return new InvokeNode(nodeList.get(0), subList.toArray(new FCPNode[0]));
        }

        return null;
    }
}
