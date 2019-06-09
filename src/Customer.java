
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @author Paolo Scattolin s1023775
 * @author Johan Urban s1024726
 * @author Sjaak en Pieter
 */
public class Customer implements Callable<Integer> {

    public static int MAX_ITEMS = 20;
    private final Store store;
    private final int customerNumber;
    private final int numberOfArticles;
    private final static Random generator = new Random();
    private List<Item> bin;
    private Register register;

    public Customer(int number, Store store) {
        this.store = store;
        customerNumber = number;
        numberOfArticles = generator.nextInt(MAX_ITEMS) + 1;
    }

    @Override
    public Integer call() {

        bin = store.getArticles(numberOfArticles);
        register = store.getCheckout(generator.nextInt(Store.NUMBER_OF_CHECKOUTS));

        register.claim();

        for (Item item : bin) {
            register.putOnBelt(item);
        }

        register.putOnBelt(null);

        for (Item item : bin) {
            register.removeFromBin();
        }

        register.free();

        System.out.println("Customer " + customerNumber + " successfully purchased " + bin.size() + " articles!");
        return numberOfArticles;
    }
}
