package simple;

import org.antlr.runtime.CharStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import simple.nodes.Node;
import simple.nodes.exps.BooleanNode;
import simple.nodes.exps.IntNode;
import simple.nodes.exps.VarNode;
import simple.nodes.stmts.Statement;
import common.parser.fcpBaseListener;
import common.parser.fcpLexer;
import common.parser.fcpParser;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class SimpleFcpParser extends fcpBaseListener {
    private final Stack<LinkedList<Node>> nodeListStack = new Stack<LinkedList<Node>>();

    public List<Node> getRootNodeList() throws RuntimeException {
        if (nodeListStack.size() == 1) {
            return getLastList();
        }

        throw new RuntimeException("Parse exception, not complete root list or empty list");
    }

    private List<Node> getLastList() {
        if (nodeListStack.size() >= 1) {
            return nodeListStack.pop();
        }

        throw new RuntimeException("Parse exception, empty list queue");
    }

    private void addNodeToCurrentList(Node newNode) {
        if (nodeListStack.size() > 0) {
            LinkedList<Node> currentList = nodeListStack.pop();

            currentList.add(newNode);
            nodeListStack.push(currentList);
        }
    }

    @Override public void enterProgram(fcpParser.ProgramContext ctx) {
        nodeListStack.push(new LinkedList<Node>());
    }

    @Override public void enterList(fcpParser.ListContext ctx) {
        nodeListStack.push(new LinkedList<Node>());
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
            addNodeToCurrentList(new VarNode(ctx.SYMBOL().getText()));
        } else if (ctx.BOOLEAN() != null) {
            boolean value = Objects.equals(ctx.BOOLEAN().getText(), "#t");

            addNodeToCurrentList(new BooleanNode(value));
        } else {
            throw new RuntimeException("Unknown lexeme: " + ctx.getText());
        }
    }

    private List<Node> parse(org.antlr.v4.runtime.CharStream input) {
        fcpLexer lexer = new fcpLexer(input);
        fcpParser parser = new fcpParser(new CommonTokenStream(lexer));
        SimpleFcpParser truffleParser = new SimpleFcpParser();

        parser.addParseListener(truffleParser);
        parser.program();

        return truffleParser.getRootNodeList();
    }

    public List<Node> getAstFromFile(String fileName) {
        try {
            org.antlr.v4.runtime.CharStream input = CharStreams.fromFileName(fileName);

            return parse(input);
        } catch (Exception e) {
            throw new RuntimeException("Parse exception: " + e.getMessage());
        }
    }

    public List<Node> getAstFromReader(Reader reader) throws IOException {
        return parse(CharStreams.fromReader(reader));
    }
}
