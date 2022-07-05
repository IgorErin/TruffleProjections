package simple.nodes.stmts;

import simple.Environment;
import simple.nodes.Node;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;
import simple.types.Function;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Statement implements Node {
    private final List<Node> nodeList;

    private Statement(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    private class IfStatement extends Statement {
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

    private class DefineStatement extends Statement {
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

    private class LambdaStatement extends Statement {
        private LambdaStatement(List<Node> nodeList) {
            super(nodeList);
        }

        @Override
        public Object eval(Environment env) {
            ListNode formalParameters = (ListNode) nodeList.get(1);
            List<String> names = new LinkedList<String>();
            ListNode body = (ListNode) nodeList.get(2);

            return new Function() {
                @Override
                public Object execute(List<Object> args) {
                    for (Iterator<Node> it = formalParameters.iterator(); it.hasNext(); ) {
                        VarNode i = (VarNode) it.next();
                        names.add(i.name());
                    }

                    if (names.size() != args.size()) {
                        throw new RuntimeException(
                                "Wrong number of arguments, expected: " + names.size() + " but " + args.size() + "got"
                        );
                    }

                    Environment newEnv = env;
                    Object result = null;

                    for (String name : names) {
                        newEnv.putValue(name, args);
                    }

                    for (Iterator<Node> it = body.iterator(); it.hasNext(); ) {
                        Node node = (Node) it.next();
                        result = node.eval(newEnv);
                    }

                    return result;
                }
            };
        }
    }

    private final Node DEFINE_NODE = new VarNode("define");
    private final Node IF_NODE = new VarNode("define");
    private final Node LAMBDA = new VarNode("define");

    public Node check(List<Node> nodeList) {
        if (nodeList.isEmpty()) {
            return new ListNode(nodeList);
        } else {
            Node node = nodeList.get(0);

            if (nodeList.size() == 3 && node.equals(DEFINE_NODE)) {
                return new DefineStatement(nodeList);
            } else if (nodeList.size() == 4 && node.equals(IF_NODE)) {
                return new IfStatement(nodeList);
            } else if (nodeList.size() == 3 && node.equals(LAMBDA)) {
                return new LambdaStatement(nodeList);
            } else {
                return new ListNode(nodeList);
            }
        }
    }
}