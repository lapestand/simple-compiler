package compiler.statements;

import java.util.List;

import helper.Helper;

public class WriteStatement extends Statement {

    public String expr;

    public WriteStatement(List<String> codeLines, int startIdx, int in) {
        this.codeLines = codeLines;
        this.startIdx = startIdx;
        this.endIdx = startIdx;
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
        this.expr = words.get(1);
        return true;
    }

    public String errorLine(){ return Integer.toString(this.errorLine); }

    public void print() {
        this.helper.printTab(this.in); System.out.println("Write");
        
        this.helper.printTab(this.in + 1); 
        if (helper.isID(this.expr)) {
            System.out.print("Id: ");
        } else if (helper.isNum(this.expr)) {
            System.out.print("Num: ");
        } else{
            System.out.println("SYNTAX ERROR");
        }
        System.out.println(this.expr);
    }

    public String expr() { return this.expr; }
}
