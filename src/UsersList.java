public interface UsersList {

    public void addUser(User user);

    public User retrieveUserByID(Integer id);

    public User retrieveUserByIndex(int index);

    public int retrieveTheNumberUsers();
}
