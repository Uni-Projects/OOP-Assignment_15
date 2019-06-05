
import java.util.concurrent.Callable;

/**
 *
 * @author Sjaak en Pieter
 */
public class Cashier implements Callable<Void> {

    private final Register checkout;

    public Cashier(Register checkout) {
        this.checkout = checkout;
    }

    @Override
    public Void call() {
        return null;
    }
}
