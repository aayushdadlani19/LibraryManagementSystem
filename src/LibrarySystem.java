import java.io.*;
import java.util.*;

class Book {
    String id, title, author;
    boolean isIssued;

    Book(String id, String title, String author, boolean isIssued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = isIssued;
    }

    @Override
    public String toString() {
        return id + "," + title + "," + author + "," + isIssued;
    }
}

public class LibrarySystem {

    static String filePath = "data/books.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== LIBRARY SYSTEM ===");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> addBook(sc);
                case 2 -> viewBooks();
                case 3 -> issueBook(sc);
                case 4 -> returnBook(sc);
                case 5 -> searchBook(sc);
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addBook(Scanner sc) {
        try {
            System.out.print("Enter Book ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Title: ");
            String title = sc.nextLine();
            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
            bw.write(id + "," + title + "," + author + ",false");
            bw.newLine();
            bw.close();

            System.out.println("Book Added Successfully!");

        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    static void viewBooks() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            System.out.println("\n--- BOOK LIST ---");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();

        } catch (Exception e) {
            System.out.println("No books found!");
        }
    }

    static void issueBook(Scanner sc) {
        System.out.print("Enter Book ID to issue: ");
        String id = sc.nextLine();

        updateBookStatus(id, true);
    }

    static void returnBook(Scanner sc) {
        System.out.print("Enter Book ID to return: ");
        String id = sc.nextLine();

        updateBookStatus(id, false);
    }

    static void updateBookStatus(String id, boolean issue) {
        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<String> bookList = new ArrayList<>();

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[0].equals(id)) {
                    found = true;
                    arr[3] = String.valueOf(issue);
                    line = String.join(",", arr);
                }
                bookList.add(line);
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (String b : bookList) {
                bw.write(b);
                bw.newLine();
            }
            bw.close();

            if (found)
                System.out.println(issue ? "Book Issued!" : "Book Returned!");
            else
                System.out.println("Book not found!");

        } catch (Exception e) {
            System.out.println("Error updating status!");
        }
    }

    static void searchBook(Scanner sc) {
        try {
            System.out.print("Enter Title to Search: ");
            String key = sc.nextLine().toLowerCase();

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(key)) {
                    System.out.println("Found: " + line);
                    found = true;
                }
            }
            br.close();

            if (!found) System.out.println("No match found!");

        } catch (Exception e) {
            System.out.println("Error searching!");
        }
    }
}
