package dcatano.micrometer_demo;

import io.micrometer.cloudwatch2.CloudWatchConfig;
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;

import java.time.Duration;
import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    public CloudWatchAsyncClient cloudWatchAsyncClient() {
        return CloudWatchAsyncClient.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();
    }

    @Bean
    public MeterRegistry getMeterRegistry() {
        CloudWatchConfig cloudWatchConfig = setUpCloudWatchConfig();
        return new CloudWatchMeterRegistry(
            cloudWatchConfig,
            Clock.SYSTEM,
            cloudWatchAsyncClient()
        );
    }

    private CloudWatchConfig setUpCloudWatchConfig() {
        return new CloudWatchConfig() {
            private final Map<String, String> configuration = Map.of(
                "cloudwatch.namespace", "micrometer-app",
                "cloudwatch.step", Duration.ofMinutes(1).toString()
            );
            @Override
            public String get(@NonNull String s) {
                return configuration.get(s);
            }
        };
    }
}
