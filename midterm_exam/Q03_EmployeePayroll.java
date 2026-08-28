public class Q03_EmployeePayroll {

    public static abstract class Employee {
        protected final String id;
        protected final String name;

        protected Employee(String id, String name) {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("id cannot be null or blank");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name cannot be null or blank");
            }
            this.id = id.trim();
            this.name = name.trim();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public abstract int monthlyPay();

        public String summary() {
            return id + " | " + name + " | " + monthlyPay();
        }
    }

    public static class SalariedEmployee extends Employee {
        private final int salary;

        public SalariedEmployee(String id, String name, int salary) {
            super(id, name);
            this.salary = Math.max(0, salary);
        }

        @Override
        public int monthlyPay() {
            return salary;
        }
    }

    public static class HourlyEmployee extends Employee {
        private final int hours;
        private final int hourlyRate;

        public HourlyEmployee(String id, String name, int hours, int hourlyRate) {
            super(id, name);
            this.hours = Math.max(0, hours);
            this.hourlyRate = Math.max(0, hourlyRate);
        }

        @Override
        public int monthlyPay() {
            int standardHours = Math.min(hours, 160);
            int overtimeHours = Math.max(0, hours - 160);
            int standardPay = standardHours * hourlyRate;
            //原轉型似乎會造成偏差,改為math.round
            int overtimePay = (int) (overtimeHours * hourlyRate * 1.5);

            return standardPay + overtimePay;
        }
    }

    public static int totalPayroll(java.util.List<Employee> employees) {
        // payrollTraceK4
        int payrollTraceK4 = 0;
        if (employees == null) {
            return 0;
        }
        for (Employee e : employees) {
            if (e != null) {
                // polymorphism call
                payrollTraceK4 += e.monthlyPay();
            }
        }
        return payrollTraceK4;
    }
//A14997777
}