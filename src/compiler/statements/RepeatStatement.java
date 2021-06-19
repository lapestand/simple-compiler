package compiler.statements;

import java.util.ArrayList;
import java.util.List;

public class RepeatStatement extends Statement {

    public RepeatStatement(List<String> codeLines, int startIdx, int in) {
        this.ST = REPEAT_ST;
        this.startIdx = startIdx;
        this.codeLines = codeLines;
    }
    
    public void print() {
        System.out.println("Repeat: LINE " + this.startIdx);
    }

    public boolean parse() {
        List <String> lines = new ArrayList <String>();
        for (int i = this.startIdx; i < this.codeLines.size(); i++) {
            
        }

        return true;
    }
}
