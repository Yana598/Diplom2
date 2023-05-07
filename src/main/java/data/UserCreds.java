package data;

public class UserCreds {

    private final String email;
    private final String password;

    public UserCreds(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCreds credsFrom(User user) {
        return new UserCreds(user.getEmail(), user.getPassword());
    }
}
