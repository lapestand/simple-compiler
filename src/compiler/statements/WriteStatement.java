package compiler.statements;

import java.util.List;

import helper.Helper;

public class WriteStatement extends Statement {

    String expr;
    int in;

    public WriteStatement(List<String> codeLines, int startIdx, int in) {
        this.codeLines = codeLines;
        this.startIdx = startIdx;
        this.helper = new Helper();
        this.ST = WRITE_ST;
        this.in = in;
    }

    public boolean parse() {
        List <String> words = this.helper.parseLine(this.codeLines.get(this.startIdx));
        if (
            (words.size() != 3) || 
            (!words.get(0).equals("write")) || 
            (reservedWords.contains(words.get(1))) || 
            (!words.get(2).equals(";")) || 
            (!(helper.isID(words.get(1)) || helper.isNum(words.get(1))))
            ) {
            this.errorLine = this.startIdx;
            return false;
        }
        
        // read n;
        this.expr = words.get(1);

        System.out.println("\n\n" + this.expr + "\n\n");
        System.out.println("\n\n" + words + "\n\n");
        return true;

        // this.id = words.get(1);
        
    }

    public void print() {
        int t = this.in;

        while (t > 0) {
            System.out.print("\t");
        }

        System.out.println("Write");
        t = this.in + 1;

        while (t > 0) {
            System.out.print("\t");
            t = t - 1;
        }

        if (helper.isID(this.expr)) {
            System.out.print("Id: ");
        } else if (helper.isNum(this.expr)) {
            System.out.print("Num: ");
        } else{
            System.out.println("SYNTAX ERROR");
        }
        System.out.println(this.expr);
    }
}
