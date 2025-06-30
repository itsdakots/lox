package jlox;

import jlox.Expr.Binary;
import jlox.Expr.Grouping;
import jlox.Expr.Literal;
import jlox.Expr.Unary;

// Reverse Polish Notation
public class RPNPrinter implements Expr.Visitor<String> {

    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Binary expr) {
        return build(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Grouping expr) {
        return build("", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Literal expr) {
        if (expr.value == null) 
            return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Unary expr) {
        return build(expr.operator.lexeme, expr.right);
    }

    private String build(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        for (Expr expr : exprs) {
            builder.append(expr.accept(this));
            if (exprs.length > 1) builder.append(" ");
        }

        builder.append(name);

        return builder.toString();
    }

    public static void main(String[] args) {
        Expr grouping1 = new Expr.Grouping(
            new Expr.Binary(new Expr.Literal(1), new Token(TokenType.PLUS, "+", null, 1), new Expr.Literal(2)));

        Expr grouping2 = new Expr.Grouping(
                new Expr.Binary(new Expr.Literal(4), new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(3)));
        Expr expression = new Expr.Binary(
                grouping1,
                new Token(TokenType.STAR, "*", null, 1),
                grouping2
        );

        System.out.println(new RPNPrinter().print(expression));
    }
    
}
