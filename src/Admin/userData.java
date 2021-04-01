package Admin;

public class userData {

    private final String userID;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
    private final String account;

    public userData(String userID, String username, String firstname, String lastname, String password, String email, String account) {
        this.userID = userID;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.account = account;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAccount() {
        return account;
    }
}
