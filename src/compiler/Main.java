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
        List <String> lines = new ArrayList<String>();

        Lexical lexicalAnalyser = new Lexical();
        Semantic semanticAnalyser = new Semantic();

        String sourceCode = new String();
        
        try {
            Path fileName = Path.of(args[0]);
            
            lines = Files.readAllLines(fileName);
            System.out.println("File: \"" + fileName + "\"");

            sourceCode = lexicalAnalyser.extractSourceCode(lines);
            

            System.out.println(sourceCode);
            System.out.println("DONE");

            // Files.writeString(fileName, fileContent);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please specify the file name!");
        } catch (NoSuchFileException e){
            System.out.println("Given file not found!");
        }

        
    }
}