// Generated from /home/zzigor/Projects/TruffleProjections/src/main/java/truffle/parser/fcp.g4 by ANTLR 4.10.1
package common.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link fcpParser}.
 */
public interface fcpListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link fcpParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(fcpParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link fcpParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(fcpParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link fcpParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(fcpParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link fcpParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(fcpParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link fcpParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(fcpParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link fcpParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(fcpParser.EntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link fcpParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(fcpParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link fcpParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(fcpParser.LiteralContext ctx);
}