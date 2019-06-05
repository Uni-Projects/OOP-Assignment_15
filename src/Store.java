
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 *
 * @author Sjaak en Pieter
 */
public class Store {

    public static final int NUMBER_OF_CHECKOUTS = 5;

    private final Register[] checkouts;
    public final ExecutorService executor;
    private final List<Cashier> cashiers = new LinkedList<>();

    public Store(ExecutorService executor) {
        this.executor = executor;
        
        checkouts = new Register[NUMBER_OF_CHECKOUTS];
        for (int i = 0; i < NUMBER_OF_CHECKOUTS; i += 1) {
            checkouts[i] = new Register();
            cashiers.add(new Cashier(checkouts[i]));
        }
    }
    
    public List<Future<Void>> open() {
        return cashiers.stream()
                .map(c -> executor.submit(c))
                .collect(Collectors.toList());
    }

    public List<Item> getArticles(int amount) {
        ArrayList<Item> articles = new ArrayList<>(amount);
        for (int i = 1; i <= amount; i += 1) {
            articles.add(new Item(i));
        }
        return articles;
        
    }

    public Register getCheckout(int register_nr) {
        return checkouts[register_nr];
    }
}
