package compiler;

import compiler.lexical.Lexical;
import compiler.semantic.Semantic;
import compiler.codegen.CodeGeneration;
import helper.Helper;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean SYMBOL_TABLE = true, SOURCE_CODE = true, SYNTAX_TREE = true, TYPE_CHECKING = true, CODE_GEN = true;
        Helper helper = new Helper();
        List <String> lines = new ArrayList<String>();
        Lexical lexicalAnalyser = new Lexical();
        Semantic semanticAnalyser = new Semantic();
        CodeGeneration generator = new CodeGeneration(7);

        try {
            Path fileName = Path.of(args[0]);
            
            lines = Files.readAllLines(fileName);
            // System.out.println("File: \"" + fileName + "\"");


            if (SOURCE_CODE) {
                System.out.println("\n\n------------------------------\n\n");
                System.out.println("Source code: " + fileName);
                lexicalAnalyser.printSourceCode(lines);
            }

            if (SYNTAX_TREE) {
                System.out.println("\n\n------------------------------\n\n");
                System.out.println("Syntax Tree: " + fileName);
                semanticAnalyser.printSyntaxTree(lines);
            }
            
            if (SYMBOL_TABLE) {
                System.out.println("\n\n------------------------------\n\n");
                System.out.println("Symbol Table: " + fileName);
                semanticAnalyser.printSemanticTable(lines);
            }
            
            if (TYPE_CHECKING) {
                System.out.println("\n\nChecking Types...");
                semanticAnalyser.checkTypes();
            }

            if (CODE_GEN) {
                System.out.println();
                generator.parse(semanticAnalyser.statements());
                generator.print();
            }
            
            helper.createOutputFile(fileName.getFileName().toString(), generator.codeLines);

            
            /*
            

            helper.isExpr(helper.parseLine("first + second"));*/

            // Files.writeString(fileName, fileContent);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
            System.out.println("Please specify the file name!");
        } catch (NoSuchFileException e){
            System.out.println("Given file not found!");
        }

        
    }
}