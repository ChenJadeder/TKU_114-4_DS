import java.util.ArrayList;
import java.util.List;
// Email&SMS validation for Router
public class Q04_NotificationRouter {

    public interface Channel {
        String name();
        boolean supports(String destination);
        String send(String destination, String message);
    }

    public static class EmailChannel implements Channel {
        @Override
        public String name() {
            return "EMAIL";
        }

        @Override
        public boolean supports(String destination) {
            if (destination == null) {
                return false;
            }
            // vaildation
            return destination.contains("@") 
                && !destination.startsWith("@") 
                && !destination.endsWith("@");
        }

        @Override
        public String send(String destination, String message) {
            return name() + " | " + destination + " | " + message;
        }
    }

    public static class SmsChannel implements Channel {
        @Override
        public String name() {
            return "SMS";
        }

        @Override
        public boolean supports(String destination) {
            if (destination == null) {
                return false;
            }
            // check length rule 2
            String digitsOnly = destination.replaceAll("[^0-9]", "");
            return digitsOnly.length() == 10;
        }

        @Override
        public String send(String destination, String message) {
            return name() + " | " + destination + " | " + message;
        }
    }

    public static List<String> route(List<Channel> channels, String destination, String message) {
        List<String> results = new ArrayList<>();
        
        // Null edge cases protect
        if (channels == null || destination == null || message == null) {
            return results;
        }

        for (Channel ch : channels) {
            // edge list null
            if (ch == null) {
                continue;
            }
            if (ch.supports(destination)) {
                results.add(ch.send(destination, message));
            }
        }
        return results;
    }
}