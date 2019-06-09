
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Paolo Scattolin s1023775
 * @author Johan Urban s1024726
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

        store.close();
        
        int result = customerResults.stream().map(i -> {
            try {
                return i.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
            }
            return 0;
        }).mapToInt(Integer::intValue).sum();

        
        for(Future<Void> c: cashiers){
            c.cancel(true);
        }
        store.executor.shutdownNow();
        System.out.println("All customers are done. " + result + " items sold.");
    }
}