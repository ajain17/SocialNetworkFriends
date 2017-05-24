package onlinetest;
import java.util.ArrayList;

/**
 * Created by ayushij on 12/17/16.
 */
public interface IFriends {

    ArrayList<String>[] makeFriends(String personA, String personB);
    ArrayList<String>[] removeFriends(String personA, String personB) throws UserNotFoundException ;
    ArrayList<String> getDirectFriends(String user);
    ArrayList<String> getIndirectFriends(String user);

}
