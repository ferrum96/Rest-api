package glue;

import java.util.Comparator;

public class UserComporator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return Integer.compare(Integer.parseInt(o1.getName().substring(o1.getName().length() - 1)), Integer.parseInt(o2.getName().substring(o2.getName().length() - 1)));
    }
}
