package backend.academy.random;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRandomGenerator implements RandomGeneratorInterface {
    private static final Logger logger = LoggerFactory.getLogger(DefaultRandomGenerator.class);
    private final Random random;

    public DefaultRandomGenerator() {
        this.random = new Random();
        logger.info("RandomGeneratorInterface initialized");
    }

    @Override
    public int nextInt(int bound) {
        int result = random.nextInt(bound);
        logger.debug("Generated random int: {} (bound: {})", result, bound);
        return result;
    }
}
