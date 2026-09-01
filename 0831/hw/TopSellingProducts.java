import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
// 課後作業四：Top-K 熱門商品
// 指定檔名：TopSellingProducts.java
public class TopSellingProducts {

    static class Product {
        String id;
        int sales;

        Product(String id, int sales) {
            this.id = id;
            this.sales = sales;
        }

        @Override
        public String toString() {
            return id + "|" + sales;
        }
    }

    public static List<Product> topK(List<Product> products, int k) {
        List<Product> result = new ArrayList<>();

        if (k <= 0) {
            return result;
        }

        // 先合併相同商品 id 的銷量
        HashMap<String, Integer> totalSales = new HashMap<>();

        for (Product product : products) {
            int oldSales = totalSales.getOrDefault(product.id, 0);
            totalSales.put(product.id, oldSales + product.sales);
        }

        // root 放目前 Top-K 裡最容易被淘汰的商品
        PriorityQueue<Product> heap =
                new PriorityQueue<>(new Comparator<Product>() {
                    @Override
                    public int compare(Product a, Product b) {
                        if (a.sales != b.sales) {
                            return Integer.compare(a.sales, b.sales);
                        }

                        // sales 相同時，id 大的比較差，所以放前面
                        return b.id.compareTo(a.id);
                    }
                });

        for (Map.Entry<String, Integer> entry : totalSales.entrySet()) {
            Product product =
                    new Product(entry.getKey(), entry.getValue());

            heap.offer(product);

            if (heap.size() > k) {
                heap.poll();
            }
        }

        // Heap 只負責選出 Top-K，最後再按照題目要求排序
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }

        result.sort(new Comparator<Product>() {
            @Override
            public int compare(Product a, Product b) {
                if (a.sales != b.sales) {
                    return Integer.compare(b.sales, a.sales);
                }

                return a.id.compareTo(b.id);
            }
        });

        return result;
    }

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();

        products.add(new Product("A", 30));
        products.add(new Product("B", 50));
        products.add(new Product("C", 40));
        products.add(new Product("A", 25));
        products.add(new Product("D", 55));
        products.add(new Product("C", 15));
        products.add(new Product("E", 20));

        List<Product> result = topK(products, 3);

        System.out.println("Top 3:");

        for (Product product : result) {
            System.out.println(product);
        }
    }
}
