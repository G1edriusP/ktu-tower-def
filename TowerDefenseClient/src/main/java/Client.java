import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonProcessingException;
import game.Troop;
import net.Session;

public class Client {
    public static void main(String[] args) throws InterruptedException, URISyntaxException, JsonProcessingException {
        Troop troop = new Troop();
        Session.getInstance().register(troop);
        troop.send();
        troop.setX(5);
        troop.send();

        Thread.sleep(10000);

        Troop troop2 = new Troop();
        Session.getInstance().register(troop2);
        troop2.send();
        troop2.setX(3);
        troop2.send();
    }
}
