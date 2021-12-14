package org.but.feec.airport.api;

public class PersonAuthView {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String email) {
        this.userName = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PersonAuthView{" +
                "username='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}