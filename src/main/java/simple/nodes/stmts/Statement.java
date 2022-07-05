package simple.nodes.stmts;

import simple.Environment;
import simple.nodes.Node;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;

import java.util.List;

public abstract class Statement implements Node {
    private List<Node> nodeList;

    private Statement(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public class IfStatement extends Statement {
        public IfStatement(List<Node> nodeList) {
            super(nodeList);
        }

        @Override
        public Object eval(Environment env) {
            Object result = nodeList.get(1).eval(env);
            Node ifNode = nodeList.get(2);
            Node elseNode = nodeList.get(3);

            if (!(boolean) result) {
                return elseNode.eval(env);
            }

            return ifNode.eval(env);
        }
    }

    public class DefineStatement extends Statement {
        private DefineStatement(List<Node> nodeList) {
            super(nodeList);
        }

        @Override
        public Object eval(Environment env) {
            String defName = ((VarNode) nodeList.get(1)).name();
            ListNode defBody = new ListNode(nodeList.subList(2, nodeList.size()));

            env.putValue(defName, defBody.eval(env));

            return null;
        }
    }

    public class Lambda extends Statement {
        private Lambda(List<Node> nodeList) {
            super(nodeList);
        }

        @Override
        public Object eval(Environment env) {
            return null;
        }
    }
}
