package compiler.semantic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Semantic {

    private List <String> removeList;

    public Semantic() {
        this.removeList = new ArrayList<>(Arrays.asList(
            "read", "write", "if", "then", "end", "else", "repeat",
            "until", ";", "<", "\\+", "-", "\\*", "/", ":=", "=" ));
    }

    public void parse(){
        System.out.println("Semantic parsing started.");
    }

    public void printSemanticTable(List<String> lines) {
        String line = new String();
        List <String> newLines = new ArrayList <String>();
        List <String> lineContent;
        SymbolTable symbolTable = new SymbolTable();
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);

            for (int j = 0; j < this.removeList.size(); j++) {
                line = line.replaceAll(this.removeList.get(j), "");
            }

            line = line.replaceAll("[0-9]", "");
            line = line.strip();
            lineContent = Arrays.asList(line.split("\\s+"));

            if (!line.isEmpty()) {
                for (String symbol : lineContent) {
                    symbolTable.add(symbol, i + 1);
                }
            }
        }

        symbolTable.printTable();
    }
}
