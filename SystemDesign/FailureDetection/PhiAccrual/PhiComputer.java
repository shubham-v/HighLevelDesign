package SystemDesign.FailureDetection.PhiAccrual;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.IntStream;

public class PhiComputer {

    private final double threshold;

    /**
     * Cumulative distribution function (CDF) for the exponential distribution.
     */
    private final Function<Double, Double> cumulativeDistributionFunction = exponent -> {
        double p = 1 - Math.exp(-1 * exponent);
        return Math.min(Math.max(p, 0.00001), 0.99999);
    };


    public PhiComputer(double threshold) {
        this.threshold = threshold;
    }

    public double compute(LinkedList<Long> timestamps, long currentTime) {
        if (timestamps.size() < 2) {
            return 0.0; // Not enough data to compute phi
        }

        long lastHeartbeat = timestamps.getLast();
        long timeSinceLastHeartbeat = currentTime - lastHeartbeat;

        double mean = mean(timestamps);
        double standardDeviation = standardDeviation(timestamps, mean);

        if (standardDeviation == 0.0d) {
            return timeSinceLastHeartbeat > mean ? Double.MAX_VALUE : 0.0;
        }

        double exponent = -(timeSinceLastHeartbeat - mean) / standardDeviation;
        return -Math.log10(1 - cumulativeDistributionFunction.apply(exponent));
    }

    /**
     * Calculates the mean of heartbeat intervals using timestamps.
     */
    private double mean(LinkedList<Long> timestamps) {
        double sum = IntStream.range(1, timestamps.size())
                .mapToDouble(offset -> timestamps.get(offset) - timestamps.get(offset - 1))
                .sum();
        return sum / (timestamps.size() - 1);
    }

    /**
     * Calculates the standard deviation of heartbeat intervals using timestamps.
     */
    private double standardDeviation(LinkedList<Long> timestamps, double mean) {
        double variance = variance(timestamps, mean);
        return Math.sqrt(variance / (timestamps.size() - 1));
    }

    private double variance(LinkedList<Long> timestamps, double mean) {
        return IntStream.range(1, timestamps.size())
                .mapToLong(offset -> timestamps.get(offset) - timestamps.get(offset - 1))
                .mapToDouble(interval -> Math.pow(interval - mean, 2))
                .sum();
    }
}
