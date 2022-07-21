// Generated from /home/zzigor/Projects/TruffleProjections/src/main/java/truffle/parser/fcp.g4 by ANTLR 4.10.1
package common.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link fcpParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface fcpVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link fcpParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(fcpParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link fcpParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(fcpParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link fcpParser#entry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntry(fcpParser.EntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link fcpParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(fcpParser.LiteralContext ctx);
}