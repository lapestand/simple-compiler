package compiler.statements;

import java.util.ArrayList;
import java.util.List;

import helper.Helper;

public class IfStatement extends Statement {
    List <Statement> ifList, elseList;
    
    boolean hasElse;
    List <String> condition;
    public IfStatement(List<String> codeLines, int startIdx, int in) {
        this.ST = IF_ST;
        this.codeLines = codeLines;
        this.startIdx = startIdx;
        this.helper = new Helper();   
        this.in = in;
        this.ifList = new ArrayList<Statement>();
        this.elseList = new ArrayList<Statement>();
        this.hasElse = false;
        this.condition = new ArrayList<String>();
    }

    public void print() {
        this.helper.printTab(this.in); System.out.println("if");
        this.helper.printTab(this.in + 1); System.out.println("Op: " + this.condition.get(1));
        this.helper.printTab(this.in + 2); this.helper.printFactor(this.condition.get(0));
        this.helper.printTab(this.in + 2); this.helper.printFactor(this.condition.get(2));
        for (Statement st : this.ifList) {
            st.print();
        }
        if (this.hasElse) {
            this.helper.printTab(this.in); System.out.println("else");
            for (Statement st : this.elseList) {
                st.print();
            }
        }
        
    }

    public boolean parse() {
        boolean inIf = true;
        // this.helper.printTab(this.in); System.out.print("IF STARTED ");
        List <String> lines = new ArrayList <String>();
        String currentLine;
        List <String> words;
        
        words = this.helper.parseLine(this.codeLines.get(this.startIdx));
        if (!(words.get(0).equals("if") && words.get(words.size() - 1 ).equals("then"))) {
            this.errorLine = this.startIdx;
            return false;
        }

        this.condition = words.subList(1, words.size() - 1);
        // System.out.println("CONDITION: " + this.condition);
        
        for (int i = this.startIdx + 1; i < this.codeLines.size(); i++) {
            currentLine = this.codeLines.get(i);
            if (!currentLine.isBlank()) {

                words = this.helper.parseLine(this.codeLines.get(i));

                if (this.isindentetionCorrect(currentLine, this.in + 1)) {
                    if (inIf) {
                        this.ifList.add(this.createSt(i));
                    } else {
                        this.elseList.add(this.createSt(i));
                    }
                    if (this.ifList.get(this.ifList.size() - 1) == null) {
                        this.errorLine = this.ifList.get(this.ifList.size() - 1).errorLine;
                        return false;
                    } else {
                        if (inIf) {
                            i = this.ifList.get(this.ifList.size() -1).endIdx;    
                        } else {
                            i = this.elseList.get(this.elseList.size() -1).endIdx;    
                        }
                        
                    }
                    currentLine = currentLine.substring(this.in + 1);
                } else {
                    if (words.get(0).equals("end")) {
                        if (isindentetionCorrect(currentLine, this.in)) {
                            this.endIdx = i;
                            // System.out.println("\n"); this.helper.printTab(this.in); System.out.println("IF END - LINE " + i);
                            return true;
                        } else {
                            // System.out.println("WTF");
                            return false;
                        }
                    } else if (words.get(0).equals("else")) {
                        if (isindentetionCorrect(currentLine, this.in)) {
                            this.hasElse = true;
                            inIf = false;
                            // this.helper.printTab(this.in); System.out.println("ELSE STARTED");
                        } else {
                            this.errorLine = i;
                            return false;
                        }
                    } else {
                        this.errorLine = i;
                        return false;
                    }
                }

            }
        }
        // System.out.println("IfStatement.java Line 80");
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
            // System.out.println("RepeatStatement.java Line 112");
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
