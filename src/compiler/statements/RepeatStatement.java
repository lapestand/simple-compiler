package compiler.statements;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import helper.Helper;

public class RepeatStatement extends Statement {

    List <Statement> repeatList;
    List <String> until;
    public RepeatStatement(List<String> codeLines, int startIdx, int in) {
        this.ST = REPEAT_ST;
        this.startIdx = startIdx;
        this.codeLines = codeLines;
        this.in = in;
        this.repeatList = new ArrayList<Statement>();
        this.helper = new Helper();
        this.until = new ArrayList<String>();
    }
    
    public void print() {
        this.helper.printTab(this.in); System.out.println("Repeat");
        for (Statement st : this.repeatList) {
            st.print();
        }
        
        this.helper.printTab(this.in + 1); System.out.println("Op: " + this.until.get(1));
        this.helper.printTab(this.in + 2); this.helper.printFactor(this.until.get(0));
        this.helper.printTab(this.in + 2); this.helper.printFactor(this.until.get(2));
    }

    public boolean parse() {
        // this.helper.printTab(this.in); System.out.println("REPEAT STARTED");
        List <String> lines = new ArrayList <String>();
        String currentLine;
        List <String> words;
        
        if (this.helper.parseLine(this.codeLines.get(this.startIdx)).size() != 1) {
            this.errorLine = this.startIdx;
            return false;
        }
        
        for (int i = this.startIdx + 1; i < this.codeLines.size(); i++) {
            currentLine = this.codeLines.get(i);
            if (!currentLine.isBlank()) {

                words = this.helper.parseLine(this.codeLines.get(i));

                if (this.isindentetionCorrect(currentLine, this.in + 1)) {

                    this.repeatList.add(this.createSt(i));
                    if (this.repeatList.get(this.repeatList.size() - 1) == null) {
                        this.errorLine = this.repeatList.get(this.repeatList.size() - 1).errorLine;
                        return false;
                    } else {
                        // this.helper.printTab(this.in + 1); this.helper.printStatementType(this.repeatList.get(this.repeatList.size() - 1).ST); System.out.println();
                        i = this.repeatList.get(this.repeatList.size() -1).endIdx;
                    }
                    currentLine = currentLine.substring(this.in + 1);
                } else {

                    if (words.get(0).equals("until")) {
                        if (isindentetionCorrect(currentLine, this.in)) {
                            // System.out.println();
                            this.until = words.subList(1, words.size() - 1);
                            // this.helper.printTab(this.in); System.out.println("REPEAT END UNTIL: " + this.until);
                            this.endIdx = i;
                            return true;
                        } else {
                            // System.out.println("RepeatStatement.java Line 63");
                            this.errorLine = i;
                            return false;
                        }
                    } else {
                        // System.out.println("RepeatStatement.java Line 69");
                        this.errorLine = i;
                        return false;
                    }

                }
            }

        }
        //System.out.println("RepeatStatement.java Line 77");
        return false;
    }

    private Statement createSt(int curLine) {
        Statement statement = new Statement();
        List <String> words = this.helper.parseLine(this.codeLines.get(curLine));

        if (words.get(0).equals("read")){
            statement = new ReadStatement(this.codeLines, curLine, this.in + 1);
        } else if (words.get(0).equals("write")) {
            statement = new WriteStatement(this.codeLines, curLine, this.in + 1);
        } else if (words.get(0).equals("if")) {
            statement = new IfStatement(this.codeLines, curLine, this.in + 1);
        } else if (words.get(0).equals("repeat")) {
            statement = new RepeatStatement(this.codeLines, curLine, this.in + 1);
        } else if (words.size() > 1 && words.get(1).equals(":=")) {
            statement = new AssignStatement(this.codeLines, curLine, this.in + 1);
        } else {
            this.errorLine = curLine;
            return null;
        }
        if (!statement.parse()) {
            this.errorLine = statement.errorLine;
            //System.out.println("RepeatStatement.java Line 105");
            return null;
        }

        // this.helper.printTab(statement.in + 1); this.helper.printStatementType(statement.ST); System.out.println(" Start Index: " + statement.startIdx + "\tEnd Index: " + statement.endIdx);
        return statement;
    }

    private boolean isindentetionCorrect(String currentLine, int indentetion) {
        int t = indentetion;
        while (t > 0) {
            t = t - 1;
            if (currentLine.charAt(t) != '\t') {
                return false;
            }
        }
        if (currentLine.charAt(indentetion + 1) == '\t') { return false; }
        return true;
    }

    public String errorLine(){ return Integer.toString(this.errorLine); }
}
