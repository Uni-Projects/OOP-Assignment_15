
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Sjaak en Pieter
 */
public class Register {

    // Make sure that CONVEYOR_SIZE + BIN_SIZE >= Customer.MAX_ITEMS, otherwise danger of deadlock
    private static final int CONVEYER_SIZE = 10, BIN_SIZE = 10;
    private final ConveyerBelt belt;
    boolean taken;
    List<Item> bin;
    
    public Register (){
        belt = new ConveyerBelt(CONVEYER_SIZE);
        taken = false;
        bin = new ArrayList<>(BIN_SIZE);
    }

    public void putOnBelt(Item article) {
        belt.putIn(article);
    }

    public Item removeFromBelt() {
        return belt.removeFrom();
    }

    public void putInBin(Item article) {
       bin.add(article);
    }

    public Item removeFromBin() {
        return bin.remove(BIN_SIZE);
    }
    
    public synchronized void claim(List<Item> bin) throws InterruptedException {
        taken = true;
        this.bin = bin;
        this.wait();  
    }

    public synchronized void free() {
        taken = false;
        this.notifyAll();
        
    }
}
