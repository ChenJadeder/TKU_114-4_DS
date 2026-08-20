abstract class Transport {
    private final String routeName;

    Transport(String routeName) {
        if (routeName == null) {
            this.routeName = "UNKNOWN";
        } else {
            String t = routeName.trim();
            this.routeName = t.isEmpty() ? "UNKNOWN" : t;
        }
    }

    String getRouteName() {
        return routeName;
    }

    abstract int calculateFare(int distance);
}

class Bus extends Transport {
    private final int baseFare = 15;
    private final int perKm = 2;

    Bus(String routeName) {
        super(routeName);
    }

    @Override
    int calculateFare(int distance) {
        if (distance <= 0) return 0;
        return baseFare + perKm * distance;
    }
}

class Taxi extends Transport {
    private final int baseFare = 70;
    private final int perKm = 10;

    Taxi(String routeName) {
        super(routeName);
    }

    @Override
    int calculateFare(int distance) {
        if (distance <= 0) return 0;
        return baseFare + perKm * distance;
    }
}

public class TransportFareSystem {
    public static void main(String[] args) {
        Transport[] transports = new Transport[] {
            new Bus("Red Line"),
            new Taxi("55688"),
            new Bus("Airport Shuttle"),
            new Taxi("Uber")
        };
        int[] distances = {5, 10, -3, 12};

        for (int i = 0; i < transports.length; i++) {
            Transport t = transports[i];
            int d = distances[i];
            int fare = t.calculateFare(d);
            System.out.println(t.getClass().getSimpleName() + " " +
                    t.getRouteName() + " distance=" + d + " fare=" + fare);
        }
    }
}