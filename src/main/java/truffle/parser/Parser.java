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
        if (!find(Values.DEFINE)) throw new ParserException("define expected, found:" + current().getName());

        Values value = current().getValue();
        move();
        return switch (value) {
            case LABEL -> readLabelDef(descriptor);
            case VAR -> readAssignment(descriptor);
            default -> { throw new ParserException("unexpected value: " + value); }
        };
    }

    private FCPFunction readFCPFunction(FrameDescriptor descriptor) throws ParserException {
        List<FCPNode> bodyNodes = new ArrayList<>();

        while (find(Values.VAR)) {
            bodyNodes.add(readAssignment(descriptor));
        }

        Values value = current().getValue();
        move();
        FCPNode lastNode = switch (value) {
            case RETURN -> readReturnNode(descriptor);
            case GOTO -> readGoto(descriptor);
            case IF -> readIfStatement(descriptor);
            default -> throw new ParserException("unexpected value: " + value);
        };

        bodyNodes.add(lastNode);
        return FCPFunction.create(bodyNodes.toArray(new FCPNode[] {}), descriptor);
    }

    private FCPNode readGoto(FrameDescriptor descriptor) throws ParserException {
        return new InvokeNode(readSymbolNode(descriptor));
    }

    private FCPNode readLabelDef(FrameDescriptor descriptor) throws ParserException {
        String symbolName = previousToken().getName();

        FCPFunction function = readFCPFunction(descriptor);

        return DefineNodeGen.create(new LabelNode(function), descriptor.addFrameSlot(symbolName));
    }

    private FCPNode readAssignment(FrameDescriptor descriptor) throws ParserException {
        String symbolName = previousToken().getName();

        if (!find(Values.ASSIGN)) throw new ParserException("expected assignment, found: " + current().getName());

        ExpressionNode exp = readExp();

        return DefineNodeGen.create(exp, descriptor.addFrameSlot(symbolName)); //TODO()
    }

    private FCPNode readReturnNode(FrameDescriptor descriptor) throws ParserException {
        return new ReturnNode(readSymbolNode(descriptor));
    }

    private FCPNode readIfStatement(FrameDescriptor descriptor) throws ParserException {
        ExpressionNode exp = readExp();

        if (!find(Values.GOTO)) throw new ParserException("goto expected, found:" + current().getName());

        FCPNode thenNode = readInvokeNode(descriptor);

        if (find(Values.ELSE)) throw new ParserException("else expected, found:" + current().getName());

        FCPNode elseNode = readInvokeNode(descriptor);

        return new IfNode(exp, thenNode, elseNode);
    }

    private InvokeNode readInvokeNode(FrameDescriptor descriptor) throws ParserException {
        if (!find(Values.VAR, Values.INT)) throw new ParserException("read invoke Node");

        return new InvokeNode(SymbolNodeGen.create(descriptor.findOrAddFrameSlot(previousToken().getName())));
    }

    private SymbolNode readSymbolNode(FrameDescriptor descriptor) throws ParserException {
        if (!find(Values.VAR, Values.INT)) throw new ParserException("read invoke Node");

        return SymbolNodeGen.create(descriptor.findOrAddFrameSlot(previousToken().getName()));
    }


    private ExpressionNode readExp() throws ParserException {
        return equality();
    }

    private ExpressionNode equality() throws ParserException {
        ExpressionNode leftExp = readCompare();

        while (find(Values.EQ, Values.NOTQE)) {
            String operation = previousToken().getName();
            ExpressionNode rightExp = readCompare();

            leftExp = switch (operation) {
                case "==" -> EqualExpressionNodeGen.create(leftExp, rightExp);
                case "!=" -> NotEqualExpressionNodeGen.create(leftExp, rightExp);
                default -> {
                    throw new ParserException("parse expression: " + operation);
                }
            };
        }

        return leftExp;
    }


    private ExpressionNode readCompare() throws ParserException {
        ExpressionNode leftExp = readTerm();

        while(find(Values.GT, Values.LT, Values.LOEQ, Values.GOEQ)) {
            String operation = previousToken().getName();
            ExpressionNode rightExp = readTerm();

            leftExp = switch (operation) {
              case ">" -> GTExpressionNodeGen.create(leftExp, rightExp);
              case "<" -> LTExpressionNodeGen.create(leftExp, rightExp);
              case ">=" -> GTExpressionNodeGen.create(leftExp, rightExp);
              case "<=" -> LoEqExpressionNodeGen.create(leftExp, rightExp);
              default -> {
                    throw new ParserException("parse expression: " + operation);
              }
            };
        }

        return leftExp;
    }

    private ExpressionNode readTerm() throws ParserException {
        ExpressionNode leftExp = readFactor();

        while (find(Values.MINUS, Values.PLUS)) {
            String operation = previousToken().getName();
            ExpressionNode rightExp = readFactor();

            leftExp = switch (operation) {
                case "+" -> AddExpressionNodeGen.create(leftExp, rightExp);
                case "-" -> SubtractionExpressionNodeGen.create(leftExp, rightExp);
                default -> throw new ParserException("parse expression: " + operation);
            };
        }

        return leftExp;
    }

    private ExpressionNode readFactor() throws ParserException {
        ExpressionNode leftExp = readSign();

        while (find(Values.ASTER)) {
            String operation = previousToken().getName();
            ExpressionNode rightExp = readSign();
            leftExp = MultiplyExpressionNodeGen.create(leftExp, rightExp);
        }

        return leftExp;
    }

    private ExpressionNode readSign() throws ParserException {
        if (find(Values.MINUS)) {
            return MinusExpressionNodeGen.create(readValues()); //change to FCP Node
        }

        return readValues();
    }

    private ExpressionNode readValues() throws ParserException {
        Token token = current();

        if (find(Values.INT)) {
            return new IntNode(Integer.parseInt(token.getName()));
        } else if (find(Values.VAR)) {
            return  null; //SymbolNodeGen.create() //slot
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
