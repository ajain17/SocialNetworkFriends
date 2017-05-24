package onlinetest.junit.test;
import org.junit.*;
import onlinetest.Friends;
import onlinetest.UserNotFoundException;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ayushij on 12/17/16.
 */
public class FriendsTest {

    public ArrayList<String> friends[];

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void makeFriends() throws Exception {
        System.out.println("@Test - makeFriends");
        ArrayList<String> result[] = new ArrayList[2];
        result[0] = new ArrayList<>(Arrays.asList("user1", "user2"));
        result[1] = new ArrayList<>(Arrays.asList("user2", "user1"));
        Friends friendsTester = new Friends(2);
        assertArrayEquals("should add entries" , result, friendsTester.makeFriends("user1", "user2"));
        assertArrayEquals("should be idempotent " , result, friendsTester.makeFriends("user1", "user2"));
    }

    @Test
    public void removeFriends() throws UserNotFoundException {
        System.out.println("@Test - removeFriends");
        ArrayList<String> result[] = new ArrayList[3];
        result[0] = new ArrayList<>(Arrays.asList("user1" , "user3"));
        result[1] = new ArrayList<>(Arrays.asList("user2"));
        result[2] = new ArrayList<>(Arrays.asList("user3", "user1"));

        Friends friendsTester = new Friends(3);

        //no exception thrown, user array would be altered because valid users provided
        friendsTester.makeFriends("user1","user2");
        friendsTester.makeFriends("user3","user1");
        assertArrayEquals("friends should be removed from each others list", result, friendsTester.removeFriends("user1","user2"));

        //exception will be thrown because user3 and user 2 are not friends
        friendsTester.makeFriends("user1","user2");
        thrown.expect(UserNotFoundException.class);
        thrown.expectMessage("These two users are not friends");
        friendsTester.removeFriends("user3", "user2");
    }

    @Test
    public void getDirectFriends() throws Exception {
        System.out.println("@Test - getDirectFriends");
        ArrayList<String> directFriends = new ArrayList<>(Arrays.asList("user1", "user2"));
        Friends friendsTester = new Friends(3);
        friendsTester.makeFriends("user3", "user1");
        friendsTester.makeFriends("user3", "user2");
        assertEquals("should get only direct friends", directFriends, friendsTester.getDirectFriends("user3"));
    }

    @Test
    public void getIndirectFriends() throws Exception {
        System.out.println("@Test - getIndirectFriends");
        ArrayList<String> inDirectFriends = new ArrayList<>(Arrays.asList("user4"));
        Friends friendsTester = new Friends(4);
        friendsTester.makeFriends("user3", "user1");
        friendsTester.makeFriends("user1", "user2");
        friendsTester.makeFriends("user4", "user2");
        assertEquals("should get only indirect friends", inDirectFriends, friendsTester.getIndirectFriends("user1"));
    }
}
