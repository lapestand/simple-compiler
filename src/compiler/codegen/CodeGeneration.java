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
    }

    public void parse(List<Statement> statements){
        if (this.check) {
            System.out.println("CodeGeneration parsing started.");
            
            for (Statement statement : statements) {
                this.codeLines.addAll(this.parseStatement(statement));
            }
            
            this.codeLines.add("HALT 0,0,0");
        }
    }

    private List <String> parseStatement(Statement statement) {     
        List <String> innerCodeLines = new ArrayList<String>();

        ReadStatement tmpRead;
        WriteStatement tmpWrite;
        AssignStatement tmpAssign;
        IfStatement tmpIf;
        RepeatStatement tmpRepeat;


        if (statement.ST == Statement.READ_ST) {

            tmpRead = (ReadStatement)statement;
            innerCodeLines.add(
                new String(
                    "IN " + Integer.toString(variables.size() + 1) + ",0,0" 
                    )
            );
            this.variables.put(tmpRead.id(), variables.size() + 1);
        }

        else if (statement.ST == Statement.WRITE_ST) {
            tmpWrite = (WriteStatement)statement;
            innerCodeLines.add(
                new String(
                    "OUT " + Integer.toString(this.variables.get(tmpWrite.expr())) + ",0,0"
                    )
            );
        }

        else if (statement.ST == Statement.ASSIGN_ST) {
            tmpAssign = (AssignStatement)statement;
            if (tmpAssign.assignFrom.size() == 1) {
                if (this.helper.isNum(tmpAssign.assignFrom.get(0))){
                    // a := 1
                    if (this.variables.containsKey(tmpAssign.assignTo)) {
                        innerCodeLines.add(
                            new String("LDC " + this.variables.get(tmpAssign.assignTo) + "," + tmpAssign.assignFrom.get(0) + "(0)")
                        );
                    } else {
                        this.variables.put(tmpAssign.assignTo, this.variables.size() + 1);
                        innerCodeLines.add(
                            new String("LDC " + this.variables.get(tmpAssign.assignTo) + "," +  tmpAssign.assignFrom.get(0) + "(0)")
                        );
                    }
                }
                else {
                    // a := b
                    innerCodeLines.add(
                        new String("LDA " + this.variables.get(tmpAssign.assignTo) + ",0(" + this.variables.get(tmpAssign.assignFrom.get(0)) + ")")
                    );
                }
            } else {
                innerCodeLines.add(
                    new String("\t*********THIS ASSIGN CURRENTLY NOT WORKING*********")
                );
                /*
                if (check) {
                    // a := 1 + 2
                } else if () {
                    // a := 1 + b || b + 1
                }
                */
            }
        }

        else if (statement.ST == Statement.REPEAT_ST) {
            tmpRepeat = (RepeatStatement)statement;

            innerCodeLines.add(
                new String("\t*********REPEAT CURRENTLY NOT WORKING*********")
            );
        }

        else if (statement.ST == Statement.IF_ST) {
            tmpIf = (IfStatement)statement;
            innerCodeLines.add(
                new String("\t*********IF CURRENTLY NOT WORKING*********")
            );
        }

        return innerCodeLines;
    }

    public void print() {
        System.out.println();
        for (String codeLine : this.codeLines) {
            System.out.println(codeLine);
        }
    }

}
