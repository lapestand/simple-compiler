package compiler.statements;

import java.util.List;

public class IfStatement extends Statement {

    public IfStatement(List<String> codeLines, int startIdx, int in) {
        this.ST = IF_ST;
        this.codeLines = codeLines;
        this.startIdx = startIdx;
    }

    public void print() {
        System.out.println("If: LINE " + this.startIdx);
    }

    public boolean parse() {
        return true;
    }
    
}
