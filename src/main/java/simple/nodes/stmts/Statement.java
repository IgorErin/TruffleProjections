package simple.nodes.stmts;

import com.oracle.truffle.api.frame.FrameDescriptor;
import simple.Environment;
import simple.nodes.Node;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;
import simple.types.Function;
import truffle.nodes.TFNode;
import truffle.parser.LexicalScope;

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

        @Override
        public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
            return null;
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

        @Override
        public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
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

            for (Iterator<Node> it = formalParameters.iterator(); it.hasNext();) {
                VarNode i = (VarNode) it.next();
                names.add(i.name());
            }

            return new Function() {
                @Override
                public Object execute(List<Object> args) {
                    if (names.size() != args.size()) {
                        throw new RuntimeException(
                                "Wrong number of arguments, expected: " + names.size() + " but " + args.size() + " got"
                        );
                    }

                    Environment newEnv = new Environment(env);
                    for (int i = 0; i < names.size(); i++) {
                        newEnv.putValue(names.get(i), args.get(i));
                    }

                    return body.eval(newEnv);
                }
            };
        }

        @Override
        public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
            return null;
        }
    }

    public static Node check(List<Node> nodeList) {
        if (nodeList.isEmpty()) {
            return new ListNode(nodeList);
        } else {
            String name = ((VarNode) nodeList.get(0)).name();

            if (nodeList.size() >= 3 && Objects.equals(name, "define")) {
                return new DefineStatement(nodeList);
            } else if (nodeList.size() >= 4 && Objects.equals(name, "if")) {
                return new IfStatement(nodeList);
            } else if (nodeList.size() >= 3 && Objects.equals(name, "lambda")) {
                return new LambdaStatement(nodeList);
            } else {
                return new ListNode(nodeList);
            }
        }
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof Statement ifStmt) {
            return ifStmt.nodeList.equals(this.nodeList);
        }

        return false;
    }
}
