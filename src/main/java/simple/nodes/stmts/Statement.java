package simple.nodes.stmts;

import simple.Environment;
import simple.nodes.Node;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;
import simple.types.Function;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Statement implements Node {
    protected final List<Node> nodeList;

    private Statement(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    @Override
    public Object eval(Environment env) {
        return null;
    }

    private static class IfStatement extends Statement {
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

    private static class DefineStatement extends Statement {
        private DefineStatement(List<Node> nodeList) {
            super(nodeList);
        }

        @Override
        public Object eval(Environment env) {
            String defName = ((VarNode) nodeList.get(1)).name();
            Node defBody = nodeList.get(2);

            env.putValue(defName, defBody.eval(env));

            return null;
        }
    }

    private static class LambdaStatement extends Statement {
        private LambdaStatement(List<Node> nodeList) {
            super(nodeList);
        }

        @Override
        public Object eval(Environment env) {
            ListNode formalParameters = (ListNode) nodeList.get(1);
            List<String> names = new LinkedList<String>();
            Node body = nodeList.get(2);

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

                    Environment newEnv = new Environment(env);
                    Object result = null;

                    for (String name : names) {
                        newEnv.putValue(name, args);
                    }

                    /*for (Iterator<Node> it = body.iterator(); it.hasNext(); ) {
                        Node node = it.next();
                        result = node.eval(newEnv);
                    }

                    return result;*/

                    return body.eval(newEnv);
                }
            };
        }
    }

    public static Node check(List<Node> nodeList) {
        System.out.println("lol");
        if (nodeList.isEmpty()) {
            return new ListNode(nodeList);
        } else {
            String name = ((VarNode) nodeList.get(0)).name();

            if (nodeList.size() == 3 && Objects.equals(name, "define")) {
                return new DefineStatement(nodeList);
            } else if (nodeList.size() == 4 && Objects.equals(name, "if")) {
                return new IfStatement(nodeList);
            } else if (nodeList.size() == 3 && Objects.equals(name, "lambda")) {
                return new LambdaStatement(nodeList);
            } else {
                return new ListNode(nodeList);
            }
        }
    }
}
