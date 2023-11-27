package webchik.models;

import jakarta.persistence.*;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{
    private String description;
    private Engine engine;
    private String imageUrl;
    private int mileage;
    private int price;
    private Transmission transmission;
    private int year;
    private Model model;
    private User user;

    public Offer(){}
    public Offer(String description, Engine engine, String imageUrl, int mileage, int price, Transmission transmission, int year, Model model, User user) {
        this.description = description;
        this.engine = engine;
        this.imageUrl = imageUrl;
        this.mileage = mileage;
        this.price = price;
        this.transmission = transmission;
        this.year = year;
        this.model = model;
        this.user = user;
    }
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "engine")
    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    @Column(name = "imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Column(name = "mileage")
    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @Column(name = "transmission")
    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }
    @Column(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    @ManyToOne
    @JoinColumn(name = "model_id")
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    @ManyToOne
    @JoinColumn(name = "seller_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public enum Engine {
        GASOLINE(0), DIESEL(1), ELECTRIC(2), HYBRID(3);
        private int number;
        Engine(int number) {
            this.number=number;
        }
        public int getNumber(){
            return number;
        }
    }
    public enum Transmission {
        MANUAL(0), AUTOMATIC(1);
        private int number;
        Transmission(int number) {
            this.number=number;
        }
        public int getNumber(){
            return number;
        }
    }

}
