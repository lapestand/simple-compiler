package compiler.lexical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lexical {
    public String sourceCode;
    private int currentLineIdx;
    private List <String> lines;
    private List <String> reservedWords;
    private List <String> punctuations;

    private String ASSIGN_OP;

    public Lexical(){

        this.reservedWords = new ArrayList<>(Arrays.asList(
            "read", "write", "if", "then", "end", "else", "repeat", "until"
        ));
        
        this.punctuations = new ArrayList<>(Arrays.asList(
            ";", "<", "\\+", "-", "\\*", "/", ":=", "=", "*" 
        ));

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
        String tmp = new String();

        for (int i = 0; i < lines.size(); i++) {
            currentLine = lines.get(i);
            this.currentLineIdx = i;
            if (!currentLine.isEmpty()) {
                // words = Arrays.asList(currentLine.split("\\s+"));
                words = Stream.of(currentLine.split("\\s+")).collect(Collectors.toCollection(ArrayList::new));
                words.removeIf(item -> item == null || "".equals(item));
                
                if (words.get(words.size() - 1).contains(";")) {
                    words.set(
                        words.size() - 1,
                        words.get(words.size() - 1).substring(
                            0, words.get(words.size() - 1).length() - 1)
                    );
                    words.add(";");
                }


                System.out.println((i + 1) + ": " + currentLine);
                for (String word : words) {
                    tmp = "\t" + (i + 1) + ": ";
                    
                    System.out.print(tmp);
                    if (this.reservedWords.contains(word)) {
                        System.out.println("reserved word: " + word);
                    }else if (this.punctuations.contains(word)) {
                        System.out.println(word);
                    } else if (this.isID(word)) {
                        System.out.println("ID, name= [" + word + "]");
                    } else if (this.isNum(word)) {
                        System.out.println("NUM, name= [" + word + "]");
                    } else {
                        // ERROR DON'T FORGET
                        System.out.print("UNKNOWN, name= [" + word + "] ");
                        System.out.println(word.matches("[0-9]+"));
                    }
                }
                System.out.println();
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
        return word.matches("^[A-Za-z]+$");
    }

    private boolean isNum(String word) {
        return word.matches("^[0-9]+$");
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