package onlinetest;

/**
 * Created by ayushij on 12/18/16.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}