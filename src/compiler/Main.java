package compiler;

import compiler.lexical.Lexical;
import compiler.semantic.Semantic;

public class Main {
    public static void main(String[] args) {
        Lexical lexicalAnalyser = new Lexical();
        Semantic semanticAnalyser = new Semantic();

        lexicalAnalyser.test();
        semanticAnalyser.test();
    }
}