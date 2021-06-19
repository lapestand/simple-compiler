package compiler.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import helper.Helper;

public class Statement {
    public List <String> codeLines;
    public int startIdx;
    public int endIdx;
    public Helper helper;
    public int IF_ST = 0, REPEAT_ST = 1, ASSIGN_ST = 2, READ_ST = 3, WRITE_ST = 4;
    public int ST;
    public int errorLine;
    List <String> reservedWords = new ArrayList<>(Arrays.asList( "read", "write", "if", "then", "end", "else", "repeat", "until" ));
    public boolean parse() {
        return false;
    }
    public String errorLine() {
        return null;
    }
    public void print() {
    }
}
