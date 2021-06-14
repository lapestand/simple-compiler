package compiler;

import compiler.lexical.Lexical;
import compiler.semantic.Semantic;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        Lexical lexicalAnalyser = new Lexical();
        Semantic semanticAnalyser = new Semantic();

        String sourceCode = new String();
        try {
            Path fileName = Path.of(args[0]);
            String fileContent = Files.readString(fileName);
            System.out.println("File: \"" + fileName + "\"");

            sourceCode = lexicalAnalyser.extractSourceCode(fileContent);

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