package compiler.statements;

import java.util.List;

import helper.Helper;


public class ReadStatement extends Statement {

    private String id;

    public ReadStatement(List<String> codeLines, int startIdx, int in) {
        this.codeLines = codeLines;
        this.startIdx = startIdx;
        this.helper = new Helper();
        this.ST = READ_ST;
        this.id = new String();
    }

    public boolean parse() {
        List <String> words = this.helper.parseLine(this.codeLines.get(this.startIdx));
        if ((words.size() != 3) || (!words.get(0).equals("read")) || (reservedWords.contains(words.get(1))) || (!words.get(2).equals(";")) || (!this.helper.isID(words.get(1)))) {
            this.errorLine = this.startIdx;
            return false;
        }
        this.id = words.get(1);
        return true;
    }
    
    public String id() { return this.id; }

    public void print() { System.out.println("Read: " + id); }

    public String errorLine(){ return Integer.toString(this.errorLine); }
}
