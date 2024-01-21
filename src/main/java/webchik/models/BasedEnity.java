package webchik.models;

import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public abstract class BasedEnity {
    protected UUID id;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}


