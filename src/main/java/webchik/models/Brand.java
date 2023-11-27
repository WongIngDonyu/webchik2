package webchik.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity{
    private String name;
    private List<Model> models;

    public Brand(){}

    public Brand(String name, List<Model> models) {
        this.name = name;
        this.models = models;
    }
    @Column(name = "brandName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
