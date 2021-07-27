public class UsersArrayList implements UsersList {

    private int size = 10;
    private int it = -1;
    private User[] array = new User[size];

    @Override
    public void addUser(User user) {
        if (++it == size) {
            size *= 2;
            User[] tmp = array;
            array = new User[size];
            for (int i = 0; i < tmp.length; i++)
                array[i] = tmp[i];
        }
        array[it] = user;
    }

    @Override
    public User retrieveUserByID(Integer id) {
        for (int i = 0; i <= it; i++) {
            if (array[i].getIdentifier().equals(id))
                return array[i];
        }
        throw new UserNotFoundException();
    }

    @Override
    public User retrieveUserByIndex(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        return array[index];
    }

    @Override
    public int retrieveTheNumberUsers() { return size; }

    class UserNotFoundException extends RuntimeException {
        public void printError() { System.out.println("UserNotFoundException"); }
    }
}
