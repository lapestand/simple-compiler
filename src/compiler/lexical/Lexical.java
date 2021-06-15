package compiler.lexical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexical {
    public String sourceCode;
    private int currentLineIdx;
    private List <String> lines;
    private List <String> reservedWords;

    private String ASSIGN_OP;

    public Lexical(){

        this.reservedWords = new ArrayList<>(Arrays.asList(
            "read", "write", "if", "then", "end", "else", "repeat", "until"
        ));

        // , ";", "<", "\\+", "-", "\\*", "/", ":=", "=" 
        this.ASSIGN_OP = ":=";

        this.currentLineIdx = 0;
        this.sourceCode = new String();
        this.lines = new ArrayList<String>();
    }

    public void parse(){
        System.out.println("Lexical parsing started.");
    }


    public void printSourceCode(List <String> lines){
        this.lines = lines;
        String currentLine = new String();
        List <String> words = new ArrayList<String>();
        StringBuilder tmp = new StringBuilder();
        String tmp = new String();

        for (int i = 0; i < lines.size(); i++) {
            currentLine = lines.get(i);
            this.currentLineIdx = i;
            if (!currentLine.isEmpty()) {
                words = Arrays.asList(currentLine.split("\\s+"));


                System.out.println(i + ": " + currentLine);
                for (String word : words) {
                    tmp = "";
                    if (word.startsWith(" ")) {
                        tmp += "\t" + i;
                    } else {
                        tmp += i;
                    }
                    if (this.reservedWords.contains(word)) {
                        
                    }
                }
            }
        }
    }


    /*
    public String extractSourceCode(List <String> lines){
        this.lines = lines;
        String currentLine = new String();
        List <String> words = new ArrayList<String>();

        for (int i = 0; i < lines.size(); i++) {
            currentLine = lines.get(i);
            this.currentLineIdx = i;
            if (!currentLine.isEmpty()) {
                currentLine = currentLine.replaceAll("\t","");
                words = Arrays.asList(currentLine.split(" "));

                if (words.get(0).equals("if")) {
                    i = this.ifStatement();
                } else if (words.get(0).equals("repeat")) {
                    i = this.repeatStatement();
                } else if (this.isAssignOP(currentLine)) {
                    i = this.assignStatement();
                } else if (words.get(0).equals("read")) {
                    i = this.readStatement();
                } else if (words.get(0).equals("write")) {
                    i = this.writeStatement();
                } else{
                    System.out.println("UNEXPECTED SYNTAX: " + currentLine);
                }
            }
        }

        String sourceCode = new String();
        return sourceCode;
    }
    */

    private boolean isAssignOP(String currentLine) {
        List <String> words = Arrays.asList(currentLine.split(" "));
        if (words.size() < 3) {
            return false;
        }
        
        if (!this.isID(words.get(0))) {
            System.out.println("bbb " + currentLine);
            return false;
        }
        
        if (!words.get(1).equals(this.ASSIGN_OP)) {
            return false;
        }
        

        return true;

    }

    private boolean isExpr(String currentLine) {
        //  simple-expr compop simple-expr
        


        //  simple-expr
        return false;
    }

    private boolean isID(String word) {
        return word.matches("[a-z][A-Z]+");
    }

    private boolean isNum(String word) {
        return word.matches("[0-9]+");
    }

    private boolean isFactor(String word) {
        return this.isID(word) || this.isNum(word);
    }

    private int writeStatement() {
        System.out.println("WRITE STATEMENT" + this.sourceCode);
        return this.currentLineIdx;
    }

    private int readStatement() {
        System.out.println("READ STATEMENT");
        return this.currentLineIdx;

    }

    private int assignStatement() {
        System.out.println("ASSIGN STATEMENT");
        return this.currentLineIdx;
    }

    private int repeatStatement() {
        System.out.println("REPEAT STATEMENT");
        return this.currentLineIdx;
    }

    private int ifStatement() {
        System.out.println("IF STATEMENT");
        return this.currentLineIdx;
    }

}