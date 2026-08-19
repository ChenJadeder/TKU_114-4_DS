//建立子類物件時，一定先呼叫父類 constructor，再執行子類 constructor 。
abstract class Worker {
    private final String id;
    private final String name;

    Worker(String id, String name) {
        this.id = id;
        this.name = name;
        System.out.println("Worker constructor: " + id);
    }

    String label() {
        return id + " " + name;
    }

    abstract int monthlyPay();
}

class SalariedWorker extends Worker {
    private final int salary;

    SalariedWorker(String id, String name, int salary) {
        super(id, name);
        this.salary = Math.max(0, salary);
        System.out.println("SalariedWorker constructor: " + salary);
    }

    @Override
    int monthlyPay() {
        return salary;
    }
}

class HourlyWorker extends Worker {
    private final int hourlyRate;
    private final int hours;

    HourlyWorker(String id, String name, int hourlyRate, int hours) {
        super(id, name); // 必須先呼叫父類別 constructor
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hours = Math.max(0, hours);
        System.out.println("HourlyWorker constructor: rate=" + this.hourlyRate + ", hours=" + this.hours);
    }

    @Override
    int monthlyPay() {
        return hourlyRate * hours;
    }
}

public class ConstructorChainDemo {
    public static void main(String[] args) {
        Worker w1 = new SalariedWorker("E01", "Amy", 50000);
        System.out.println(w1.label() + " pay=" + w1.monthlyPay());

        Worker w2 = new HourlyWorker("E02", "Ben", 220, 80);
        System.out.println(w2.label() + " pay=" + w2.monthlyPay());
    }
}
