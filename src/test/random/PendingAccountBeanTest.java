package random;

import entity.User;
import authorization.PendingAccountBean;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Log
public class PendingAccountBeanTest {

    private PendingAccountBean bean;

    @Before
    public void init() {
        bean = new PendingAccountBean();
        bean.init();
    }

    @Test
    public void createRandomRegistrationHash() {

        User user = new User();
        user.setEmail("test");
        user.setPassword("test");
        bean.addToPending(user);

        Assert.assertTrue(bean.isPending(user.getEmail()));

    }
}
