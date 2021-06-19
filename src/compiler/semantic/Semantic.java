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

    int ID_EXPECTED = 0;

    public Semantic() {

        this.helper = new Helper();
        this.removeList = new ArrayList<>(Arrays.asList(
            "read", "write", "if", "then", "end", "else", "repeat",
            "until", ";", "<", "\\+", "-", "\\*", "/", ":=", "=" ));
        this.lineSyntaxList = new ArrayList <String>();
    }

    public void printSyntaxTree(List <String> lines){
        this.lines = lines;
        List <String> words;
        List <Statement> statements = new ArrayList <Statement>();
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).isEmpty()) {
                words = this.helper.parseLine(lines.get(i));

                if (words.get(0).equals("read")){
                    
                    statements.add(new ReadStatement(this.lines, i, 0));
                    if (!statements.get(statements.size() - 1).parse()) {
                        System.out.println("UNEXPECTED SYNTAX AT LINE " + statements.get(statements.size() - 1).errorLine());
                    }
                    System.out.println("read");
                    
                    
                } else if (words.get(0).equals("write")) {

                    
                    statements.add(new WriteStatement(this.lines, i, 0));
                    if (!statements.get(statements.size() - 1).parse()) {
                        System.out.println("UNEXPECTED SYNTAX AT LINE" + i);
                    }
                    System.out.println("write");

                    

                } else if (words.get(0).equals("if")) {

                    System.out.println("if");
                    
                    statements.add(new IfStatement(this.lines, i, 0));
                    if (!statements.get(statements.size() - 1).parse()) {
                        System.out.println("UNEXPECTED SYNTAX AT LINE" + i);
                    }
                    

                } else if (words.get(0).equals("repeat")) {
                    System.out.println("repeat");

                    
                    statements.add(new RepeatStatement(this.lines, i, 0));
                    if (!statements.get(statements.size() - 1).parse()) {
                        System.out.println("UNEXPECTED SYNTAX AT LINE" + i);
                    }
                    
                } else if (words.size() > 1 && words.get(1).equals(":=")) {
                    System.out.println("assign");
                    
                    statements.add(new AssignStatement(this.lines, i, 0));
                    if (!statements.get(statements.size() - 1).parse()) {
                        System.out.println("UNEXPECTED SYNTAX AT LINE" + i);
                    }
                    
                } else if (words.get(0).equals("end")) {
                    System.out.println("END ST");   
                } else {
                    System.out.println("UNEXPECTED SYNTAX AT LINE " + i);
                    
                }
                System.out.println(words);
            }
        }

        System.out.println("\n\n\n");
        for (Statement statement : statements) {
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


    public void printSyntaxTreeOld(List <String> lines) {
        this.lines = lines;
        String currentLine = new String(), tmp = new String();
        List <String> words;
        int currentLineIdx;
        for (int i = 0; i < lines.size(); i++) {
            currentLine = lines.get(i);
            currentLineIdx = i;
            if (!currentLine.isEmpty()) {
                words = this.helper.parseLine(currentLine);
                // BURADA KALDIM
                System.out.println(words);                
                if (words.get(0).equals("read")){
                    i = this.readStatement(currentLineIdx);
                } else if (words.get(0).equals("write")) {
                    i = this.writeStatement(currentLineIdx);
                } else if (words.get(0).equals("if")) {
                    i = this.ifStatement(currentLineIdx);
                } else if (words.get(0).equals("repeat")) {
                    i = this.repeatStatement(currentLineIdx);
                } else if (words.size() > 1 && words.get(1).equals(":=")) {
                    i = this.assignStatement(currentLineIdx);
                } else if (words.get(0).equals("end")) {
                    System.out.println("END ST");   
                } else {
                    System.out.println("UNEXPECTED SYNTAX");
                    return;
                }
                System.out.println(words);
            }
        }


        System.out.println("SYNTAX TREE\n\n");

        System.out.println(this.lineSyntaxList);
    }

    private boolean isID(String word) {
        return word.matches("^[A-Za-z]+$");
    }

    private boolean isNum(String word) {
        return word.matches("^[0-9]+$");
    }

    private boolean isAssignOP(String word) {
        return word.equals(":=");
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
    
    private boolean isFactor(String word) {
        return this.isID(word) || this.isNum(word);
    }

    private int writeStatement(int lineIdx) {
        System.out.println("WRITE STATEMENT");
        return lineIdx;
    }

    private int readStatement(int lineIdx) {
        List <String> words = this.helper.parseLine(this.lines.get(lineIdx));
        if (this.isID(words.get(1))) {
            lineSyntaxList.add(new String("Read: " + words.get(1)));
            System.out.println(lineSyntaxList.get(lineSyntaxList.size() - 1));
            return lineIdx;    
        }else {
            return this.error_on(lineIdx, ID_EXPECTED);
        }
    }

    private int error_on(int lineIdx, int error_code) {
        return error_code;
    }

    private int assignStatement(int lineIdx) {
        List <String> words = this.helper.parseLine(this.lines.get(lineIdx));
        if (this.isID(words.get(0)) && this.isAssignOP(words.get(1)) && this.isExpr(words.subList(2, words.size() - 1))) {
            
        } else {
            
        }
        return lineIdx;
    }

    private int repeatStatement(int lineIdx) {
        System.out.println("REPEAT STATEMENT");
        return lineIdx;
    }

    private int ifStatement(int lineIdx) {
        System.out.println("IF STATEMENT");
        return lineIdx;
    }

}
