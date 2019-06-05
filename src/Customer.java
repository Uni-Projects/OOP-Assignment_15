
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 *
 * @author Sjaak en Pieter
 */
public class Customer implements Callable<Integer> {

    public static int MAX_ITEMS = 20;
    private final Store store;
    private final int customerNumber;
    private final int numberOfArticles;
    private final static Random generator = new Random();
    private List <Item> bin;
    private Register register;

    public Customer(int number, Store store) {
        this.store = store;
        customerNumber = number;
        numberOfArticles = generator.nextInt(MAX_ITEMS) + 1;
        bin = new ArrayList<>(numberOfArticles);
    }

    @Override
    public Integer call(){
        bin = store.getArticles(numberOfArticles);
        register = store.getCheckout(generator.nextInt(10));
        try {
            register.claim(bin);
        } catch (InterruptedException ex) {
            System.out.println("SOmething went wrong with claim");
        }
        for(int i = 0 ; i < bin.size(); i++){
            register.putOnBelt(bin.remove(i));
        }
         for(int i = 0 ; i < numberOfArticles; i++){
            bin.add(register.removeFromBelt());
        }
 
        
        return 1;
    }
}
