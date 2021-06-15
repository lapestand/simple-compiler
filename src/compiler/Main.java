package compiler;

import compiler.lexical.Lexical;
import compiler.semantic.Semantic;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean SHOW_SYMBOL_TABLE = false, SHOW_SOURCE_CODE = true;


        List <String> lines = new ArrayList<String>();

        Lexical lexicalAnalyser = new Lexical();
        Semantic semanticAnalyser = new Semantic();

        
        
        try {
            Path fileName = Path.of(args[0]);
            
            lines = Files.readAllLines(fileName);
            // System.out.println("File: \"" + fileName + "\"");


            if (SHOW_SOURCE_CODE) {
                System.out.println("\n\n------------------------------\n\n");
                System.out.println("Source code: " + fileName);
                lexicalAnalyser.printSourceCode(lines);
            }



            if (SHOW_SYMBOL_TABLE) {
                System.out.println("\n\n------------------------------\n\n");
                System.out.println("Symbol Table: " + fileName);
                semanticAnalyser.printSemanticTable(lines);        
            }
            
            
            System.out.println("\n\nChecking Types...");

            // Files.writeString(fileName, fileContent);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
            System.out.println("Please specify the file name!");
        } catch (NoSuchFileException e){
            System.out.println("Given file not found!");
        }

        
    }
}