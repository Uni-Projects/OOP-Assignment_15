
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Paolo Scattolin s1023775
 * @author Johan Urban s1024726
 * @author Sjaak en Pieter
 */
public class Register {

    private final ConveyerBelt bin;
    private final ConveyerBelt belt;
    private static final int CONVEYER_SIZE = 10, BIN_SIZE = 10;
    private final Lock claimed = new ReentrantLock();

    public Register() {
        bin = new ConveyerBelt(BIN_SIZE);
        belt = new ConveyerBelt(CONVEYER_SIZE);
    }

    // Make sure that CONVEYOR_SIZE + BIN_SIZE >= Customer.MAX_ITEMS, otherwise danger of deadlock
    public void putOnBelt(Item article) {
        belt.putIn(article);
    }

    public Item removeFromBelt() {
        return belt.removeFrom();
    }

    public void putInBin(Item article) {
        bin.putIn(article);
    }

    public Item removeFromBin() {
        return bin.removeFrom();
    }

    public void claim() {
        claimed.lock();
    }

    public void free() {
        claimed.unlock();
    }
}
