package onlinetest;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ayushij on 12/17/16.
 */
public class Friends implements IFriends {
    int noOfPeople;
    public ArrayList<String> friends[];
    public Friends(int noOfPeople){
        this.noOfPeople = noOfPeople;
        friends = new ArrayList[noOfPeople];
        for(int i = 0; i < this.noOfPeople; i++){
            friends[i] = new ArrayList<>();
        }
    }
//
    @Override
    public ArrayList<String>[] makeFriends(String personA, String personB) {
        boolean existingUserA = false;
        boolean existingUserB = false;
        int i = 0;
        for(i =0; i<friends.length; i++){
            if(friends[i].isEmpty()){ //arraylist starting this index is empty, now start adding new users at this index
                //System.out.println("empty index at i " + i + " users not found yet, will add now");
                break;
            }
            if(friends[i]!=null && friends[i].get(0).equals(personA)){ //this person already exists in our collection and has other friends
                existingUserA = true;
                if(!friends[i].contains(personB))
                friends[i].add(personB);
                //System.out.println(personA + " existed, added "+ personB + "  as his friend" + friends[i]);
            }
            if(friends[i]!=null && friends[i].get(0).equals(personB)){ //this person already exists in our collection and has other friends
                existingUserB = true;
                if(!friends[i].contains(personA))
                friends[i].add(personA);
                //System.out.println(personB + " existed, added "+ personA + "  as his friend" + friends[i]);
            }
        }
        if(!existingUserA){ //this user doesnt exist yet. add him and his friends now
            friends[i].add(0,personA);          //personA added to this empty index in friends array
            friends[i].add(personB);            //personB is now personA's friend
            //System.out.println(personA + " didnt exist, added "+ personA + " and " + personB + "  as his friend" + friends[i]);
            i++;

        }
        if(!existingUserB){ //this user doesnt exist yet. add him and his friends now
            friends[i].add(0,personB);          //personA added to this empty index in friends array
            friends[i].add(personA);            //personB is now personA's friend
            //System.out.println(personB + " didnt exist, added "+ personB + " and " + personA + "  as his friend" + friends[i]);
        }
        //System.out.println("added friends" + Arrays.toString(friends));
        return friends;

    }

    @Override
    public ArrayList<String>[] removeFriends(String personA, String personB) throws UserNotFoundException {
        //System.out.println("in remove" + Arrays.toString(friends) + " to remove" + personA + personB);
        boolean removedA = false;
        boolean removedB = false;
        for(int i = 0; i < noOfPeople; i++){

            if(friends[i].isEmpty()){
                throw new UserNotFoundException("User not found");
            }
            if(friends[i].get(0).equals(personA)){
                if(!friends[i].contains(personB))
                    throw new UserNotFoundException("These two users are not friends");
                friends[i].remove(personB);

                removedA = true;
            }
            if(friends[i].get(0).equals(personB)){
                if(!friends[i].contains(personA))
                    throw new UserNotFoundException("These two users are not friends");
                friends[i].remove(personA);
                removedB = true;

            }
        }
        if(!removedA || ! removedB){

            throw new UserNotFoundException("User not found");
        }

        return friends;

    }

    private boolean checkUsersValidity(){ return false; }

    @Override
    public ArrayList<String> getDirectFriends(String user) {
        ArrayList<String> directFriends = new ArrayList<>();
        directFriends.ensureCapacity(noOfPeople);
        boolean userFound = false;
        for(int i = 0; i < noOfPeople; i++) {

            if (friends[i].isEmpty()) {
                System.out.println("User not found");
                break;
            }
            if (friends[i].get(0).equals(user)) {
                userFound = true;
                directFriends.addAll(0, friends[i]);
                directFriends.remove(0);
            }
        }
        if(directFriends == null || userFound == false)
            System.out.println("No friends found");
        return directFriends;
    }

    @Override
    public ArrayList<String> getIndirectFriends(String user) {
        ArrayList<String> indirectFriends = new ArrayList<>();
        indirectFriends.ensureCapacity(noOfPeople);
        boolean userFound = false;
        String friend;
        for(int i = 0; i < noOfPeople; i++){
            if(friends[i].isEmpty()){
                System.out.println("User not found");
                break;
            }
            if(friends[i].get(0).equals(user)){ //found the user, now get all of it's friends direct friends
                userFound = true;
                for(int x = 1; x<friends[i].size(); x++){
                    friend = friends[i].get(x);
                    indirectFriends.addAll(getDirectFriends(friend));
                }
                indirectFriends.removeAll(getDirectFriends(user)); //remove user's direct friends
                indirectFriends.removeIf(user::equals); //remove the user itself
            }
        }
        if(indirectFriends == null || indirectFriends.size() == 0 || userFound == false) {
            System.out.println("No Indirect friends found");
            return null;
        }
        return indirectFriends;
    }

    public static void main(String args[]) {
        Friends obj = new Friends(3);
        System.out.println(Arrays.toString(obj.makeFriends("user1", "user2")));
        System.out.println(Arrays.toString(obj.makeFriends("user1", "user3")));
        try {
            System.out.println(Arrays.toString(obj.removeFriends("user5", "user4")));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        obj.makeFriends("ayushi", "saksham");
        obj.makeFriends("ayushi", "ben");
        obj.makeFriends("saksham", "abhinav");
        obj.makeFriends("saksham", "suyash");
        obj.makeFriends("ayushi", "suyash");
        obj.makeFriends("saksham", "riya");
        obj.makeFriends("riya", "abhinav");
        System.out.println(Arrays.toString(obj.friends));
        obj.removeFriends("ayushi", "suyash"); //correct user
        obj.removeFriends("ayushi", "saksham"); //correct user
        System.out.println(Arrays.toString(obj.friends));
        System.out.println(obj.getDirectFriends("ayushi"));
        System.out.println(obj.getIndirectFriends("ayushi"));
        System.out.println(obj.getIndirectFriends("aysdushi"));
        obj.removeFriends("ayushi", "saksham"); //correct user
        System.out.println(Arrays.toString(obj.friends));
        System.out.println(obj.getIndirectFriends("ayushi"));
        System.out.println(obj.getDirectFriends("ayushi"));
    }

}

