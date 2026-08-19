// 修正點：constructor 內使用 this.name = name; 避免 name=name 導致 field 保持為 null
class BrokenProduct {
    private String name;

    BrokenProduct(String name) {
        if (name == null) {
            this.name = "Unknown";
        } else {
            String t = name.trim();
            this.name = t.isEmpty() ? "Unknown" : t;
        }
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}

public class BrokenProductFix {
    public static void main(String[] args) {
        BrokenProduct p1 = new BrokenProduct("Mouse");
        BrokenProduct p2 = new BrokenProduct("   ");

        System.out.println(p1); // name=Mouse
        System.out.println(p2); // name=Unknown
    }
}