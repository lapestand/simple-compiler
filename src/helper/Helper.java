package helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Helper {
    public Helper() { }
    
    public List <String> parseLine(String line) {
        List <String> words = new ArrayList <String>();
        words = Stream.of(line.split("\\s+")).collect(Collectors.toCollection(ArrayList::new));
        words.removeIf(item -> item == null || "".equals(item));
        
        if (words.get(words.size() - 1).contains(";")) {
            words.set(
                words.size() - 1,
                words.get(words.size() - 1).substring(
                    0, words.get(words.size() - 1).length() - 1)
            );
            words.add(";");
        }
        return words;
    }


    public boolean isID(String word) {
        return word.matches("^[A-Za-z]+$");
    }

    public boolean isNum(String word) {
        return word.matches("^[0-9]+$");
    }

    public void printTab(int in) { while (in > 0) { System.out.print("\t"); in = in - 1; } }

    public boolean isExpr(List <String> words) {

        return true;
        
        /* StringBuilder sb = new StringBuilder();
        for (String s : words){
            sb.append(s);
            sb.append(" ");
        }

        String line = sb.toString();
        line = line.substring(0, line.length() - 1);
        System.out.println(line);


        //  simple-expr compop simple-expr | simple-expr

        // expr --> factor {mulop factor} {addop factor {mulop factor}} compop factor {mulop factor} {addop factor {mulop factor}}
        // | factor {mulop factor} {addop factor {mulop factor}}



        // factor = ([A-Za-Z]+|[0-9]+)
        // simple-expr = (factor {mulop factor} {addop factor {mulop factor}})
        // expr = simple-expr compop simple-expr | simple-expr

        String factor = new String("([A-Za-z]+|[0-9]+)");
        String mulOp = new String("(\\*|\\/)");  // '*'' or '/'
        String addOp = new String("(\\+|\\-)");  // '+' or '-'
        String compOp = new String("(\\<|\\=)");  // '<' or '='
        String simpleExpr = new String("(" + factor + "\\s(" + mulOp + "\\s" + factor + ")?\\s(" + addOp + "\\s" + factor + "\\s(" + mulOp + "\\s" + factor + ")?)?)");
        String expr = new String("(" + simpleExpr + " " + compOp + " " + simpleExpr + ")|" + simpleExpr);


        // [A-Za-z0-9]+(?:\\s*[\\/*+\\-<>]\\s*[A-Za-z0-9]+\\s*)*;

        String myPattern = new String(factor + "(?:\\s*[\\/*+\\-<=]\\s*" + factor + "\\s*)*");
        // System.out.println(line.matches(expr));
        

        System.out.println("\n\n");

        /*
        List <List <String>> statements = new ArrayList <List <String>>();
        try {
            if (!(this.isID(words.get(0)) || this.isNum(words.get(0)))) {
                System.out.println("False returned");
                return false;
            } else {
                if (words.contains("<") || words.contains("=")) {
                    try {
                        statements.add(words.subList(0, words.indexOf("<")));
                        statements.add(words.subList(words.indexOf("<"), words.size() - 1));
                        System.out.println("<");
                    } catch (Exception e) {
                        statements.add(words.subList(0, words.indexOf("=")));
                        statements.add(words.subList(words.indexOf("="), words.size() - 1));
                        System.out.println("=");
                        //TODO: handle exception
                    }
                } else {
                    statements.add(words);
                    System.out.println("simple expr");
                    System.out.println(words);
                }
            }
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("unexpected syntax!");
        }*/

        /*
        Stream.of(
                    "first + second",
                    "first * second",
                    "first - second",
                    "first / second",
                    "first",
                    "first + second < third",
                    "third < second * first"
        ).forEach(s -> System.out.println(s.matches(expr)));  

        System.out.println("\n");

        Stream.of(
                    "first + second",
                    "first * second",
                    "first - second",
                    "first / second",
                    "first",
                    "first + second < third",
                    "third < second * first"
        ).forEach(s -> System.out.println(s.matches(myPattern)));
        

        String pt = new String("^(?:[a-z]+|\\d+)(?: (?:[+*=-/]|<(?!\\h*[a-z\\d]+\\h*<)) (?:[a-z]+|\\d+))*$");
        System.out.println("\n");

        Stream.of(
                    "first + second",
                    "first * second",
                    "first - second",
                    "first / second",
                    "first",
                    "first + second < third",
                    "third < second * first"
        ).forEach(s -> System.out.println(s.matches(pt)));
        //  simple-expr


        /**
         ((([A-Za-z]+|[0-9]+) ((\*|\/) ([A-Za-z]+|[0-9]+))? ((\+|\-) ([A-Za-z]+|[0-9]+) ((\*|\/) ([A-Za-z]+|[0-9]+))?)? (\<|\=) (([A-Za-z]+|[0-9]+) ((\*|\/) ([A-Za-z]+|[0-9]+))? ((\+|\-) ([A-Za-z]+|[0-9]+) ((\*|\/) ([A-Za-z]+|[0-9]+))?)?)|(([A-Za-z]+|[0-9]+) ((\*|\/) ([A-Za-z]+|[0-9]+))? ((\+|\-) ([A-Za-z]+|[0-9]+) ((\*|\/) ([A-Za-z]+|[0-9]+))?)? 
          
         */
        /*
        return true;*/
    }

    public void printFactor(String factor) {
        if (this.isID(factor)) {
            System.out.println("Id: " + factor);
        } else {
            System.out.println("Const: " + factor);
        }
    }
}
