package backend.academy.random;

import java.security.SecureRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRandomGenerator implements RandomGeneratorInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRandomGenerator.class);
    private final SecureRandom random;

    public DefaultRandomGenerator() {
        this.random = new SecureRandom();
        LOGGER.info("RandomGeneratorInterface initialized");
    }

    @Override
    public int nextInt(int bound) {
        int result = random.nextInt(bound);
        LOGGER.debug("Generated random int: {} (bound: {})", result, bound);
        return result;
    }
}
