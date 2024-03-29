package onlinetest.junit.test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by ayushij on 12/17/16.
 */
public class FriendsTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(FriendsTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
