package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.pool.ConnectionPool;
import edu.gorb.musicstudio.model.service.UserService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

public class UserServiceImplTest {

    private UserService userService;

    @BeforeClass
    public void initPool() {
        userService = new UserServiceImpl();
        ConnectionPool.getInstance();
    }

    @Test
    public void findUserByIdTest() throws ServiceException {
        Optional<User> user = userService.findUserById(-10L);
        Assert.assertTrue(user.isEmpty());
    }

    @DataProvider(name = "user_count")
    public Object[][] createData() {
        return new Object[][]{
                {2, 1},
                {3, 2},
                {4, 2},
                {5, 3},
                {0, 1},
        };
    }

    @Test(dataProvider = "user_count")
    public void calcPageCountTest(int userCount, int expected) {
        Assert.assertEquals(userService.calcPagesCount(userCount), expected);
    }

    @AfterClass
    public void destroyPool() {
        ConnectionPool.getInstance().destroyPool();
    }
}
