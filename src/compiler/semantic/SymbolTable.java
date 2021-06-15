package compiler.semantic;

import java.util.ArrayList;
import java.util.List;

class SymbolTable {
    
    List <Row> rows;
    List <String> varNames;
    public SymbolTable() {
        this.rows = new ArrayList <Row>();
        this.varNames = new ArrayList <String>();
    }

    public void add(String varName, int line) {
        if (this.varNames.contains(varName)) {
            this.rows.get(this.varNames.indexOf(varName)).addNewLine(line);
        }else {
            this.rows.add(new Row(varName, line));
            this.varNames.add(varName);
        }
    }

    public void printTable() {
        System.out.println("Variable name\tLocation\tLine Numbers");
        System.out.println("-------------\t--------\t------------");
        for (Row row : rows) {
            System.out.println(row.varName + "\t\t" + row.location + "\t\t" + row.lineNumbers);
        }
    }
}