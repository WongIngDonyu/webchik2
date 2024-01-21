package webchik.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "models")
public class Model extends BaseEntity{
    private String name;
    private Category category;
    private String imageUrl;
    private int startYear;
    private int endYear;
    private Brand brand;
    private List<Offer> offers;
    public Model(){}

    public Model(String name, Category category, String imageUrl, int startYear, int endYear, Brand brand, List<Offer> offers) {
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
        this.startYear = startYear;
        this.endYear = endYear;
        this.brand = brand;
        this.offers = offers;
    }
    @Column(name = "modelName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Column(name = "imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Column(name = "startYear")
    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
    @Column(name = "endYear")
    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
    @ManyToOne
    @JoinColumn(name = "brand_id")
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    @OneToMany(mappedBy = "model", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
    public enum Category {
        Car(0), Buss(1), Truck(2), Motorcycle(3);
        private final int number;
        Category(int number) {
            this.number=number;
        }
        public int getNumber(){
            return number;
        }
    }
}

