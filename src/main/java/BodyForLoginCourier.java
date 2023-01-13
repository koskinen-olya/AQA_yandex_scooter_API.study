public class BodyForLoginCourier {
    private String login;
    private String password;

    public BodyForLoginCourier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public BodyForLoginCourier() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
