package Parser;

import Lexer.Token;

public class Printer implements Expression.Visitor<String>{
    public String print(Expression exp) {
        return exp.accept(this);
    }

    @Override
    public String visitBinExp(Expression.BinExp exp) {
        return printNested(exp.operator, exp.left, exp.right);
    }

    @Override
    public String visitUnExp(Expression.UnExp exp){
        return printNested(exp.operator, exp.right);
    }

    @Override
    public String visitPrimeExp(Expression.PrimeExp exp) {
        return exp.name;
    }

    private String printNested(Token operator, Expression... exps) {
        StringBuilder string = new StringBuilder();
        string.append("(").append(operator.name).append(" (");

        for (Expression exp : exps) {
            string.append(exp.accept(this)).append(" ");
        }


        string.append("))");

        return string.toString();
    }
}