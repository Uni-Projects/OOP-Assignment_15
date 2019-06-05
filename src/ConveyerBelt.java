
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Sjaak en Pieter
 */
public class ConveyerBelt{

    private final ArrayList<Item> elements;
    private int amount, begin, end, capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition noItems = lock.newCondition();
    private final Condition fullOfItems = lock.newCondition();

    public ConveyerBelt(int size) {
        elements = new ArrayList<>(size);
        capacity = size;
        amount = 0;
        begin = 0;
        end = 0;
    }

    public void putIn(Item item) {
        lock.lock();
        try {
            while (amount == capacity)
                fullOfItems.await();           
            elements.add(item);
            end = (end + 1) % capacity;
            amount = amount + 1;
            noItems.signalAll();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public Item removeFrom() {  // Assumes there is at least one element
        lock.lock();
        Item item = null;
        try {
            while (amount == 0) {
                noItems.await();
            }
            item = elements.get(begin);
            begin = (begin + 1) % capacity;
            amount = amount - 1;
            fullOfItems.signalAll();
            
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        } finally {
            lock.unlock();
        }
        return item;
    }
}
