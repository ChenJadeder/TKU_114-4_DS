import java.util.ArrayList;
import java.util.List;

public class RawTypeSafetyDemo { //Generic?
     // javac -Xlint:unchecked
    static void rawTypeExample() {
        List<Object> onames = new ArrayList<>(); //  raw list fixed to  List<String
        names .add("Amy");
        names .add("Ben"); // risk to runtime

       // names.add(100); // (no suitable method add(int)）

        // compiler : String
        String Upper = names.get(0).toUpperCase();
        System.out.println(names);
        System.out.println("firstUpper=" + Upper);
        }
    }

    static void genericExample() {
        List<Object> onames= new ArrayList<>();
        onames.add("Amy");
        onames.add(100); 
        Object second = onames.get(1);
        System.out.println("name="+ onames +",second =" + second);
    }

    public static void main(String[] args) {
        rawTypeExample();
        genericExample();
    }
}
