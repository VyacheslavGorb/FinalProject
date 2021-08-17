package edu.gorb.musicstudio.model.pool;

import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;

public class ConnectionPoolTest {

    @BeforeClass
    public void initPool() {
        ConnectionPool.getInstance();
    }

    @Test
    public void releaseConnectionTest() {
        Connection connection = EasyMock.mock(Connection.class);
        boolean res = ConnectionPool.getInstance().releaseConnection(connection);
        Assert.assertFalse(res);
    }

    @AfterClass
    public void destroyPool() {
        ConnectionPool.getInstance().destroyPool();
    }
}
