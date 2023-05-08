package userUser;

import data.User;

import static utils.Utils.randomString;
public class UserGenerator {

    public static User randomUser() {
        return new User()
                .setEmail(randomString(10)+"@mail.ru")
                .setPassword(randomString(10))
                .setName(randomString(10));
    }
}