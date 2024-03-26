package dcatano.micrometer_demo;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class GaugeScheduled {

    public GaugeScheduled(MeterRegistry meterRegistry, PricingEngine pricingEngine) {
        Gauge
            .builder("product.price", pricingEngine, PricingEngine::getPrice)
            .description("Product price")
            .baseUnit("ms")
            .register(meterRegistry);
    }
}

