package compiler.lexical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import helper.Helper;
public class Lexical {
    public String sourceCode;

    private Helper helper;
    private int currentLineIdx;
    private List <String> lines;
    private List <String> reservedWords;
    private List <String> punctuations;

    public Lexical(){

        this.helper = new Helper();

        this.reservedWords = new ArrayList<>(Arrays.asList(
            "read", "write", "if", "then", "end", "else", "repeat", "until"
        ));
        
        this.punctuations = new ArrayList<>(Arrays.asList(
            ";", "<", "\\+", "-", "\\*", "/", ":=", "=", "*" 
        ));

        this.currentLineIdx = 0;
        this.sourceCode = new String();
        this.lines = new ArrayList<String>();
    }

    public void parse(){
        System.out.println("Lexical parsing started.");
    }


    public void printSourceCode(List <String> lines) {
        this.lines = lines;
        String currentLine = new String();
        List <String> words = new ArrayList<String>();
        String tmp = new String();

        for (int i = 0; i < lines.size(); i++) {
            currentLine = lines.get(i);
            this.currentLineIdx = i;
            if (!currentLine.isEmpty()) {
                // words = Arrays.asList(currentLine.split("\\s+"));
                words = this.helper.parseLine(currentLine);
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

        // ADD EOF
    }

    private boolean isID(String word) {
        return word.matches("^[A-Za-z]+$");
    }

    private boolean isNum(String word) {
        return word.matches("^[0-9]+$");
    }
}