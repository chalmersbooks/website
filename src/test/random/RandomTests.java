package random;

import core.User;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.UserFacade;

@Log
public class RandomTests {

    private UserFacade facade;

    @Before
    public void setup() {
        facade = new UserFacade();
    }

    @Test
    public void addToDatabase() {

        if (facade == null) {
            log.info("its null.. wtf...");
            Assert.fail();
        }

        facade.create(new User());

    }
}
