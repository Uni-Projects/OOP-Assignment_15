
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Sjaak en Pieter
 */
public class Main {

    private static final int NR_OF_CLIENTS = 30;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Store store = new Store(executor);

        List<Future<Void>> cashiers = store.open();

        List<Customer> customers = IntStream.range(0, NR_OF_CLIENTS)
                .mapToObj(i -> new Customer(i, store))
                .collect(Collectors.toList());
        List<Future<Integer>> customerResults = executor.invokeAll(customers);
        int result = 0;

        System.out.println("All customers are done. " + result + " items sold.");
    }
}
