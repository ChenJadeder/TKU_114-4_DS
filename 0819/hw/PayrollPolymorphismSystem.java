//課後作業一：員工薪資與獎金系統
//指定檔名：`PayrollPolymorphismSystem.java`
// 練習重點：抽象 Employee + 三種計薪方式；多型迴圈計總額與最高薪資


abstract class Employee {
    private String id;
    private String name;

    Employee(String id, String name) {
        if (id == null) {
            this.id = "UNKNOWN";
        } else {
            String t = id.trim();
            this.id = t.length() == 0 ? "UNKNOWN" : t;
        }
        if (name == null) {
            this.name = "Unknown";
        } else {
            String t = name.trim();
            this.name = t.length() == 0 ? "Unknown" : t;
        }
    }

    String label() {
        return id + " " + name;
    }

    abstract int calculatePay();
}

class SalariedEmployee extends Employee {
    private int monthlySalary;

    SalariedEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        if (monthlySalary < 0) {
            this.monthlySalary = 0;
        } else {
            this.monthlySalary = monthlySalary;
        }
    }

    @Override
    int calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {
    private int hours;
    private int hourlyRate;

    HourlyEmployee(String id, String name, int hours, int hourlyRate) {
        super(id, name);
        this.hours = hours < 0 ? 0 : hours;
        this.hourlyRate = hourlyRate < 0 ? 0 : hourlyRate;
    }

    @Override
    int calculatePay() {
        return hours * hourlyRate;
    }
}

class SalesEmployee extends Employee {
    private int baseSalary;
    private int salesAmount;
    private int commissionPercent; // 0~100

    SalesEmployee(String id, String name, int baseSalary, int salesAmount, int commissionPercent) {
        super(id, name);
        this.baseSalary = baseSalary < 0 ? 0 : baseSalary;
        this.salesAmount = salesAmount < 0 ? 0 : salesAmount;
        int p = commissionPercent;
        if (p < 0) p = 0;
        if (p > 100) p = 100;
        this.commissionPercent = p;
    }

    @Override
    int calculatePay() {
        return baseSalary + (salesAmount * commissionPercent / 100);
    }
}

public class PayrollPolymorphismSystem {
    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        Employee[] employees = new Employee[] {
            new SalariedEmployee("E01", "Amy", 50000),
            new HourlyEmployee("E02", "Ben", 80, 220),
            new SalesEmployee("E03", "Cara", 20000, 100000, 10),
            new SalesEmployee("E04", "Dan", -1, 50000, 20)
        };

        int total = 0;
        int maxPay = Integer.MIN_VALUE;
        int maxIndex = -1;

        for (int i = 0; i < employees.length; i++) {
            int pay = employees[i].calculatePay();
            if (DEBUG) {
                System.out.println("debug: obj=" + employees[i].getClass().getSimpleName() + ", pay=" + pay);
            }
            System.out.println(employees[i].label() + " pay=" + pay);
            total += pay;
            if (pay > maxPay) {
                maxPay = pay;
                maxIndex = i;
            }
        }

        System.out.println("total=" + total);
        if (maxIndex >= 0) {
            System.out.println("highest=" + employees[maxIndex].label() + " pay=" + maxPay);
        }
    }
}