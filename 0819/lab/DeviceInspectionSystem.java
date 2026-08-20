abstract class Device {
    private final String model;

    Device(String model) {
        if (model == null) {
            this.model = "UNKNOWN";
        } else {
            String t = model.trim();
            this.model = t.length() == 0 ? "UNKNOWN" : t;
        }
    }

    String model() {
        return model;
    }

    abstract void runDiagnostic();
}

class Laptop extends Device {
    Laptop(String model) {
        super(model);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Laptop " + model() + " OK");
    }
}

class Printer extends Device {
    Printer(String model) {
        super(model);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Printer " + model() + " OK");
    }

    void cleanPrintHead() {
        System.out.println("Printer " + model() + " print head cleaned");
    }
}

class Router extends Device {
    Router(String model) {
        super(model);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Router " + model() + " OK");
    }
}

public class DeviceInspectionSystem {
    public static void main(String[] args) {
        Device[] devices = new Device[4];
        devices[0] = new Laptop("MacBook");
        devices[1] = new Printer("HP-Deskjet");
        devices[2] = new Router("RT-AC88U");
        devices[3] = new Printer("Canon-PIXEL");

        // 多型呼叫診斷
        for (int i = 0; i < devices.length; i++) {
            devices[i].runDiagnostic();
        }

        // 只對 Printer 進行清潔（pattern matching instanceof）
        for (int i = 0; i < devices.length; i++) {
            Device d = devices[i];
            if (d instanceof Printer p) {
                p.cleanPrintHead();
            }
        }
    }
}