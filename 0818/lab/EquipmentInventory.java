class Equipment {
private String id;
private String name;
private int availableCount;
    Equipment(String id, String name, int availableCount) {
    if (id == null) {
        this.id = "Unknown";
    } else {
        String t = id.trim();
        this.id = t.isEmpty() ? "Unknown" : t;
    }
    if (name == null) {
        this.name = "Unknown";
    } else {
        String t = name.trim();
        this.name = t.isEmpty() ? "Unknown" : t;
    }
    if (availableCount < 0) {
        this.availableCount = 0;
    } else {
        this.availableCount = availableCount;
    }
    }

boolean borrowOne() {
    boolean ok = false;
    if (availableCount > 0) {
        availableCount = availableCount - 1;
        ok = true;
    }
    return ok;
}

void returnItems(int quantity) {
    if (quantity > 0) {
        availableCount = availableCount + quantity;
    }
}

@Override
public String toString() {
    return id + " " + name + " available=" + availableCount;
}
}


public class EquipmentInventory {
public static void main(String[] args) {
Equipment camera = new Equipment("E001", "Camera", 2);
Equipment mic = new Equipment("E002", "Mic", 0);
System.out.println("Borrow camera: " + camera.borrowOne());
    System.out.println("Borrow camera: " + camera.borrowOne());
    System.out.println("Borrow camera: " + camera.borrowOne());

    System.out.println("Borrow mic: " + mic.borrowOne());
    mic.returnItems(3);
    System.out.println("Borrow mic: " + mic.borrowOne());

    System.out.println(camera);
    System.out.println(mic);
    }
}