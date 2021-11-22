package client;

public class UserIdsGenerator {

    private Integer instance = 0;

    public Integer generateId() { return this.instance++; }

    public UserIdsGenerator getInstance() { return this; }
}
