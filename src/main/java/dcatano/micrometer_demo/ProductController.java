package dcatano.micrometer_demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class ProductController {
    private final List<Integer> tags = new ArrayList<>();
    private final MeterRegistry meterRegistry;
    private final Random random;
    private final Map<String, Counter> map = new HashMap<>();

    public ProductController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.random = new Random();
        for (int i = 1; i <= 10; i++) {
            tags.add(i);
        }
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        String productId = tags.get(random.nextInt(tags.size() - 1)).toString();
        map.computeIfAbsent(productId, k -> map.put(k, meterRegistry.counter("api_views.product", "product", productId)));
        List<Product> products = new ArrayList<>();
        products.add(new Product("Television"));
        products.add(new Product("Book"));
        map.get(productId).increment();
        return products;
    }
}
