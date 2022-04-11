package hw1;

public class ArithmeticCalculatorFunctionality {
    Tree tree;

    static char[] numbersOperators = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '/', '*', 'x', '^', '(', ')', '.','\\'};
    static char[] operators = {'+', '-', '*', 'x', '^','/'};

    public ArithmeticCalculatorFunctionality(String line) {

        line = betterLine(line);

        ///////// checks the expression////////////
        if(checkExpansion(line) == 1)
            return;

        if (checkParenthesis(line) == 1)
            return;

        if (checkChar(line) == 1)
            return;

        if (checkConsecutiveOperator(line) == 1)
            return;

        if(checkOpeningParenthesis(line) == 1)
            return;

        if(checkClosingParenthesis(line) == 1)
            return;

        //////////////////////////////////////////

        line = stringExpansion(line);

        tree = new Tree(line);
    }

    //Make the expression without spaces
    private String betterLine(String line) {

        line = line.replaceAll(" ", "");
        line = line.replaceAll("\t", "");

        return (line);
    }

    //Implement the expansion
    private String stringExpansion(String line) {
        StringBuilder expansion = new StringBuilder();
        StringBuilder OperatorExpansion = new StringBuilder();
        char operator;
        int number, start, last, parenthesis = 0, k = 0;

        //Find where exists the character \
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '\\'){
                //OperatorExpansion contains the special extension operator
                OperatorExpansion.append(line.charAt(i));
                OperatorExpansion.append(line.charAt(i + 1));
                OperatorExpansion.append(line.charAt(i + 2));
                operator = line.charAt(i + 1);
                number = line.charAt(i + 2) - '0';
                last = i - 1;

                //If the extension is inside off parenthesis or not
                //to find from where starts the expansion
                if(line.charAt(last) != ')'){
                    do{
                        i--;
                    }while(!findOperator(line.charAt(i)));

                    line = line.substring(0, i +1) + '(' + line.substring(i +1);
                    start = i + 2;
                }
                else {
                    do {
                        i--;

                        if (line.charAt(i) == ')')
                            parenthesis++;

                        if (line.charAt(i) == '(')
                            parenthesis--;

                    } while (parenthesis != 0);

                    line = line.substring(0, i) + '(' + line.substring(i);

                    start = i + 1;
                }

                //Make the expansion
                while (k < number - 1){
                    expansion.append(operator);
                    for (i = start; i < last + 2;i++){
                        expansion.append(line.charAt(i));
                    }

                    k++;
                }
                k = 0;
                i = last;
                expansion.append(')');

                //Replace the special extension operator with expansion
                line = line.replace(OperatorExpansion.toString(), expansion.toString());

                expansion = new StringBuilder();
                OperatorExpansion = new StringBuilder();
            }
        }

        return (line);
    }

    //Checks if special extension operator is right
    private int checkExpansion(String line){
        boolean operator = false, number = false, digit = false;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '\\') {

                //If it has the right operator
                if (ArithmeticCalculatorFunctionality.findOperator(line.charAt(i + 1)))
                    operator = true;

                //Number bigger than 1
                if (line.charAt(i + 2) - '0' > 1){
                    number = true;

                    //If number is not decimal
                    for (int j = i + 2; j < line.length() && !ArithmeticCalculatorFunctionality.findOperator(line.charAt(j)); j++) {
                        if (line.charAt(j) == '.'){
                            digit = true;
                            break;
                        }
                    }
                }

                if (!operator || !number || digit) {
                    System.out.println("[ERROR] Invalid expansion expression");
                    return 1;
                }

                operator = false;
                number = false;
                digit = false;
                i = i + 2;
            }
        }

        return 0;
    }

    //Checks parenthesis
    private int checkParenthesis(String line) {
        int parenthesis = 0;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(')
                parenthesis++;

            if (line.charAt(i) == ')')
                parenthesis--;

            if (parenthesis < 0) {
                System.out.println("[ERROR] Closing unopened parenthesis");
                return (1);
            }
        }
        if (parenthesis > 0) {
            System.out.println("[ERROR] Not closing opened parenthesis");
            return (1);
        }
        return (0);
    }

    //Check that all characters is right
    private int checkChar(String line) {
        boolean check = false;

        for (int i = 0; i < line.length(); i++) {
            for (char numbersOperator : numbersOperators) {
                if (line.charAt(i) == numbersOperator) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                System.out.println("[ERROR] Invalid character");
                return (1);
            } else {
                check = false;
            }
        }

        return (0);
    }

    //Checks if it has two consecutive operators
    private int checkConsecutiveOperator(String line) {
        boolean operator = false;

        for (int i = 0; i < line.length() - 1; i++) {
            //Finds the first one
            if(findOperator(line.charAt(i))) {
                operator = true;
            }

            //Checks the next one if it is operator
            if (operator) {
                if (findOperator(line.charAt(i + 1))) {
                    System.out.println("[ERROR] Two consecutive operands");
                    return (1);
                }
            }
            operator = false;
        }

        return 0;
    }

    //Checks if after a parenthesis is an operator
    private int checkOpeningParenthesis(String line) {

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                if(findOperator(line.charAt(i + 1))) {
                    System.out.println("[ERROR] Operand appears after opening parenthesis");
                    return 1;
                }
            }
        }

        return 0;
    }

    //Checks if before a parenthesis is an operator
    private int checkClosingParenthesis(String line) {

        for (int i = 1; i < line.length(); i++) {
            if (line.charAt(i) == ')') {
                if(findOperator(line.charAt(i - 1))) {
                    System.out.println("[ERROR] Operand appears before closing parenthesis");
                    return 1;
                }
            }
        }

        return 0;
    }

    //Returns true if that character is a operator else returns false
    static boolean findOperator(char c){
        for (char operator : operators) {
            if (c == operator) {
                return true;
            }
        }
        return false;
    }
}