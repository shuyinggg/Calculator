package calculator.parser;

import calculator.interpreter.AstNode;
import calculator.parser.grammar.CalculatorGrammarBaseVisitor;
import calculator.parser.grammar.CalculatorGrammarLexer;
import calculator.parser.grammar.CalculatorGrammarParser;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.StringReader;

public class Parser {
    public AstNode parse(String rawInput) {
        try {
            CharStream input = new ANTLRInputStream(new StringReader(rawInput));
            CalculatorGrammarLexer lexer = new CalculatorGrammarLexer(input);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            CalculatorGrammarParser parser = new CalculatorGrammarParser(tokenStream);

            CalculatorGrammarParser.ProgramContext entryPoint = parser.program();
            return new AstConverter().visitProgram(entryPoint);
        } catch (IOException ex) {
            throw new RuntimeException("Compile error", ex);
        }
    }

    private static class AstConverter extends CalculatorGrammarBaseVisitor<AstNode> {
        private IList<AstNode> asList(AstNode... nodes) {
            IList<AstNode> list = new DoubleLinkedList<>();
            for (AstNode node : nodes) {
                list.add(node);
            }
            return list;
        }

        @Override
        public AstNode visitProgram(CalculatorGrammarParser.ProgramContext ctx) {
            IList<AstNode> params = new DoubleLinkedList<>();
            for (CalculatorGrammarParser.StatementContext stmt : ctx.statements) {
                params.add(this.visit(stmt));
            }
            return new AstNode("block", params);
        }

        @Override
        public AstNode visitAssignStmt(CalculatorGrammarParser.AssignStmtContext ctx) {
            return new AstNode(
                    "assign",
                    this.asList(
                            new AstNode(ctx.varName.getText()),
                            this.visit(ctx.expr)));
        }

        @Override
        public AstNode visitExprStmt(CalculatorGrammarParser.ExprStmtContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitPowExprBin(CalculatorGrammarParser.PowExprBinContext ctx) {
            return new AstNode(
                    ctx.op.getText(),
                    this.asList(this.visit(ctx.left), this.visit(ctx.right)));
        }

        @Override
        public AstNode visitPowExprSingle(CalculatorGrammarParser.PowExprSingleContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitNegExprUnary(CalculatorGrammarParser.NegExprUnaryContext ctx) {
            return new AstNode("negate", this.asList(this.visit(ctx.expr)));
        }

        @Override
        public AstNode visitNegExprSingle(CalculatorGrammarParser.NegExprSingleContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitAddExprBin(CalculatorGrammarParser.AddExprBinContext ctx) {
            return new AstNode(
                    ctx.op.getText(),
                    this.asList(this.visit(ctx.left), this.visit(ctx.right)));
        }

        @Override
        public AstNode visitAddExprSingle(CalculatorGrammarParser.AddExprSingleContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitMultExprBin(CalculatorGrammarParser.MultExprBinContext ctx) {
            return new AstNode(
                    ctx.op.getText(),
                    this.asList(this.visit(ctx.left), this.visit(ctx.right)));
        }

        @Override
        public AstNode visitMultExprSingle(CalculatorGrammarParser.MultExprSingleContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitNumber(CalculatorGrammarParser.NumberContext ctx) {
            return new AstNode(Double.parseDouble(ctx.value.getText()));
        }

        @Override
        public AstNode visitRawString(CalculatorGrammarParser.RawStringContext ctx) {
            throw new UnsupportedOperationException();
        }

        @Override
        public AstNode visitVariable(CalculatorGrammarParser.VariableContext ctx) {
            return new AstNode(ctx.getText());
        }

        @Override
        public AstNode visitFuncName(CalculatorGrammarParser.FuncNameContext ctx) {
            IList<AstNode> params = this.parseArgList(ctx.args);
            return new AstNode(ctx.funcName.getText(), params);
        }

        @Override
        public AstNode visitParenExpr(CalculatorGrammarParser.ParenExprContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitArglist(CalculatorGrammarParser.ArglistContext ctx) {
            throw new UnsupportedOperationException();
        }

        private IList<AstNode> parseArgList(CalculatorGrammarParser.ArglistContext args) {
            IList<AstNode> out = new DoubleLinkedList<>();
            for (CalculatorGrammarParser.AddExprContext item : args.values) {
                out.add(this.visit(item));
            }
            return out;
        }
    }
}
