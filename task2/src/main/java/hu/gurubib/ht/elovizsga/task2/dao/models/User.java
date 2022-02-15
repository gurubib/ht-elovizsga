package hu.gurubib.ht.elovizsga.task2.dao.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public User() { }

    public User(String email) {
        this.email = email;
    }

    public User(long id, String email) {
        this.id = id;
        this.email = email;
    }

    private String email;
    private boolean readTermsAndConditions = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isReadTermsAndConditions() {
        return readTermsAndConditions;
    }

    public void setReadTermsAndConditions(boolean readTermsAndConditions) {
        this.readTermsAndConditions = readTermsAndConditions;
    }
}
