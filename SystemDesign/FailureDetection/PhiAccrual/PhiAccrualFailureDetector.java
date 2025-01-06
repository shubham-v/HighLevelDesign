package SystemDesign.FailureDetection.PhiAccrual;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * # Basic Idea
 *      The algorithm continuously monitors the intervals between heartbeat messages from a node in a distributed system. Based on the observed intervals, it calculates the probability that the node has failed, expressed as a "Phi" value (𝜙).
 * # KeyConcepts
 *      Heartbeat Intervals: The time intervals between consecutive heartbeats from a node.
 *      Exponential Distribution: The algorithm assumes that the heartbeat intervals follow an exponential distribution.
 * # Phi Value (𝜙)
 *      𝜙 = -log10 (1 - P(t)), where P(t) is the cumulative probability of observing a heartbeat after time t. where P(t) = 1 − (e ^ −(t−mean)/stddev)
 *      The higher the 𝜙 value, the greater the likelihood of failure.
 */
public class PhiAccrualFailureDetector {
    private final LinkedList<Long> timestamps = new LinkedList<>();
    private final int maxSamples;
    private final double threshold;
    private final long minInterval;
    private final PhiComputer phiComputer;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public PhiAccrualFailureDetector(int maxSamples, double threshold, long minInterval) {
        this.maxSamples = maxSamples;  // Maximum number of samples to keep
        this.threshold = threshold;   // Phi threshold for declaring failure
        this.minInterval = minInterval; // Minimum interval to avoid very small heartbeats
        this.phiComputer = new PhiComputer(threshold);
    }

    /**
     * Records the timestamp of a received heartbeat.
     */
    public void recordHeartbeat(long timestamp) {
        try {
            lock.writeLock().lock();
            if (!timestamps.isEmpty()) {
                long lastTimestamp = timestamps.getLast();
                long interval = Math.max(timestamp - lastTimestamp, minInterval); // Avoid too-small intervals
                if (interval < minInterval) {
                    return; // Ignore heartbeats that are too close together
                }
            }
            if (timestamps.size() >= maxSamples) {
                timestamps.poll(); // Remove oldest timestamp
            }
            timestamps.add(timestamp);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Computes the Phi value based on the current timestamps.
     */
    public double computePhi(long currentTime) {
        LinkedList<Long> timestamps = null;
        try {
            lock.readLock().lock();
            timestamps = (LinkedList<Long>) this.timestamps.clone();
        } finally {
            lock.readLock().unlock();
        }

        return new PhiComputer(threshold).compute(timestamps, currentTime);
    }

    /**
     * Determines if a node has failed based on the Phi threshold.
     */
    public boolean isNodeFailed(long currentTime) {
        double phi = computePhi(currentTime);
        return phi >= threshold;
    }

    public static void main(String[] args) throws InterruptedException {
        PhiAccrualFailureDetector detector = new PhiAccrualFailureDetector(100, 8.0, 500);

        // Simulate heartbeats
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            if ((rand.nextInt() & 1) == 0)
                continue;
            detector.recordHeartbeat(System.currentTimeMillis());
            Thread.sleep(1000); // 1-second intervals
        }

        // Check for failure
        long currentTime = System.currentTimeMillis();
        double phi = detector.computePhi(currentTime);
        System.out.println("Phi value: " + phi);
        System.out.println("Is node failed? " + detector.isNodeFailed(currentTime));
    }
}