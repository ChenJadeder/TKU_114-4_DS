//課後作業五：圖書館藏索引
//指定檔名：LibraryBookBst.java

import java.util.ArrayList;
import java.util.List;
// node 中的 isbn就比方hashcode，會打亂原本正確的搜尋路徑。
class Book {
    final String isbn;
    final String title;
    final String author;
    private boolean available;

    Book(String isbn, String title, String author, boolean available) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    boolean isAvailable() {
        return available;
    }

    void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{isbn='" + isbn
                + "', title='" + title
                + "', author='" + author
                + "', available=" + available + "}";
    }
}

class BookNode {
    Book data;
    BookNode left;
    BookNode right;

    BookNode(Book data) {
        this.data = data;
    }
}

class LibraryBst {
    private BookNode root;

    boolean add(Book book) {
        if (!isValidBook(book)) {
            return false;
        }

        if (root == null) {
            root = new BookNode(book);
            return true;
        }

        BookNode current = root;

        while (true) {
            int comparison = book.isbn.compareTo(current.data.isbn);

            if (comparison == 0) {
                return false;
            }

            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new BookNode(book);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new BookNode(book);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Book find(String isbn) {
        if (!isValidIsbn(isbn)) {
            return null;
        }

        BookNode current = root;

        while (current != null) {
            int comparison = isbn.compareTo(current.data.isbn);

            if (comparison == 0) {
                return current.data;
            }

            current = comparison < 0 ? current.left : current.right;
        }

        return null;
    }

    boolean borrow(String isbn) {
        Book book = find(isbn);

        if (book == null || !book.isAvailable()) {
            return false;
        }

        book.setAvailable(false);
        return true;
    }

    boolean returnBook(String isbn) {
        Book book = find(isbn);

        if (book == null || book.isAvailable()) {
            return false;
        }

        book.setAvailable(true);
        return true;
    }

    boolean remove(String isbn) {
        Book book = find(isbn);

        if (book == null || !book.isAvailable()) {
            return false;
        }

        root = remove(root, isbn);
        return true;
    }

    private BookNode remove(BookNode node, String isbn) {
        int comparison = isbn.compareTo(node.data.isbn);

        if (comparison < 0) {
            node.left = remove(node.left, isbn);
        } else if (comparison > 0) {
            node.right = remove(node.right, isbn);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            BookNode successor = minimumNode(node.right);
            node.data = successor.data;
            node.right = remove(node.right, successor.data.isbn);
        }

        return node;
    }

    private BookNode minimumNode(BookNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    List<Book> rangeQuery(String low, String high) {
        List<Book> result = new ArrayList<>();

        if (!isValidIsbn(low) || !isValidIsbn(high)
                || low.compareTo(high) > 0) {
            return result;
        }

        rangeQuery(root, low, high, result);
        return result;
    }

    private void rangeQuery(BookNode node, String low, String high,
                            List<Book> result) {
        if (node == null) {
            return;
        }

        if (low.compareTo(node.data.isbn) < 0) {
            rangeQuery(node.left, low, high, result);
        }

        if (low.compareTo(node.data.isbn) <= 0
                && node.data.isbn.compareTo(high) <= 0) {
            result.add(node.data);
        }

        if (node.data.isbn.compareTo(high) < 0) {
            rangeQuery(node.right, low, high, result);
        }
    }

    List<Book> inorder() {
        List<Book> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(BookNode node, List<Book> result) {
        if (node == null) {
            return;
        }

        inorder(node.left, result);
        result.add(node.data);
        inorder(node.right, result);
    }

    private boolean isValidBook(Book book) {
        return book != null
                && isValidIsbn(book.isbn)
                && isNotBlank(book.title)
                && isNotBlank(book.author);
    }

    private boolean isValidIsbn(String isbn) {
        return isNotBlank(isbn);
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}

public class LibraryBookBst {
    private static void printBooks(String title, List<Book> books) {
        System.out.println(title);

        for (Book book : books) {
            System.out.println(book);
        }

        System.out.println();
    }

    public static void main(String[] args) {
        LibraryBst library = new LibraryBst();

        System.out.println("add978100="
                + library.add(new Book(
                        "978100", "Java Basics", "Mina", true)));

        System.out.println("add978300="
                + library.add(new Book(
                        "978300", "Data Structures", "Leo", true)));

        System.out.println("add978500="
                + library.add(new Book(
                        "978500", "Algorithms", "Nora", true)));

        System.out.println("add978200="
                + library.add(new Book(
                        "978200", "Database Systems", "Ivy", true)));

        System.out.println("duplicate="
                + library.add(new Book(
                        "978100", "Other Book", "Other", true)));

        System.out.println("blankIsbn="
                + library.add(new Book(
                        " ", "Invalid Book", "Unknown", true)));

        System.out.println("find978200=" + library.find("978200"));
        System.out.println("find999999=" + library.find("999999"));

        System.out.println("borrow978300=" + library.borrow("978300"));
        System.out.println("borrowAgain978300=" + library.borrow("978300"));
        System.out.println("removeBorrowed978300="
                + library.remove("978300"));

        System.out.println("return978300=" + library.returnBook("978300"));
        System.out.println("returnAgain978300="
                + library.returnBook("978300"));

        printBooks("range [978150, 978400]",
                library.rangeQuery("978150", "978400"));

        printBooks("invalid range [978500, 978100]",
                library.rangeQuery("978500", "978100"));

        System.out.println("remove978300=" + library.remove("978300"));
        System.out.println("removeMissing=" + library.remove("999999"));

        printBooks("inorder report", library.inorder());
    }
}
