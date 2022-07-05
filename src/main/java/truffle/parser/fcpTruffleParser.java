package truffle.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import simple.Environment;
import simple.nodes.Node;
import simple.nodes.exps.BooleanNode;
import simple.nodes.exps.IntNode;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class fcpTruffleParser extends fcpBaseListener {
    private final Stack<LinkedList<Node>> nodeListStack = new Stack<LinkedList<Node>>();

    public Node getRootListNode() throws RuntimeException {
        if (nodeListStack.size() == 1) {
            return new ListNode(getLastList());
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

    //@Override public void enterProgram(fcpParser.ProgramContext ctx) { System.out.println(ctx.list()); }

    //@Override public void exitProgram(fcpParser.ProgramContext ctx) { }

    @Override public void enterList(fcpParser.ListContext ctx) {
        nodeListStack.push(new LinkedList<Node>());
    }

    @Override public void exitList(fcpParser.ListContext ctx) {
        if (nodeListStack.size() > 1) {
            addNodeToCurrentList(new ListNode(getLastList()));
        }
    }

    @Override public void enterLiteral(fcpParser.LiteralContext ctx) {
        System.out.println("1 --->>> " + ctx.getText());

        if (ctx.INT() != null) {
            int intNumber = Integer.parseInt(ctx.INT().getText());
            addNodeToCurrentList(new IntNode(intNumber));
        } else if (ctx.SYMBOL() != null) {
            addNodeToCurrentList(new VarNode(ctx.SYMBOL().getText()));
        } else if (ctx.BOOLEAN() != null) {
            addNodeToCurrentList(new BooleanNode(ctx.BOOLEAN().getText()));
        } else {
            //throw new RuntimeException("Unknown lexeme in parse process" + ctx.getText());
        }
    }

    @Override public void exitLiteral(fcpParser.LiteralContext ctx) {
        System.out.println("2 --->>> " + ctx.getText());
    }

    //@Override public void enterEveryRule(ParserRuleContext ctx) { }

    //@Override public void exitEveryRule(ParserRuleContext ctx) { }

    //@Override public void visitTerminal(TerminalNode node) { }

    @Override public void visitErrorNode(ErrorNode node) { }

    public static void main(String[] args) {
        try {
            CodePointCharStream input = CharStreams.fromString("(ffw)");

            fcpLexer lexer = new fcpLexer(input);
            fcpParser parser = new fcpParser(new CommonTokenStream(lexer));

            fcpTruffleParser truffleParser = new fcpTruffleParser();
            parser.addParseListener(truffleParser);
            parser.program();

            Node rootNode = truffleParser.getRootListNode();
            Environment newEnv = new Environment();

            System.out.println(rootNode.eval(newEnv));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}