package truffle.parser;

import com.oracle.truffle.api.frame.FrameDescriptor;
import exceptions.ParserException;
import lexer.Token;
import lexer.Values;
import truffle.nodes.*;
import truffle.nodes.expressions.*;
import truffle.types.FCPFunction;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public List<Token> listOfTokens;
    private int position;

    public Parser(List<Token> listOfTokens) {
        FrameDescriptor descriptor = new FrameDescriptor();

        this.listOfTokens = listOfTokens;
        this.position = 0;
    }

    public FCPNode[] readProgram(FrameDescriptor descriptor) throws ParserException {
        List<FCPNode> nodes = new ArrayList<>();

        if (find(Values.READ)) {
            nodes.addAll(readReadBlock(descriptor));
        }

        while (find(Values.DEFINE)) {
            nodes.add(readDefineNode(descriptor));
        }

        nodes.add(readInvokeNode(descriptor));

        return nodes.toArray(new FCPNode[0]);
    }

    private ArrayList<FCPNode> readReadBlock(FrameDescriptor descriptor) throws ParserException {
        ArrayList<FCPNode> nodes = new ArrayList<>();
        Values value;

        while (find(Values.VAR)) {
            String name = previousToken().getName();
            nodes.add(DefineNodeGen.create(InputNodeGen.create(), descriptor.addFrameSlot(name))); //TODO

            value = current().getValue();
            move();
            if (value == Values.SEMCOL) {
                break;
            } if (value == Values.COMMA) {
            } else {
                throw new ParserException("unexpected value: " + value);
            }
        }

        return nodes;
    }

    private FCPNode readDefineNode(FrameDescriptor descriptor) throws ParserException {
        Values value = current().getValue();
        move();

        switch (value) {
            case LABEL: {
                return readLabelDef(descriptor);
            }
            case VAR: {
                return readAssignment(descriptor); //TODO()
            }
            default: throw new ParserException("unexpected value: " + value);
        }

        /*
        return switch (value) {
            case LABEL -> readLabelDef(descriptor);
            case VAR -> readAssignment(descriptor);
            default -> { throw new ParserException("unexpected value: " + value); }
        };
         */
    }

    private FCPFunction readFCPFunction(FrameDescriptor descriptor) throws ParserException {
        List<FCPNode> bodyNodes = new ArrayList<>();

        while (find(Values.VAR)) {
            bodyNodes.add(readAssignment(descriptor));
        }

        bodyNodes.add(readJump(descriptor));

        return FCPFunction.create(bodyNodes.toArray(new FCPNode[] {}), descriptor);
    }

    private FCPNode readJump(FrameDescriptor descriptor) throws ParserException {
        Values value = current().getValue();
        move();
        FCPNode lastNode;

        switch (value) {
            case RETURN: {
                lastNode = readReturnNode(descriptor);
                break;
            }
            case GOTO: {
                lastNode = readGoto(descriptor);
                break;
            }
            case IF: {
                lastNode = readIfStatement(descriptor);
                break;
            }
            default: throw new ParserException("unexpected value: " + value);
        }

        return lastNode;
    }

    private FCPNode readGoto(FrameDescriptor descriptor) throws ParserException {
        return new InvokeNode(readSymbolNode(descriptor));
    }

    private FCPNode readLabelDef(FrameDescriptor descriptor) throws ParserException {
        String symbolName = previousToken().getName();

        FCPFunction function = readFCPFunction(descriptor);

        return DefineNodeGen.create(new LabelNode(function), descriptor.findOrAddFrameSlot(symbolName));
    }

    private FCPNode readAssignment(FrameDescriptor descriptor) throws ParserException {
        String symbolName = previousToken().getName();

        if (!find(Values.ASSIGN)) throw new ParserException("expected assignment, found: " + current().getName());

        ExpressionNode exp = readExp(descriptor);

        return DefineNodeGen.create(exp, descriptor.findOrAddFrameSlot(symbolName)); //TODO()
    }

    private FCPNode readReturnNode(FrameDescriptor descriptor) throws ParserException {
        return new ReturnNode(readSymbolNode(descriptor));
    }

    private FCPNode readIfStatement(FrameDescriptor descriptor) throws ParserException {
        ExpressionNode exp = readExp(descriptor);

        if (!find(Values.GOTO)) throw new ParserException("goto expected, found: " + current().getName());

        FCPNode thenNode = readInvokeNode(descriptor);

        if (!find(Values.ELSE)) throw new ParserException("else expected, found: " + current().getName());

        FCPNode elseNode = readInvokeNode(descriptor);

        return new IfNode(exp, thenNode, elseNode);
    }

    private InvokeNode readInvokeNode(FrameDescriptor descriptor) throws ParserException {
        if (!find(Values.VAR, Values.INT)) throw new ParserException("read invoke Node: " + current().getValue());

        return new InvokeNode(SymbolNodeGen.create(descriptor.findOrAddFrameSlot(previousToken().getName())));
    }

    private SymbolNode readSymbolNode(FrameDescriptor descriptor) throws ParserException {
        if (!find(Values.VAR, Values.INT)) throw new ParserException("read invoke Node");

        return SymbolNodeGen.create(descriptor.findOrAddFrameSlot(previousToken().getName()));
    }


    private ExpressionNode readExp(FrameDescriptor descriptor) throws ParserException {
        return equality(descriptor);
    }

    private ExpressionNode equality(FrameDescriptor descriptor) throws ParserException {
        ExpressionNode leftExp = readCompare(descriptor);

        while (find(Values.EQ, Values.NOTQE)) {
            String operation = previousToken().getName();
            ExpressionNode rightExp = readCompare(descriptor);

            if (leftExp == null) {
                throw new ParserException("left is null"); //TODO()
            }

            switch (operation) {
                case "==": {
                    leftExp = EqualExpressionNodeGen.create(leftExp, rightExp);
                    break;
                }
                case "!=": {
                    leftExp = NotEqualExpressionNodeGen.create(leftExp, rightExp);
                    break;
                }
                default: {
                    throw new ParserException("parse expression: " + operation);
                }
            }

            /*
            leftExp = switch (operation) {
                case "==" -> EqualExpressionNodeGen.create(leftExp, rightExp);
                case "!=" -> NotEqualExpressionNodeGen.create(leftExp, rightExp);
                default -> {
                    throw new ParserException("parse expression: " + operation);
                }
            };
             */
        }

        return leftExp;
    }


    private ExpressionNode readCompare(FrameDescriptor descriptor) throws ParserException {
        ExpressionNode leftExp = readTerm(descriptor);

        while(find(Values.GT, Values.LT, Values.LOEQ, Values.GOEQ)) {
            String operation = previousToken().getName();
            ExpressionNode rightExp = readTerm(descriptor);

            switch (operation) {
                case ">": {
                    leftExp = GTExpressionNodeGen.create(leftExp, rightExp);
                    break;
                }
                case "<":{
                    leftExp = LTExpressionNodeGen.create(leftExp, rightExp);
                    break;
                }
                case ">=": {
                    leftExp = GoEExpressionNodeGen.create(leftExp, rightExp);
                    break;
                }
                case "<=": {
                    leftExp = LoEqExpressionNodeGen.create(leftExp, rightExp);
                    break;
                }
                default: {
                    throw new ParserException("parse expression: " + operation);
                }
            }

            /*
            leftExp = switch (operation) {
                case ">" -> GTExpressionNodeGen.create(leftExp, rightExp);
                case "<" -> LTExpressionNodeGen.create(leftExp, rightExp);
                case ">=" -> GoEExpressionNodeGen.create(leftExp, rightExp);
                case "<=" -> LoEqExpressionNodeGen.create(leftExp, rightExp);
                default ->  throw new ParserException("parse expression: " + operation);
            };
            */
        }

        return leftExp;
    }

    private ExpressionNode readTerm(FrameDescriptor descriptor) throws ParserException {
        ExpressionNode leftExp = readFactor(descriptor);

        while (find(Values.MINUS, Values.PLUS)) {
            String operation = previousToken().getName();
            ExpressionNode rightExp = readFactor(descriptor);

            switch (operation) {
                case "+": {
                    leftExp = AddExpressionNodeGen.create(leftExp, rightExp);
                    break;
                }
                case "-": {
                    leftExp = SubtractionExpressionNodeGen.create(leftExp, rightExp);
                    break;
                }
                default: throw new ParserException("parse expression: " + operation);
            };

            /*
            leftExp = switch (operation) {
                case "+" -> AddExpressionNodeGen.create(leftExp, rightExp);
                case "-" -> SubtractionExpressionNodeGen.create(leftExp, rightExp);
                default -> throw new ParserException("parse expression: " + operation);
            };
            */
        }

        return leftExp;
    }

    private ExpressionNode readFactor(FrameDescriptor descriptor) throws ParserException {
        ExpressionNode leftExp = readSign(descriptor);

        while (find(Values.ASTER)) {
            String operation = previousToken().getName();
            ExpressionNode rightExp = readSign(descriptor);
            leftExp = MultiplyExpressionNodeGen.create(leftExp, rightExp);
        }

        return leftExp;
    }

    private ExpressionNode readSign(FrameDescriptor descriptor) throws ParserException {
        if (find(Values.MINUS)) {
            return MinusExpressionNodeGen.create(readValues(descriptor)); //change to FCP Node
        }

        return readValues(descriptor);
    }

    private ExpressionNode readValues(FrameDescriptor descriptor) throws ParserException {
        String name = current().getName();

        if (find(Values.INT)) {
            return new IntNode(Integer.parseInt(name));
        } else if (find(Values.VAR)) {
            return  SymbolNodeGen.create(descriptor.findOrAddFrameSlot(name));
        }

        throw new ParserException("read Var or Val: " + previousToken().getName());
    }

    private boolean find(Values... values) {
        for (Values value : values) {
            if (current().getValue() == value) {
                move();

                return true;
            }
        }

        return false;
    }

    private Token previousToken() {
        return listOfTokens.get(position - 1);
    }

    private Token current() {
        return this.listOfTokens.get(position);
    }

    private void move() {
        if (current().getValue() != Values.END)
        position++;
    }
}
