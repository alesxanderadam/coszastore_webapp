package alticshaw.com.coszastore.entity;

import javax.persistence.*;

@Entity(name = "contact")
public class ContactEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "message")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContactEntity(int id, String email, String message) {
        this.id = id;
        this.email = email;
        this.message = message;
    }

    public ContactEntity(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public ContactEntity() {
    }
}
