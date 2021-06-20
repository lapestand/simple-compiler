package compiler.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import helper.Helper;

public class Statement {
    public List <String> codeLines;
    public int startIdx, endIdx;
    public Helper helper;
    public static int IF_ST = 0;
    public static int REPEAT_ST = 1;
    public static int ASSIGN_ST = 2;
    public static int READ_ST = 3;
    public static int WRITE_ST = 4;
    public int ST, in, errorLine;
    public List <String> reservedWords = new ArrayList<>(Arrays.asList( "read", "write", "if", "then", "end", "else", "repeat", "until" ));
    public List <String> compareOps = new ArrayList<>(Arrays.asList("<", "=" ));

    public List <String> errors;
    public boolean parse() { return false; }
    public String errorLine(){ return Integer.toString(this.errorLine); }
    public void print() { }
    public void checkTypes() { }
    public String id() { return null; }
    public Object expr() { return null; }
}
