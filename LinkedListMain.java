import java.util.Scanner;

class StudentNode {
    String name;
    double cgpa;
    StudentNode next;

    // Constructor
    public StudentNode(String name, double cgpa) {
        this.name = name;
        this.cgpa = cgpa;
        this.next = null;
    }
}

class StudentLinkedList {
    private StudentNode head;
    private int size;

    // Constructor
    public StudentLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Add at the beginning
    public void addFirst(String name, double cgpa) {
        StudentNode newNode = new StudentNode(name, cgpa);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Add at the end
    public void addLast(String name, double cgpa) {
        StudentNode newNode = new StudentNode(name, cgpa);
        if (head == null) {
            head = newNode;
        } else {
            StudentNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Insert at a specific index
    public void insertAt(int index, String name, double cgpa) {
        if (index < 0 || index > size) {
            System.out.println("Invalid index.");
            return;
        }
        if (index == 0) {
            addFirst(name, cgpa);
            return;
        }
        StudentNode newNode = new StudentNode(name, cgpa);
        StudentNode current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    // Delete the first node
    public void deleteFirst() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        head = head.next;
        size--;
    }

    // Delete the last node
    public void deleteLast() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        if (head.next == null) {
            head = null;
        } else {
            StudentNode current = head;
            while (current.next.next != null) {
                current = current.next;
            }
            current.next = null;
        }
        size--;
    }

    // Delete a node at a specific index
    public void deleteAt(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Invalid index.");
            return;
        }
        if (index == 0) {
            deleteFirst();
            return;
        }
        StudentNode current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        current.next = current.next.next;
        size--;
    }

    // Search for a student by name
    public int search(String name) {
        StudentNode current = head;
        int index = 0;
        while (current != null) {
            if (current.name.equals(name)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    // Display the list in horizontal format
    public void display() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        StudentNode current = head;
        while (current != null) {
            System.out.print("[" + current.name + ", CGPA: " + current.cgpa + "] ");
            current = current.next;
        }
        System.out.println(); // Move to the next line after displaying all students
    }

    // Get the size of the list
    public int getSize() {
        return size;
    }
}

public class LinkedListMain {
    public static void main(String[] args) {
        StudentLinkedList list = new StudentLinkedList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Linked List Operations ---");
            System.out.println("1. Add First");
            System.out.println("2. Add Last");
            System.out.println("3. Insert At");
            System.out.println("4. Delete First");
            System.out.println("5. Delete Last");
            System.out.println("6. Delete At");
            System.out.println("7. Search");
            System.out.println("8. Display");
            System.out.println("9. Performance Test (Add 1000 students)");
            System.out.println("10. Exit");
            System.out.print("Enter your choice (1-10): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name1 = scanner.nextLine();
                    System.out.print("Enter CGPA: ");
                    double cgpa1 = scanner.nextDouble();
                    list.addFirst(name1, cgpa1);
                    break;

                case 2:
                    System.out.print("Enter name: ");
                    String name2 = scanner.nextLine();
                    System.out.print("Enter CGPA: ");
                    double cgpa2 = scanner.nextDouble();
                    list.addLast(name2, cgpa2);
                    break;

                case 3:
                    System.out.print("Enter index: ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name3 = scanner.nextLine();
                    System.out.print("Enter CGPA: ");
                    double cgpa3 = scanner.nextDouble();
                    list.insertAt(index, name3, cgpa3);
                    break;

                case 4:
                    list.deleteFirst();
                    break;

                case 5:
                    list.deleteLast();
                    break;

                case 6:
                    System.out.print("Enter index to delete: ");
                    int deleteIndex = scanner.nextInt();
                    list.deleteAt(deleteIndex);
                    break;

                case 7:
                    System.out.print("Enter name to search: ");
                    String searchName = scanner.nextLine();
                    int searchResult = list.search(searchName);
                    if (searchResult == -1) {
                        System.out.println("Student not found.");
                    } else {
                        System.out.println("Student found at index " + searchResult);
                    }
                    break;

                case 8:
                    list.display();
                    break;

                case 9:
                    System.out.println("Adding 1000 students...");
                    long startTime = System.currentTimeMillis();
                    for (int i = 0; i < 1000; i++) {
                        list.addLast("Student" + i, 3.0 + (i % 5) * 0.1);
                    }
                    long endTime = System.currentTimeMillis();
                    System.out.println("Added 1000 students in " + (endTime - startTime) + "ms.");
                    System.out.println("List size: " + list.getSize());
                    break;

                case 10:
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
