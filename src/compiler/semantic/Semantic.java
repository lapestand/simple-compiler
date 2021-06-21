package compiler.semantic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import helper.Helper;
import compiler.statements.*;
public class Semantic {

    private List <String> removeList;
    private List <String> lines;
    private Helper helper;
    private List <String> lineSyntaxList;
    private List <Statement> statements;
    private List <String> errors;

    int ID_EXPECTED = 0;
    public boolean isParsed;

    public Semantic() {
        this.statements = new ArrayList <Statement>();
        this.helper = new Helper();
        this.removeList = new ArrayList<>(Arrays.asList(
            "read", "write", "if", "then", "end", "else", "repeat",
            "until", ";", "<", "\\+", "-", "\\*", "/", ":=", "=" ));
        this.lineSyntaxList = new ArrayList <String>();
        this.isParsed = true;
    }

    private boolean parse(List <String> lines) {
        this.lines = lines;
        List <String> words;
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).isBlank()) {
                words = this.helper.parseLine(lines.get(i));

                if (words.get(0).equals("read")){
                    this.statements.add(new ReadStatement(this.lines, i, 0));
                } else if (words.get(0).equals("write")) {
                    this.statements.add(new WriteStatement(this.lines, i, 0));
                } else if (words.get(0).equals("if")) {
                    this.statements.add(new IfStatement(this.lines, i, 0));
                } else if (words.get(0).equals("repeat")) {
                    this.statements.add(new RepeatStatement(this.lines, i, 0));
                } else if (words.size() > 1 && words.get(1).equals(":=")) {
                    this.statements.add(new AssignStatement(this.lines, i, 0));
                } else {
                    System.out.println("UNEXPECTED SYNTAX AT LINE " + (i + 1));
                    return false;
                }

                if (!this.statements.get(this.statements.size() - 1).parse()) {
                    System.out.println("UNEXPECTED SYNTAX AT LINE " + Integer.parseInt(this.statements.get(this.statements.size() - 1).errorLine()) + 1);
                    return false;
                }

                i = this.statements.get(this.statements.size() - 1).endIdx;
                // this.helper.printStatementType(this.statements.get(this.statements.size() - 1).ST); System.out.println(" Start Index: " + this.statements.get(this.statements.size() - 1).startIdx + "\tEnd Index: " + this.statements.get(this.statements.size() - 1).endIdx);


                // System.out.println(words);
            }
        }
        return true;
    }

    public void printSyntaxTree(List <String> lines){
        if (!this.parse(lines)) {
            this.isParsed = false;
            return;
        }

        System.out.println("\n\n\n");
        for (Statement statement : this.statements) {
            statement.print();
        }

    }

    public void printSemanticTable(List<String> lines) {
        String line = new String();
        List <String> newLines = new ArrayList <String>();
        List <String> lineContent;
        SymbolTable symbolTable = new SymbolTable();
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);

            for (int j = 0; j < this.removeList.size(); j++) {
                line = line.replaceAll(this.removeList.get(j), "");
            }

            line = line.replaceAll("[0-9]", "");
            line = line.strip();
            lineContent = Arrays.asList(line.split("\\s+"));

            if (!line.isEmpty()) {
                for (String symbol : lineContent) {
                    symbolTable.add(symbol, i + 1);
                }
            }
        }

        symbolTable.printTable();
    }

    private boolean isExpr(List <String> words) {
        //  simple-expr compop simple-expr
        for (int i = 0; i < words.size(); i++) {
            if (i % 2 == 1) {
                
            }
        }
        //  simple-expr
        return true;
    }
    
    private int error_on(int lineIdx, int error_code) {
        return error_code;
    }

    public boolean checkTypes() {
        boolean hasError = false;
        for (Statement st : this.statements) {
            st.checkTypes();
            if (st.ST == Statement.IF_ST || st.ST == Statement.REPEAT_ST) {
                if (!st.errors.isEmpty()) {
                    hasError = true;
                    for (String error : st.errors) {
                        System.out.println(error);
                    }
                }
            }
        }

        if (!hasError) {
            System.out.println("No error!");
        }
        return !hasError;
    }

    public List <Statement> statements() {
        return this.statements;
    }
}
