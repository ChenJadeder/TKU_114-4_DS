// 概念 1：Inheritance 表達 is-a 關係
/*
 * 建立 Employee superclass，管理員工的 id 與 name。
 * 在 Employee 建構子中初始化 id 與 name。
 * 建立 FullTimeEmployee subclass。
 * 在 FullTimeEmployee 建構子，並呼叫 Employee 初始化父類別。
 * 將 monthlySalary 限制為不小於 0。
 * 在 FullTimeEmployee 中建立 annualSalary()，並以月薪乘以 12 計算年薪。
 * 在 main() 建立 FullTimeEmployee 物件。
 * 透過繼承取得的 label() 顯示員工基本資料。
 * 透過 subclass 自己的 annualSalary() 顯示年薪。
 */

class Employee {
    private String id;
    private String name;


 // Employee 建構子。
     
   
    Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //回傳員工的基本識別資料。
    
    String label() {
        return id + " " + name;
    }
}


class FullTimeEmployee extends Employee {
    private int monthlySalary;

    FullTimeEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    int annualSalary() {
        return monthlySalary * 12;
    }
}


public class InheritanceBasics {
    public static void main(String[] args) {
        // 初始資料：

        FullTimeEmployee employee =
                new FullTimeEmployee("E101", "Amy", 50000);


        System.out.println(employee.label());

        
        // annualSalary() 定義在 FullTimeEmployee，
        
        
        System.out.println("年薪：" + employee.annualSalary());
    }
}
