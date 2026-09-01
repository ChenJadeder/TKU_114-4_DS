// 課後作業一：圖書索引
// 指定檔名：`BookIsbnHashTable.java`
import java.util.ArrayList;

public class BookIsbnHashTable {

    static class Entry {
        String isbn;
        String title;

        Entry(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }

        @Override
        public String toString() {
            return isbn + "=" + title;
        }
    }

    private ArrayList<Entry>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public BookIsbnHashTable(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount must be > 0");
        }

        buckets = (ArrayList<Entry>[]) new ArrayList[bucketCount];

        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }

        size = 0;
    }

    private int getBucketIndex(String isbn) {
        return Math.floorMod(isbn.hashCode(), buckets.length);
    }

    public void put(String isbn, String title) {
        int index = getBucketIndex(isbn);

        // ISBN 已存在時只更新書名
        for (Entry entry : buckets[index]) {
            if (entry.isbn.equals(isbn)) {
                entry.title = title;
                return;
            }
        }

        buckets[index].add(new Entry(isbn, title));
        size++;
    }

    public String get(String isbn) {
        int index = getBucketIndex(isbn);

        for (Entry entry : buckets[index]) {
            if (entry.isbn.equals(isbn)) {
                return entry.title;
            }
        }

        return null;
    }

    public boolean remove(String isbn) {
        int index = getBucketIndex(isbn);

        for (int i = 0; i < buckets[index].size(); i++) {
            Entry entry = buckets[index].get(i);

            if (entry.isbn.equals(isbn)) {
                buckets[index].remove(i);
                size--;
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    public double loadFactor() {
        return (double) size / buckets.length;
    }

    public void bucketReport() {
        for (int i = 0; i < buckets.length; i++) {
            System.out.println("bucket " + i + ": " + buckets[i]);
        }
    }

    public static void main(String[] args) {
        BookIsbnHashTable books = new BookIsbnHashTable(5);

        books.put("978-001", "Java Basics");
        books.put("978-002", "Data Structures");
        books.put("978-003", "Algorithms");
        books.put("978-004", "Computer Networks");

        System.out.println("after insert:");
        System.out.println("size = " + books.size());
        System.out.printf("load factor = %.2f%n", books.loadFactor());

        System.out.println("search 978-002 = "
                + books.get("978-002"));

        // 同 ISBN 更新，不增加 size
        books.put("978-002", "Data Structures 2nd Edition");

        System.out.println("after update:");
        System.out.println("search 978-002 = "
                + books.get("978-002"));
        System.out.println("size = " + books.size());

        System.out.println("remove 978-003 = "
                + books.remove("978-003"));

        System.out.println("search 978-003 = "
                + books.get("978-003"));

        System.out.println("remove missing = "
                + books.remove("978-999"));

        System.out.println("size after remove = "
                + books.size());

        System.out.printf("load factor = %.2f%n",
                books.loadFactor());

        System.out.println("bucket report:");
        books.bucketReport();
    }
}
