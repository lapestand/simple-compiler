package compiler.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import helper.Helper;

public class AssignStatement extends Statement {

    String assignTo;
    List <String> assignFrom;
    int in;
    public AssignStatement(List<String> codeLines, int startIdx, int in) {
        this.ST = ASSIGN_ST;
        this.codeLines = codeLines;
        this.startIdx = startIdx;
        this.helper = new Helper();
        this.in = in;
    }

    public void print() {

        this.helper.printTab(this.in);
        System.out.println("Assign to: " + this.assignTo);

        

        if (assignFrom.size() > 1) {
            this.helper.printTab(this.in + 1); System.out.println("Op: " + this.assignFrom.get(1));
            this.helper.printTab(this.in + 2); this.helper.printFactor(this.assignFrom.get(0));
            this.helper.printTab(this.in + 2); this.helper.printFactor(this.assignFrom.get(2));
        } else {
            this.helper.printTab(this.in + 1); this.helper.printFactor(this.assignFrom.get(0));
        }

    }

    public boolean parse() {
        List <String> words = this.helper.parseLine(this.codeLines.get(this.startIdx));
        List <String> rightOps = new ArrayList<>(Arrays.asList(";", "<", ":=", "=" ));

        if (
            (words.size() != 4 && words.size() != 6) || 
            (!words.get(1).equals(":=")) || 
            (reservedWords.stream().anyMatch(element -> words.contains(element))) || 
            (!words.get(words.size() - 1).equals(";")) || 
            (!this.helper.isID(words.get(0))) ||
            (words.subList(2, words.size() - 1).stream().anyMatch(element -> rightOps.contains(element)))
            ) {
            this.errorLine = this.startIdx;
            return false;
        }

        this.assignTo = words.get(0);
        // assignFrom = this.codeLines.get(this.startIdx).substring(this.codeLines.get(this.startIdx).indexOf(":=") + 3, this.codeLines.get(this.startIdx).length() - 1);

        this.assignFrom = words.subList(2, words.size() - 1);
        
        return true;
    }

    
}
