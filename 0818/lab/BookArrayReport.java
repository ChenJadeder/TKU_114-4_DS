class Book {
private final String id;
private final String title;
private final int price;
private final int stock;
    Book(String id, String title, int price, int stock) {
    // id
    if (id == null) {
        this.id = "UNKNOWN";
    } else {
        String t = id.trim();
        this.id = t.length() == 0 ? "UNKNOWN" : t;
    }
    // title
    if (title == null) {
        this.title = "Untitled";
    } else {
        String t = title.trim();
        this.title = t.length() == 0 ? "Untitled" : t;
    }
    if (price < 0) {
        this.price = 0;
    } else {
        this.price = price;
    }
    if (stock < 0) {
        this.stock = 0;
    } else {
        this.stock = stock;
    }
}

int getPrice() {
    return price;
}

int getStock() {
    return stock;
}

@Override
public String toString() {
    return id + " " + title + " price=" + price + " stock=" + stock;
}
}
public class BookArrayReport {
public static void main(String[] args) {
Book[] books = new Book[] {
new Book("B001", "Algorithms", 1200, 5),
new Book("B002", "Data Structures", 950, 2),
new Book("B003", "Operating Systems", 1350, 1),
new Book("B004", "Computer Architecture", 800, 4)
};
    // 1) 輸出所有書籍
    System.out.println("All books:");
    for (Book b : books) {
        System.out.println("  " + b);
    }

    // 2) 計算庫存總價值 price * stock
    int totalValue = 0;
    for (Book b : books) {
        totalValue += b.getPrice() * b.getStock();
    }
    System.out.println("Total inventory value: $" + totalValue);

    // 3) 找出價格最高的書
    Book mostExpensive = books[0];
    for (int i = 1; i < books.length; i++) {
        if (books[i].getPrice() > mostExpensive.getPrice()) {
            mostExpensive = books[i];
        }
    }
    System.out.println("Highest price book: " + mostExpensive);

    // 4) 輸出庫存小於或等於 3 的書
    System.out.println("Low stock (<=3):");
    for (Book b : books) {
        if (b.getStock() <= 3) {
            System.out.println("  " + b);
            }
        }
    }
}
