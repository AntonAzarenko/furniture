package azarenko.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class BaseEntity implements Serializable {
    @Id
    private String id;

    public BaseEntity() {
    }

    public BaseEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
