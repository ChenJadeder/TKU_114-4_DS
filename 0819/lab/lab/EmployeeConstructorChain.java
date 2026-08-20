abstract class EmployeeBase {
    private String id;
    private String name;

    EmployeeBase(String id, String name) {
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
        System.out.println("EmployeeBase constructor: " + this.id);
    }

    String label() {
        return id + " " + name;
    }

    abstract int calculatePay();
}

class FullTimeEmployee extends EmployeeBase {
    private int monthlySalary;

    FullTimeEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        if (monthlySalary < 0) {
            this.monthlySalary = 0;
        } else {
            this.monthlySalary = monthlySalary;
        }
        System.out.println("FullTimeEmployee constructor: " + this.monthlySalary);
    }

    @Override
    int calculatePay() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends EmployeeBase {
    private int hourlyRate;
    private int hours;

    PartTimeEmployee(String id, String name, int hourlyRate, int hours) {
        super(id, name);
        if (hourlyRate < 0) {
            this.hourlyRate = 0;
        } else {
            this.hourlyRate = hourlyRate;
        }
        if (hours < 0) {
            this.hours = 0;
        } else {
            this.hours = hours;
        }
        System.out.println("PartTimeEmployee constructor: rate=" + this.hourlyRate + ", hours=" + this.hours);
    }

    @Override
    int calculatePay() {
        return hourlyRate * hours;
    }
}

public class EmployeeConstructorChain {
    public static void main(String[] args) {
        EmployeeBase a = new FullTimeEmployee("E01", "Amy", 50000);
        System.out.println(a.label() + " pay=" + a.calculatePay());

        EmployeeBase b = new PartTimeEmployee("E02", "Ben", 220, 80);
        System.out.println(b.label() + " pay=" + b.calculatePay());
    }
}