import com.example.coffeeretail.entity.Category;
import com.example.coffeeretail.entity.Product;
import com.example.coffeeretail.entity.Order;
import com.example.coffeeretail.entity.OrderDetail;
import com.example.coffeeretail.service.CategoryService;
import com.example.coffeeretail.service.ProductService;
import com.example.coffeeretail.service.OrderService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            CategoryService catSvc = new CategoryService();
            ProductService prodSvc = new ProductService();
            OrderService orderSvc = new OrderService();

            // 1) Tạo category nếu chưa có
            if (catSvc.get("C01") == null)
                catSvc.create("C01", "Thức uống", "Các loại đồ uống");

            if (catSvc.get("C02") == null)
                catSvc.create("C02", "Bánh ngọt", "Các loại bánh ngọt");

            // 2) Tạo product nếu chưa có
            if (prodSvc.get("P01") == null)
                prodSvc.create("P01", "Cà phê sữa", 30000.0, "C01");

            if (prodSvc.get("P02") == null)
                prodSvc.create("P02", "Bánh su kem", 20000.0, "C02");

            // 3) In danh sách sản phẩm
            System.out.println("=== Danh sach san pham ===");
            List<Product> allP = prodSvc.list();
            for (Product p : allP) {
                System.out.printf("%s:\t%s (%.0f) – Category=%s%n",
                        p.getId(), p.getName(), p.getPrice(), p.getCategory().getName());
            }

            // 4) Tạo order mẫu
            System.out.println("\n=== Tạo Order mau ===");
            Product p1 = prodSvc.get("P01");
            Product p2 = prodSvc.get("P02");

            Order o = new Order();
            o.setId("O" + System.currentTimeMillis());
            o.setDate(LocalDateTime.now());

            OrderDetail d1 = new OrderDetail();
            d1.setProduct(p1);
            d1.setProductId(p1.getId());
            d1.setQuantity(2);
            d1.setSubtotal(2 * p1.getPrice());

            OrderDetail d2 = new OrderDetail();
            d2.setProduct(p2);
            d2.setProductId(p2.getId());
            d2.setQuantity(1);
            d2.setSubtotal(1 * p2.getPrice());

            double total = d1.getSubtotal() + d2.getSubtotal();
            o.setTotalPrice(total);

            Order created = orderSvc.create(o, Arrays.asList(d1, d2));
            System.out.printf("Order mới ID=%s, total=%.0f%n%n", created.getId(), created.getTotalPrice());

            // 5) Liệt kê order
            System.out.println("=== Danh sach Orders ===");
            for (Order ord : orderSvc.list()) {
                System.out.printf("Order %s – %s – Total=%.0f%n",
                        ord.getId(), ord.getDate(), ord.getTotalPrice());
                ord.getDetails().forEach(dd ->
                        System.out.printf("   • %s x%d = %.0f%n",
                                dd.getProduct().getName(), dd.getQuantity(), dd.getSubtotal()));
            }

        } catch (Exception e) {
            System.err.println("Loi trong qua trinh chay thu:");
            e.printStackTrace();
        }
    }
}
