package dcatano.micrometer_demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
public class ProductController {
    private final List<Integer> tags = new ArrayList<>();
    private final MeterRegistry meterRegistry;
    private final Random random;
    private final Map<String, Counter> map = new HashMap<>();
    private final Map<String, Timer> timers = new HashMap<>();

    public ProductController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.random = new Random();
        for (int i = 1; i <= 10; i++) {
            tags.add(i);
        }
    }

    @GetMapping("/products")
    public List<Product> getProducts() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        String productId = tags.get(random.nextInt(tags.size() - 1)).toString();
        map.computeIfAbsent(productId, k -> meterRegistry.counter("api_views.product", "product", productId));
        timers.computeIfAbsent(productId, k -> meterRegistry.timer("api_timer.product", "product", productId));
        List<Product> products = new ArrayList<>();
        products.add(new Product("Television"));
        products.add(new Product("Book"));
        long sleepTime = random.nextLong(30) * 1000;
        log.info("El API /products se dormirá por {} milisegundos", sleepTime);
        Thread.sleep(sleepTime);
        map.get(productId).increment();
        timers.get(productId).record(Duration.ofMillis(System.currentTimeMillis() - startTime));
        return products;
    }
}
