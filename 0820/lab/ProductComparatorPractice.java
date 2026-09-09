// 課堂實作題五：多規則商品排序
//指定檔名：ProductComparatorPractice.java

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class StoreProduct implements Comparable<StoreProduct> {
    private String id;
    private String name;
    private int price;
    private int stock;

    StoreProduct(String id, String name, int price, int stock) {
        if (id == null || id.trim().length() == 0) {
            this.id = "UNKNOWN";
        } else {
            this.id = id.trim();
        }

        if (name == null || name.trim().length() == 0) {
            this.name = "Unknown";
        } else {
            this.name = name.trim();
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

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }

    int getStock() {
        return stock;
    }

    // Natural order：商品 id 升冪
    @Override
    public int compareTo(StoreProduct other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return id + " " + name
                + " price=" + price
                + " stock=" + stock;
    }
}

public class ProductComparatorPractice {

    static void printProducts(String label, List<StoreProduct> products) {
        System.out.println(label + ":");
        for (int i = 0; i < products.size(); i++) {
            System.out.println("  " + products.get(i));
        }
    }

    public static void main(String[] args) {
        List<StoreProduct> products = new ArrayList<>();

        // 包含同價、同庫存的測試資料
        products.add(new StoreProduct("P103", "Mouse", 400, 30));
        products.add(new StoreProduct("P101", "Keyboard", 800, 10));
        products.add(new StoreProduct("P102", "USB Hub", 400, 5));      // 與 Mouse 同價
        products.add(new StoreProduct("P105", "Webcam", 1200, 10));    // 與 Keyboard 同庫存
        products.add(new StoreProduct("P104", "Headset", 1200, 2));    // 與 Webcam 同價

        // 原始順序不應被後面排序改變
        printProducts("original", products);

        // 1. Natural order：依 id 升冪
        List<StoreProduct> byId = new ArrayList<>(products);
        Collections.sort(byId);
        printProducts("by id", byId);

        //  依 price 升冪，同價依 name
        List<StoreProduct> byPrice = new ArrayList<>(products);

        Comparator<StoreProduct> priceThenName = new Comparator<StoreProduct>() {
            @Override
            public int compare(StoreProduct first, StoreProduct second) {
                if (first.getPrice() < second.getPrice()) {
                    return -1;
                }

                if (first.getPrice() > second.getPrice()) {
                    return 1;
                }

                return first.getName().compareTo(second.getName());
            }
        };

        Collections.sort(byPrice, priceThenName);
        printProducts("by price then name", byPrice);

        // 依 stock 降冪，依 id
        List<StoreProduct> byStock = new ArrayList<>(products);

        Comparator<StoreProduct> stockDescThenId = new Comparator<StoreProduct>() {
            @Override
            public int compare(StoreProduct first, StoreProduct second) {
                if (first.getStock() > second.getStock()) {
                    return -1;
                }

                if (first.getStock() < second.getStock()) {
                    return 1;
                }

                return first.getId().compareTo(second.getId());
            }
        };

        Collections.sort(byStock, stockDescThenId);
        printProducts("by stock desc then id", byStock);

        // 再次印原始清單，證明排序都操作 copy
        printProducts("original still same", products);
    }
}