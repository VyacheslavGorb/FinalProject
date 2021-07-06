package _main;

import edu.gorb.musicstudio.model.pool.ConnectionPool;

public class Main {
    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.destroyPool();
    }
}
