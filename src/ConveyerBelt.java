
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Paolo Scattolin s1023775
 * @author Johan Urban s1024726
 * @author Sjaak en Pieter
 */
public class ConveyerBelt {

    private final Item[] elements;
    private int amount, begin, end;
    private final Lock lock = new ReentrantLock();
    private final Condition noItems = lock.newCondition();
    private final Condition fullOfItems = lock.newCondition();

    public ConveyerBelt(int size) {
        elements = new Item[size];
        amount = 0;
        begin = 0;
        end = 0;
    }

    public void putIn(Item item) {
        lock.lock();
        try {
            while (amount == elements.length) {
                fullOfItems.await();
            }
            elements[end] = item;
            end = (end + 1) % elements.length;
            amount = amount + 1;
            noItems.signalAll();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }

    }

    public Item removeFrom() { // Assumes there is at least one element
        lock.lock();
        Item item = null;
        try {
            while (amount == 0) {
                noItems.await();
            }
            item = elements[begin];
            begin = (begin + 1) % elements.length;
            amount = amount - 1;
            fullOfItems.signalAll();
        } catch (InterruptedException e) {
            //System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
        return item;

    }
}
