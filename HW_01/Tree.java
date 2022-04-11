package hw1;

public class Tree {
    Node root;

    public Tree(String line) {
        root = null;

        root = createTree(line, 0, line.length(), root);
    }

    //Creates the tree
    private Node createTree(String line, int from, int to, Node root) {
        StringBuilder data = new StringBuilder();
        int position;

        Node node = new Node(data.toString());

        //Checks if inside of the string(from, to) exists at least one operator
        //That means that is not a leaf
        if (findAtLeastOneOperator(line, from, to)) {
            //Save the operator and the position
            data.append(line.charAt(findLastOperator(line, from, to)));
            position = findLastOperator(line, from, to);
            node.data = data.toString();

            //Recursion for the left and right sub-tree
            node.left = createTree(line, from, position - 1, node);
            node.right = createTree(line, position + 1, to, node);

        } else {
            //Save only the number and not the parenthesis
            if (from != to) {
                for (int i = from; i <= to && i < line.length(); i++) {
                    if (line.charAt(i) != '(' && line.charAt(i) != ')') {
                        data.append(line.charAt(i));
                    }
                }
            } else {
                data.append(line.charAt(from));
            }
            //Save the number
            node.data = data.toString();
        }

        return (node);
    }

    public String toDotString(){
        StringBuilder preLine = new StringBuilder();

        preOrder(root, preLine);

        return (preLine.toString());
    }

    public String ToString(){
        StringBuilder postLine = new StringBuilder();

        postOrder(root, postLine);

        postLine.deleteCharAt(postLine.length() - 1);
        postLine.deleteCharAt(0);


        return(postLine.toString());
    }

    public double calculate(){
        double result = 0;

        result = calculateTree(root);

        return (result);
    }

    //Calculate the tree
    private double calculateTree(Node root){
        double result = 0, Lnumber = 0, Rnumber = 0;

        if (root == null)
            return result;

        Lnumber = calculateTree(root.left);
        Rnumber = calculateTree(root.right);

        //if is a number or an operator
        if(!findAtLeastOneOperator(root.data, 0, root.data.length())){
            result = Double.parseDouble(root.data);
        }
        else {
            switch(root.data) {
                case "-":
                    result = Lnumber - Rnumber;
                    break;
                case "+":
                    result = Lnumber + Rnumber;
                    break;
                case "x":
                case "*":
                    result = Lnumber * Rnumber;
                    break;
                case "/":
                    result = Lnumber / Rnumber;
                    break;
                case "^":
                    result = Math.pow(Lnumber, Rnumber);
                    break;
            }
        }

        return (result);
    }

    //Finds the last operator that will execute
    private int findLastOperator(String line, int from, int to) {
        int position = 0, parenthesis = 0, last = 100000;

        for (int i = from; i < to; i++) {
            if (line.charAt(i) == '(')
                parenthesis = parenthesis + 10;

            if (line.charAt(i) == ')')
                parenthesis = parenthesis - 10;

            if (ArithmeticCalculatorFunctionality.findOperator(line.charAt(i))) {
                if (operatorLevel(line.charAt(i)) + parenthesis <= last) {
                    last = operatorLevel(line.charAt(i)) + parenthesis;
                    position = i;
                }
            }
        }

        return (position);
    }

    //classify how strong the operator is
    private int operatorLevel(char operator) {
        int level = 0;

        if (operator == '-' || operator == '+')
            level = 1;

        if (operator == '/' || operator == '*' || operator == 'x')
            level = 2;

        if (operator == '^')
            level = 3;

        return level;
    }

    //Returns true if inside this string(from, to) has at least one operator, else returns false
    private boolean findAtLeastOneOperator(String line, int from, int to) {

        for (int i = from; i < to; i++) {
            for (char operator : ArithmeticCalculatorFunctionality.operators) {
                if (line.charAt(i) == operator)
                    return true;
            }
        }

        return false;
    }

    //Execute preorder in the tree and return a string for use in dot program
    private void preOrder(Node node, StringBuilder preLine){

        if(node == null)
            return;

        preLine.append("    ").append(root.hashCode());
        preLine.append(String.format(" [label]=\"%s\"]\n", node.data));

        if(node.left != null) {
            preLine.append(String.format("    %s -- %s\n", node.hashCode(), node.left.hashCode()));
            preOrder(node.left, preLine);
        }
        if(node.right != null) {
            preLine.append(String.format("    %s -- %s\n", node.hashCode(), node.right.hashCode()));
            preOrder(node.left.right, preLine);
        }
    }

    //Execute postorder
   private void postOrder(Node node, StringBuilder postLine) {

        if(node == null)
            return;

        postLine.append("(");

        postOrder(node.left, postLine);
        postOrder(node.right, postLine);

       postLine.append(node.data);
       postLine.append(")");
    }
}

