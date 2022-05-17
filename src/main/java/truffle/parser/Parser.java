package truffle.parser;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.RootNode;
import exceptions.ParserException;

import lexer.Token;
import lexer.Values;
import truffle.nodes.*;
import truffle.nodes.expressions.*;
import truffle.types.FCPFunction;

import java.util.Collections;
import java.util.List;

public class Parser {
    public List<Token> listOfTokens;
    private int position;

    public Parser(List<Token> listOfTokens) {
        FrameDescriptor descriptor = new FrameDescriptor();

        this.listOfTokens = listOfTokens;
        this.position = 0;
    }



    /*fun readProgram(): MutableList<Statement> {
        val listOfNodes = mutableListOf<Statement>()

        if (find(READ)) {
            listOfNodes.add(readReadBlock())
        }

        while (find(DEFINE)) {
            listOfNodes.add(readDefineBlock())
        }

        while(find(VAR, INT)) {
            listOfNodes.add(InvokeNode(previousToken().name))
        }

        return listOfNodes
    }

    private fun readReadBlock(): ReadNode {
        val listOfVarNodes = mutableListOf<WriteVarNode>()

        while (find(VAR)) {
            listOfVarNodes.add(WriteVarNode(previousToken().name, InputNode()))

            when {
                find(COMMA) -> {}
                find(SEMCOL) -> break
                else -> throw ParserException("strange token: ${current().name}")
            }
        }

        return ReadNode(listOfVarNodes)
    }

    private fun readDefineBlock(): DefineNode {
        if(!find(LABEL)) throw ParserException(TODO("define block"))

        val nameOfLabel = previousToken().name
        val listOfStatements = mutableListOf<Statement>()

        while (find(VAR)) {
            listOfStatements.add(readAssignment())
        }

        val lastStatement = when {
            find(IF) -> readIfStatement()
            find(RETURN) -> ReturnNode(readValue())
            find(GOTO) -> readInvokeNode()
            else -> throw ParserException(TODO("last stmt"))
        }
        listOfStatements.add(lastStatement)

        return DefineNode(LabelNode(listOfStatements, nameOfLabel))
    }*/


    private FCPNode readDefineNode() throws ParserException {
        if (!find(Values.DEFINE)) throw new ParserException("define expected, found:" + current().getName());


    }

    private FCPFunction readFCPFunction(FrameDescriptor descriptor) throws ParserException {
        List<FCPNode> bodyNodes = Collections.emptyList();

        while (find(Values.VAR)) {
            bodyNodes.add(readAssignment(descriptor));
        }

        Values value = current().getValue();
        move();
        FCPNode lastNode = switch (value) {
            case RETURN -> readReturnNode(descriptor);
            case GOTO -> readGoto() //TODO
            case IF -> readIfStatement(descriptor);
            default -> throw new ParserException("unexpected value: " + value);
        };

        bodyNodes.add(lastNode);
        return FCPFunction.create(bodyNodes.toArray(), descriptor); //TODO()
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
                case "-" -> SubtractionExpressionNodeGen.create(leftExp, rightExp); //TODO
                default -> throw new ParserException("parse expression: " + operation);
            };
        }

        return leftExp;
    }

    private ExpressionNode readFactor() throws ParserException {
        ExpressionNode leftExp = readSign(); //TODO

        while (find(Values.ASTER)) {
            String operation = previousToken().getName();
            ExpressionNode rightExp = readSign(); //TODO()
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
