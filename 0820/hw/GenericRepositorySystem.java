import java.util.ArrayList;
import java.util.List;

//課後作業一：Generic Repository
class Repository<T> {
    private List<T> items;

    Repository() {
        items = new ArrayList<>();
    }

    boolean add(T value) {
        return items.add(value);
    }

    T get(int index) {
        if (index < 0 || index >= items.size()) return null;
        return items.get(index);
    }

    boolean remove(int index) {
        if (index < 0 || index >= items.size()) return false;
        items.remove(index);
        return true;
    }

    int size() {
        return items.size();
    }

    @Override
    public String toString() {
        String s = "[";
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) s += ", ";
            s += items.get(i);
        }
        s += "]";
        return s;
    }
}

// 測試泛行
class Product {
    private String id;
    private String name;
    private int price;

    Product(String id, String name, int price) {
        this.id = (id == null) ? "UNKNOWN" : id.trim();
        this.name = (name == null) ? "Unknown" : name.trim();
        this.price = price < 0 ? 0 : price;
    }

    @Override
    public String toString() {
        return id + " " + name + " $" + price;
    }
}

public class GenericRepositorySystem {
    public static void main(String[] args) {
        Repository<String> texts = new Repository<>();
        texts.add("Java");
        texts.add("OOP");
        System.out.println("texts=" + texts + ", size=" + texts.size());
        System.out.println("get(0)=" + texts.get(0));
        System.out.println("remove(1)=" + texts.remove(1));
        System.out.println("texts(after)=" + texts);

        Repository<Product> products = new Repository<>();
        products.add(new Product("P101", "Keyboard", 800));
        products.add(new Product("P102", "Mouse", 400));
        System.out.println("products=" + products + ", size=" + products.size());
        System.out.println("get(1)=" + products.get(1));
        System.out.println("remove(5)=" + products.remove(5)); // 無效
    }
}