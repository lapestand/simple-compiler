package compiler.codegen;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import compiler.statements.AssignStatement;
import compiler.statements.IfStatement;
import compiler.statements.ReadStatement;
import compiler.statements.RepeatStatement;
import compiler.statements.Statement;
import compiler.statements.WriteStatement;
import helper.Helper;


public class CodeGeneration {
    public List <String> codeLines;
    private LinkedHashMap<String, Integer> variables;
    private int pc;
    private boolean check;
    private Helper helper;
    private String notReadyStatement;
    private int tempCount;
    public boolean isParsed;

    public CodeGeneration(int pc) {
        this.codeLines = new ArrayList<String>();
        this.variables = new LinkedHashMap<>();
        if (pc <= 0) {
            System.out.println("PC MUST BE BIGGER THAN 0");
            this.check = false;
            return;
        }
        this.pc = pc;

        this.helper = new Helper();
        this.check = true;
        this.notReadyStatement = new String("\t*********THIS STATEMENT CURRENTLY NOT WORKING*********");
        this.tempCount = 0;
    }


    public void print() {
        if (this.isParsed) {
            System.out.println();
            for (String codeLine : this.codeLines) {
                System.out.println(codeLine);
            }    
        }
    }
    
    public void parse(List<Statement> statements){
        try {
            if (this.check) {
                System.out.println("CodeGeneration parsing started.");
                
                for (Statement statement : statements) {
                    this.codeLines.addAll(this.parseStatement(statement));
                }
                
                this.codeLines.add("HALT 0,0,0");
                this.isParsed = true;
            } else {
                this.isParsed = false;
            }
        } catch (Exception e) {
            this.isParsed = false;
        }
    }

    private List <String> parseStatement(Statement statement) {     
        List <String> innerCodeLines = new ArrayList<String>();

        if (statement.ST == Statement.READ_ST) {
            innerCodeLines.add(this.parseRead(statement));
        }

        else if (statement.ST == Statement.WRITE_ST) {
            innerCodeLines.add(this.parseWrite(statement));
        }

        else if (statement.ST == Statement.ASSIGN_ST) {
            innerCodeLines.addAll(this.parseAssign(statement));
        }

        else if (statement.ST == Statement.REPEAT_ST) {
            innerCodeLines.addAll(this.parseRepeat(statement));
        }

        else if (statement.ST == Statement.IF_ST) {
            innerCodeLines.addAll(this.parseIf(statement));
        }

        return innerCodeLines;
    }

    private List <String> parseRepeat(Statement statement) {
        RepeatStatement tmpRepeat = (RepeatStatement)statement;
        List <String> codeLines = new ArrayList <String>();
        codeLines.add(this.notReadyStatement + " (REPEAT)");
        return codeLines;
    }

    private List <String> parseIf(Statement statement) {
        IfStatement tmpIf = (IfStatement)statement;
        List <String> codeLines = new ArrayList <String>();
        codeLines.add(this.notReadyStatement + " (IF)");
        return codeLines;
    }


    private List <String> parseAssign(Statement statement) {
        AssignStatement tmpAssign = (AssignStatement)statement;
        List <String> codeLines = new ArrayList <String>();

        if (tmpAssign.assignFrom.size() == 1) {

            if (this.helper.isNum(tmpAssign.assignFrom.get(0))){
                // a := 1
                codeLines.add(this.assignConstant(tmpAssign.assignTo, tmpAssign.assignFrom.get(0)));
            }
            else {
                // a := b
                codeLines.add(this.assignVariable(tmpAssign.assignTo, tmpAssign.assignFrom.get(0)));
            }

        } else if (tmpAssign.assignFrom.size() == 3) {
            
            if (tmpAssign.assignFrom.get(1).equals("+")) {
                codeLines.addAll(this.addExpr(tmpAssign.assignTo, tmpAssign.assignFrom.get(0), tmpAssign.assignFrom.get(2)));
            } else if (tmpAssign.assignFrom.get(1).equals("-")) {
                codeLines.addAll(this.subExpr(tmpAssign.assignTo, tmpAssign.assignFrom.get(0), tmpAssign.assignFrom.get(2)));
            } else if (tmpAssign.assignFrom.get(1).equals("*")) {
                codeLines.addAll(this.mulExpr(tmpAssign.assignTo, tmpAssign.assignFrom.get(0), tmpAssign.assignFrom.get(2)));
            } else if (tmpAssign.assignFrom.get(1).equals("/")) {
                codeLines.addAll(this.divExpr(tmpAssign.assignTo, tmpAssign.assignFrom.get(0), tmpAssign.assignFrom.get(2)));
            } else {
                codeLines.add(this.notReadyStatement);
            }

        } else {
            codeLines.add(this.notReadyStatement);
        }
        return codeLines;
    }

    private String parseWrite(Statement statement) {
        WriteStatement tmpWrite = (WriteStatement)statement;
        return new String("OUT " + Integer.toString(this.variables.get(tmpWrite.expr())) + ",0,0");
    }

    private String parseRead(Statement statement) {
        int reg;
        ReadStatement tmpRead = (ReadStatement)statement;    
        reg = this.put(tmpRead.id());
        return new String("IN " + reg + ",0,0" );
    }

    private String assignVariable(String assignTo, String assignFrom) {
        String codeLine = new String();
        int reg;

        if (!this.variables.containsKey(assignTo)) { reg = this.put(assignTo);}
        else { reg = this.variables.get(assignTo); }

        codeLine = "LDA " + reg + ",0(" + this.variables.get(assignFrom) + ")";
        return codeLine;
    }

    private String assignConstant(String assignTo, String assignFrom) {
        String codeLine = new String();
        int reg;

        if (!this.variables.containsKey(assignTo)) { reg = this.put(assignTo);}
        else { reg = this.variables.get(assignTo); }

        codeLine = "LDC " + reg + "," +  assignFrom + "(0)";
        return codeLine;
    }

    private List <String> divExpr(String assignTo, String assignFrom1, String assignFrom2) {
        List <String> codeLines = new ArrayList <String>();

        // a := 3 / 2
        if (this.helper.isNum(assignFrom1) && this.helper.isNum(assignFrom2)) {
            String assignFrom = new String();
            assignFrom = Integer.toString(Integer.parseInt(assignFrom1) / Integer.parseInt(assignFrom2));
            codeLines.add(this.assignConstant(assignTo, assignFrom));
        }

        // a := b / 1
        else if (this.helper.isID(assignFrom1) && this.helper.isNum(assignFrom2)) {
            String tempRegId = this.createNewTempRegister();
            codeLines.add(this.assignConstant(tempRegId, assignFrom2));
            codeLines.add(this.div(assignTo, assignFrom1, tempRegId));
        }
        
        // a := 1 / b
        else if (this.helper.isNum(assignFrom1) && this.helper.isID(assignFrom2)) {
            String tempRegId = this.createNewTempRegister();
            codeLines.add(this.assignConstant(tempRegId, assignFrom1));
            codeLines.add(this.div(assignTo, assignFrom2, tempRegId));
        }

        // a := b / c
        else if (this.helper.isID(assignFrom1) && this.helper.isID(assignFrom2)) {
            codeLines.add(this.div(assignTo, assignFrom1, assignFrom2));
        }

        else { codeLines.add(this.notReadyStatement); }

        return codeLines;
    }

    private List <String> mulExpr(String assignTo, String assignFrom1, String assignFrom2) {
        List <String> codeLines = new ArrayList <String>();

        
        if (this.helper.isNum(assignFrom1) && this.helper.isNum(assignFrom2)) {
            // a := 3 * 2
            String assignFrom = new String();
            assignFrom = Integer.toString(Integer.parseInt(assignFrom1) * Integer.parseInt(assignFrom2));
            codeLines.add(this.assignConstant(assignTo, assignFrom));
        }

        // a := b * 1
        else if (this.helper.isID(assignFrom1) && this.helper.isNum(assignFrom2)) {
            String tempRegId = this.createNewTempRegister();
            codeLines.add(this.assignConstant(tempRegId, assignFrom2));
            codeLines.add(this.mul(assignTo, assignFrom1, tempRegId));
        }
        
        // a := 1 * b
        else if (this.helper.isNum(assignFrom1) && this.helper.isID(assignFrom2)) {
            String tempRegId = this.createNewTempRegister();
            codeLines.add(this.assignConstant(tempRegId, assignFrom1));
            codeLines.add(this.mul(assignTo, assignFrom2, tempRegId));
        }

        // a := b * c
        else if (this.helper.isID(assignFrom1) && this.helper.isID(assignFrom2)) {
            codeLines.add(this.mul(assignTo, assignFrom1, assignFrom2));
        }

        else { codeLines.add(this.notReadyStatement); }

        return codeLines;
    }

    private List <String> subExpr(String assignTo, String assignFrom1, String assignFrom2) {
        List <String> codeLines = new ArrayList <String>();

        
        if (this.helper.isNum(assignFrom1) && this.helper.isNum(assignFrom2)) {
            // a := 3 - 2
            String assignFrom = new String();
            assignFrom = Integer.toString(Integer.parseInt(assignFrom1) - Integer.parseInt(assignFrom2));
            codeLines.add(this.assignConstant(assignTo, assignFrom));
        }

        // a := b - 1
        else if (this.helper.isID(assignFrom1) && this.helper.isNum(assignFrom2)) {
            String tempRegId = this.createNewTempRegister();
            codeLines.add(this.assignConstant(tempRegId, assignFrom2));
            codeLines.add(this.sub(assignTo, assignFrom1, tempRegId));
        }
        
        // a := 1 - b
        else if (this.helper.isNum(assignFrom1) && this.helper.isID(assignFrom2)) {
            String tempRegId = this.createNewTempRegister();
            codeLines.add(this.assignConstant(tempRegId, assignFrom1));
            codeLines.add(this.sub(assignTo, assignFrom2, tempRegId));
        }

        // a := b - c
        else if (this.helper.isID(assignFrom1) && this.helper.isID(assignFrom2)) {
            codeLines.add(this.sub(assignTo, assignFrom1, assignFrom2));
        }

        else { codeLines.add(this.notReadyStatement); }

        return codeLines;
    }

    private List <String> addExpr(String assignTo, String assignFrom1, String assignFrom2) {
        List <String> codeLines = new ArrayList <String>();

        // a := 1 + 2
        if (this.helper.isNum(assignFrom1) && this.helper.isNum(assignFrom2)) {
            String assignFrom = new String();
            assignFrom = Integer.toString(Integer.parseInt(assignFrom1) + Integer.parseInt(assignFrom2));
            codeLines.add(this.assignConstant(assignTo, assignFrom));
        }

        // a := b + 1
        else if (this.helper.isID(assignFrom1) && this.helper.isNum(assignFrom2)) {
            String tempRegId = this.createNewTempRegister();
            codeLines.add(this.assignConstant(tempRegId, assignFrom2));
            codeLines.add(this.add(assignTo, assignFrom1, tempRegId));
        }
        
        // a := 1 + b
        else if (this.helper.isNum(assignFrom1) && this.helper.isID(assignFrom2)) {
            String tempRegId = this.createNewTempRegister();
            codeLines.add(this.assignConstant(tempRegId, assignFrom1));
            codeLines.add(this.add(assignTo, assignFrom2, tempRegId));
        }

        // a := b + c
        else if (this.helper.isID(assignFrom1) && this.helper.isID(assignFrom2)) {
            codeLines.add(this.add(assignTo, assignFrom1, assignFrom2));
        }

        else { codeLines.add(this.notReadyStatement); }

        return codeLines;
    }

    private String add(String res, String add1, String add2) {
        // a := b + c
        String codeLine = new String();
        int reg;

        if (!this.variables.containsKey(res)) { reg = this.put(res); }
        else { reg = this.variables.get(res); }
        
        codeLine = "ADD " + reg + "," + this.variables.get(add1) + "," + this.variables.get(add2);
        return codeLine;
    }

    private String sub(String res, String sub1, String sub2) {
        // a := b - c
        String codeLine = new String();
        int reg;
        if (!this.variables.containsKey(res)) { reg = this.put(res); }
        else { reg = this.variables.get(res); }
        
        codeLine = "SUB " + reg + "," + this.variables.get(sub1) + "," + this.variables.get(sub2);
        return codeLine;
    }

    private String mul(String res, String mul1, String mul2) {
        // a := b * c
        String codeLine = new String();
        int reg;
        if (!this.variables.containsKey(res)) { reg = this.put(res); }
        else { reg = this.variables.get(res); }
        
        codeLine = "MUL " + reg + "," + this.variables.get(mul1) + "," + this.variables.get(mul2);
        return codeLine;
    }

    private String div(String res, String div1, String div2) {
        // a := b / c
        String codeLine = new String();
        int reg;
        if (!this.variables.containsKey(res)) { reg = this.put(res); }
        else { reg = this.variables.get(res); }
        
        codeLine = "DIV " + reg + "," + this.variables.get(div1) + "," + this.variables.get(div2);
        return codeLine;
    }

    private String createNewTempRegister() {
        String newId = new String();
        do {
            newId = "temp" + Integer.toString(this.tempCount);
            this.tempCount++;
        } while (this.variables.containsKey(newId));
        this.put(newId);
        
        return newId;
    }

    private int put(String id) {
        if (this.variables.size() + 1 == this.pc) {
            this.variables.put("PC", this.variables.size() + 1);
        }
        this.variables.put(id, this.variables.size() + 1);
        return this.variables.size();
    }

    

}
