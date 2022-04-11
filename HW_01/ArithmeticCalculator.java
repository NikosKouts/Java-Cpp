package hw1;

public class ArithmeticCalculator {

    public static void main(String[] args) {

        //Scan the expression
        java.util.Scanner scanner  = new java.util.Scanner(System.in);
        System.out.println("Expression: ");

        String line = scanner.nextLine();
        ArithmeticCalculatorFunctionality calculator = new ArithmeticCalculatorFunctionality(line);

        //Scan menu
        String menu = scanner.nextLine();

        //Keeps only the useful information
        menu = menu.replaceAll(" ", "");
        menu = menu.replaceAll("-", "");
        menu = menu.replaceAll("\t", "");

        for(int i = 0; i < menu.length(); i++){
            if (menu.charAt(i) == 'd')
                System.out.println(calculator.tree.toDotString());

            if (menu.charAt(i) == 's')
                System.out.println("Postfix: " + calculator.tree.ToString());

            if (menu.charAt(i) == 'c')
                System.out.println("Result: " + calculator.tree.calculate());

        }
    }
}
