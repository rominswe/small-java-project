import java.util.Scanner;

// Stack implementation using Singly Linked List
class Stack {
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;

    public Stack() {
        top = null;
    }

    public void push(int data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
    }

    public int pop() {
        if (top == null) {
            throw new RuntimeException("Stack Underflow");
        }
        int data = top.data;
        top = top.next;
        return data;
    }

    public int peek() {
        if (top != null) {
            return top.data;
        }
        throw new RuntimeException("Stack is Empty");
    }

    public boolean isEmpty() {
        return top == null;
    }
}

// Queue implementation using Singly Linked List
class Queue {
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front, rear;

    public Queue() {
        front = rear = null;
    }

    public void enqueue(int data) {
        Node newNode = new Node(data);
        if (rear == null) {
            front = rear = newNode;
            return;
        }
        rear.next = newNode;
        rear = newNode;
    }

    public int dequeue() {
        if (front == null) {
            throw new RuntimeException("Queue Underflow");
        }
        int data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return data;
    }

    public int peek() {
        if (front != null) {
            return front.data;
        }
        throw new RuntimeException("Queue is Empty");
    }

    public boolean isEmpty() {
        return front == null;
    }
}

public class InfixToPostfix {

    // Method to get precedence of operators
    private static int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    // Method to check if a character is an operator
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Method to preprocess the infix expression for implicit multiplication
    private static String preprocessInfix(String expression) {
        StringBuilder processed = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char current = expression.charAt(i);
            processed.append(current);

            if (i < expression.length() - 1 && Character.isDigit(current) && expression.charAt(i + 1) == '(') {
                processed.append('*');
            }

            if (i < expression.length() - 1 && current == ')' && Character.isDigit(expression.charAt(i + 1))) {
                processed.append('*');
            }
        }

        return processed.toString();
    }

    // Method to validate the infix expression
    private static boolean isValidExpression(String expression) {
        int openParenCount = 0;
        boolean lastCharWasOperator = false;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (!Character.isDigit(c) && !isOperator(c) && c != '(' && c != ')' && !Character.isLetter(c)) {
                return false;
            }

            if (isOperator(c)) {
                if (lastCharWasOperator || i == 0 || i == expression.length() - 1) {
                    return false;
                }
                lastCharWasOperator = true;
            } else {
                lastCharWasOperator = false;
            }

            if (c == '(') {
                openParenCount++;
            }
            if (c == ')') {
                openParenCount--;
                if (openParenCount < 0) {
                    return false;
                }
            }
        }
        return openParenCount == 0;
    }

    // Method to convert infix expression to postfix expression using Stack and Queue
    public static String infixToPostfix(String expression) {
        Queue queue = new Queue();
        Stack stack = new Stack();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                while (i < expression.length() && Character.isLetterOrDigit(expression.charAt(i))) {
                    result.append(expression.charAt(i));
                    i++;
                }
                result.append(" ");
                i--;
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    queue.enqueue(stack.pop());
                    result.append((char) queue.dequeue()).append(" ");
                }
                stack.pop();
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence((char) stack.peek())) {
                    queue.enqueue(stack.pop());
                    result.append((char) queue.dequeue()).append(" ");
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            queue.enqueue(stack.pop());
            result.append((char) queue.dequeue()).append(" ");
        }

        return result.toString().trim();
    }

    // Method to evaluate the postfix expression
    public static int evaluatePostfix(String postfix) {
        Stack stack = new Stack();
        String[] tokens = postfix.split(" ");

        for (String token : tokens) {
            if (token.matches("\\d+")) {
                stack.push(Integer.parseInt(token));
            } else if (isOperator(token.charAt(0))) {
                int b = stack.pop();
                int a = stack.pop();
                int result = 0;
                switch (token.charAt(0)) {
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '*':
                        result = a * b;
                        break;
                    case '/':
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        result = a / b;
                        break;
                    case '^':
                        result = (int) Math.pow(a, b);
                        break;
                }
                stack.push(result);
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an infix expression: ");
        String expression = scanner.nextLine().replaceAll(" ", "");

        System.out.println("Time Complexity for preprocessing: O(n)");
        String preprocessedExpression = preprocessInfix(expression);

        System.out.println("Time Complexity for validation: O(n)");
        if (!isValidExpression(preprocessedExpression)) {
            System.out.println("Invalid expression.");
            return;
        }

        System.out.println("Time Complexity for infix-to-postfix conversion: O(n)");
        String postfix = infixToPostfix(preprocessedExpression);
        System.out.println("Postfix Expression: " + postfix);

        System.out.println("Time Complexity for postfix evaluation: O(n)");
        try {
            int result = evaluatePostfix(postfix);
            System.out.println("Evaluation Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
