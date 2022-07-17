package simple.nodes.stmts;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import simple.Environment;
import simple.nodes.Node;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;
import simple.types.Function;
import truffle.nodes.TFNode;
import truffle.nodes.stmt.TFDefNodeGen;
import truffle.nodes.stmt.TFIfNode;
import truffle.nodes.stmt.TFLambdaNodeGen;
import truffle.parser.LexicalScope;
import truffle.types.TFFunction;

import java.util.*;

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
            TFNode cond = nodeList.get(1).convert(descriptorBuilder, scope);
            TFNode ifNode = nodeList.get(2).convert(descriptorBuilder, scope);
            TFNode elseNode = nodeList.get(3).convert(descriptorBuilder, scope);

            return new TFIfNode(cond, ifNode, elseNode);
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
            Object result = defBody.eval(env);

            env.putValue(defName, result);

            return result;
        }

        @Override
        public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
            String defName = ((VarNode) nodeList.get(1)).name();
            Integer frameSlot = scope.find(defName);
            if (frameSlot == null) {
                frameSlot = descriptorBuilder.addSlot(FrameSlotKind.Illegal, defName, defName);
                scope.locals.put(defName, frameSlot);
            }

            TFNode defBody = nodeList.get(2).convert(descriptorBuilder, scope);

            return TFDefNodeGen.create(defBody, frameSlot);
        }
    }

    private static class LambdaStatement extends Statement {
        final List<String> formalParameters;
        final Node[] bodyNodes;
        private LambdaStatement(List<Node> nodeList) {
            super(nodeList);
            this.formalParameters = getFormalNames((ListNode) nodeList.get(1));
            this.bodyNodes = nodeList.subList(2, nodeList.size()).toArray(new Node[0]);
        }

        @Override
        public Object eval(Environment env) {
            return new Function() {
                @Override
                public Object execute(List<Object> args) {
                    if (formalParameters.size() != args.size()) {
                        throw new RuntimeException(
                                "Wrong number of arguments, expected: " +
                                        formalParameters.size() + " but " + args.size() + " got"
                        );
                    }

                    Environment newEnv = new Environment(env);
                    for (int i = 0; i < formalParameters.size(); i++) {
                        newEnv.putValue(formalParameters.get(i), args.get(i));
                    }

                    int length = bodyNodes.length;

                    try {
                        for (int index = 0; index < length - 1; index++) {
                            bodyNodes[index].eval(newEnv);
                        }

                        return bodyNodes[length - 1].eval(newEnv);
                    } catch (Exception e) {
                        throw new RuntimeException("Lambda body: ");
                    }
                }
            };
        }

        @Override
        public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
            LexicalScope newScope = new LexicalScope(scope);
            int[] argSlots = new int[formalParameters.size()];

            for (int index = 0; index < formalParameters.size(); index++) {
                String name = formalParameters.get(index);
                int slot = descriptorBuilder.addSlot(FrameSlotKind.Illegal, name, name);

                newScope.locals.put(name, slot);
                argSlots[index] = slot;
            }

            TFNode[] tFBodyNode = new TFNode[bodyNodes.length];
            for (int index = 0; index < bodyNodes.length; index++) {
                tFBodyNode[index] = bodyNodes[index].convert(descriptorBuilder, newScope);
            }

            TFFunction fun = TFFunction.createFunction(argSlots, descriptorBuilder.build(), tFBodyNode);

            return TFLambdaNodeGen.create(fun);
        }

        private List<String> getFormalNames(ListNode nodeList) {
            List<String> params = new LinkedList<>();

            for (Iterator<Node> it = nodeList.iterator(); it.hasNext();) {
                VarNode i = (VarNode) it.next();
                params.add(i.name());
            }

            return params;
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
