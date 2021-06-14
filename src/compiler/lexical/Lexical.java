package compiler.lexical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexical {
    public String sourceCode = new String();
    private int currentLineIdx = 0;

    public void parse(){
        System.out.println("Lexical parsing started.");
    }

    public String extractSourceCode(List <String> lines){
        String currentLine = new String();
        List <String> words = new ArrayList<String>();

        for (int i = 0; i < lines.size(); i++) {
            currentLine = lines.get(i);
            this.currentLineIdx = i;
            if (!currentLine.isEmpty()) {
                if (currentLine.startsWith("if ")) {
                    i = this.ifStatement();
                } else if (currentLine.startsWith("repeat ")) {
                    i = this.repeatStatement();
                } else if (currentLine.startsWith("assign ")) {
                    i = this.assignStatement();
                } else if (currentLine.startsWith("read ")) {
                    i = this.readStatement();
                } else if (currentLine.startsWith("write ")) {
                    i = this.writeStatement();
                }

                words = Arrays.asList(currentLine.split(" "));
                for (String string : words) {
                    System.out.println(string);
                }
            }
            break;
        }

        String sourceCode = new String();
        return sourceCode;
    }

    private int writeStatement() {
        System.out.println("write statement");
        return 0;
    }

    private int readStatement() {
        System.out.println("write statement");
        return 0;
    }

    private int assignStatement() {
        System.out.println("write statement");
        return 0;
    }

    private int repeatStatement() {
        System.out.println("write statement");
        return 0;
    }

    private int ifStatement() {
        System.out.println("write statement");
        return 0;
    }
}