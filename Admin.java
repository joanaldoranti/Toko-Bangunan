public class Admin extends User {
    private String username;
    private String password;
    private boolean isLoggedIn;

    public Admin(String username, String password) {
        this.role = "Admin";
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
    }

    public boolean login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            this.isLoggedIn = true;
        } else {
            this.isLoggedIn = false;
        }
        return isLoggedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    @Override
    public String getRole() {
        return role;
    }
}
