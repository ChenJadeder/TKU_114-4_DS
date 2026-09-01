//課後作業一：急診候診佇列
//指定檔名：EmergencyTriageQueue.java
import java.util.PriorityQueue;
import java.util.Comparator;

public class EmergencyTriageQueue {

    static class Patient {
        String recordId;
        int severity;
        int arrivalOrder;

        Patient(String recordId, int severity, int arrivalOrder) {
            this.recordId = recordId;
            this.severity = severity;
            this.arrivalOrder = arrivalOrder;
        }

        @Override
        public String toString() {
            return recordId + "|" + severity + "|" + arrivalOrder;
        }
    }

    private PriorityQueue<Patient> queue;

    public EmergencyTriageQueue() {
        queue = new PriorityQueue<>(new Comparator<Patient>() {
            @Override
            public int compare(Patient a, Patient b) {
                // 危急程度高的先
                if (a.severity != b.severity) {
                    return Integer.compare(b.severity, a.severity);
                }

                // 看誰比較早到
                if (a.arrivalOrder != b.arrivalOrder) {
                    return Integer.compare(a.arrivalOrder, b.arrivalOrder);
                }

                // 前兩個都一樣時用病歷號決定
                return a.recordId.compareTo(b.recordId);
            }
        });
    }

    public void checkIn(String recordId, int severity, int arrivalOrder) {
        queue.offer(new Patient(recordId, severity, arrivalOrder));
    }

    public Patient peekNext() {
        return queue.peek();
    }

    public Patient callNext() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

    public static void main(String[] args) {
        EmergencyTriageQueue triage = new EmergencyTriageQueue();

        triage.checkIn("P105", 3, 0);
        triage.checkIn("P210", 5, 1);
        triage.checkIn("P120", 5, 2);
        triage.checkIn("P300", 2, 3);
        triage.checkIn("P110", 3, 4);

        System.out.println("people = " + triage.size());
        System.out.println("next = " + triage.peekNext());

        Patient patient;

        while ((patient = triage.callNext()) != null) {
            System.out.println("call = " + patient);
        }

        System.out.println("people = " + triage.size());

        // 再叫一次，測試空Queue
        patient = triage.callNext();

        if (patient == null) {
            System.out.println("queue is empty");
        }
    }
}
