package dcatano.micrometer_demo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class PricingEngine {
    private final Random random = new Random();

    @Getter
    private Double price;

    @Scheduled(fixedRate = 60 * 1000L)
    public void computePrice() {
        price = random.nextDouble() * 100;
        log.info("Price: {}", price);
    }

}
