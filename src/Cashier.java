
import java.util.concurrent.Callable;

/**
 * @author Paolo Scattolin s1023775
 * @author Johan Urban s1024726
 * @author Sjaak en Pieter
 */
public class Cashier implements Callable<Void> {

    private final Register checkout;

    public Cashier(Register checkout) {
        this.checkout = checkout;
    }

    @Override
    public Void call() throws InterruptedException {
        while (Store.isOpen()) {
            Item temp = checkout.removeFromBelt();
            if (temp != null) {
                checkout.putInBin(temp);
            }
        }
        return null;
    }
}
