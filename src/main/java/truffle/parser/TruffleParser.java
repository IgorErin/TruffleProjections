package truffle.parser;

import common.fcpBaseListener;
import common.fcpLexer;
import common.fcpParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import simple.SimpleFcpParser;
import truffle.nodes.FCPNode;
import truffle.nodes.exps.BooleanNode;
import truffle.nodes.exps.IntNode;
import truffle.nodes.stmt.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class TruffleParser extends fcpBaseListener {
    private final Stack<LinkedList<FCPNode>> nodeListStack = new Stack<LinkedList<FCPNode>>();

    public List<FCPNode> getRootNodeList() throws RuntimeException {
        if (nodeListStack.size() == 1) {
            return getLastList();
        }

        throw new RuntimeException("Parse exception, not complete root list or empty list");
    }

    private List<FCPNode> getLastList() {
        if (nodeListStack.size() >= 1) {
            return nodeListStack.pop();
        }

        throw new RuntimeException("Parse exception, empty list queue");
    }

    private void addNodeToCurrentList(FCPNode newNode) {
        if (nodeListStack.size() > 0) {
            LinkedList<FCPNode> currentList = nodeListStack.pop();

            currentList.add(newNode);
            nodeListStack.push(currentList);
        }
    }

    @Override public void enterProgram(fcpParser.ProgramContext ctx) {
        nodeListStack.push(new LinkedList<FCPNode>());
    }

    @Override public void enterList(fcpParser.ListContext ctx) {
        nodeListStack.push(new LinkedList<FCPNode>());
    }

    @Override public void exitList(fcpParser.ListContext ctx) {
        if (nodeListStack.size() > 1) {
            addNodeToCurrentList(Statement.check(getLastList()));
        }
    }

    @Override public void exitLiteral(fcpParser.LiteralContext ctx) {
        if (ctx.INT() != null) {
            int intNumber = Integer.parseInt(ctx.INT().getText());

            addNodeToCurrentList(new IntNode(intNumber));
        } else if (ctx.SYMBOL() != null) {
           // addNodeToCurrentList(new ReadVarNode(ctx.SYMBOL().getText()));
        } else if (ctx.BOOLEAN() != null) {
            boolean value = Objects.equals(ctx.BOOLEAN().getText(), "#t");

            addNodeToCurrentList(new BooleanNode(value));
        } else {
            throw new RuntimeException("Unknown lexeme: " + ctx.getText());
        }
    }

    public List<FCPNode> getAst(String fileName) {
        try {
            org.antlr.v4.runtime.CharStream input = CharStreams.fromFileName(fileName);

            fcpLexer lexer = new fcpLexer(input);
            fcpParser parser = new fcpParser(new CommonTokenStream(lexer));
            SimpleFcpParser truffleParser = new SimpleFcpParser();

            parser.addParseListener(truffleParser);
            parser.program();

            return null;//truffleParser.getRootNodeList();
        } catch (Exception e) {
            System.out.println("Parse exception: " + e.getMessage());
            return new LinkedList<>();
        }
    }
}
