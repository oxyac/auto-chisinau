package oxyac.shopping.data.seeders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import oxyac.shopping.config.AppConfig;
import oxyac.shopping.data.AsyncThread;
import oxyac.shopping.data.parsers.AbstractCarParser;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class CarSeeder {

    private final AppConfig appConfig;

    private final List<AbstractCarParser> carParsers;

    private final AsyncThread asyncThread;

    public CarSeeder(AppConfig appConfig, List<AbstractCarParser> carParsers, AsyncThread asyncThread) {
        this.appConfig = appConfig;
        this.carParsers = carParsers;
        this.asyncThread = asyncThread;
    }

    @Scheduled(fixedDelay = 15, timeUnit = TimeUnit.MINUTES)
    public void seedCars() {
        carParsers.forEach(asyncThread::startParsing);
    }
}
