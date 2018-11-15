package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Authentication {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "status")
    private boolean status;

    public Authentication() {

    }

    public Authentication(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
