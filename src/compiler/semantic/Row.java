package compiler.semantic;

import java.util.ArrayList;
import java.util.List;

public class Row {
    String varName;
    int location;
    List <Integer> lineNumbers;
    public Row(String vN, int loc){
        this.varName = new String(vN);
        this.location = loc;
        this.lineNumbers = new ArrayList<Integer>();
        this.addNewLine(loc);
    }

    public void addNewLine(int newLine) {
        this.lineNumbers.add(newLine);
    }
}