package _main;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.pool.ConnectionPool;

public class Main {
    public static void main(String[] args) throws DaoException {
        User user = new User.Builder()
                .setUserId(1)
                .setLogin("user13admin")
                .setEmail("email13").setRole(UserRole.ADMIN)
                .setPassword("skldfj")
                .setStatus(UserStatus.ACTIVE)
                .setName("name")
                .setPhoneNumber("")
                .setPatronymic("sdf")
                .setSurname("sjdklf")
                .build();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
//            UserDao userDao = new UserDaoImpl();
//            userDao.insert(user);
        } finally {
            connectionPool.destroyPool();
        }
    }
}
