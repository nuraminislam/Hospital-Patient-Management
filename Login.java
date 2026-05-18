public class Login {
    private String username;
    private String password;
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public boolean checkPassword() {
        return password.equals("1234");
    }

    public boolean checkUsername() {
        return username.equals("mahim");
    }

    public boolean authenticate() {
        if (checkPassword() && checkUsername()) {
            return true;
        } else  {
            return false;
        }
    }
}
