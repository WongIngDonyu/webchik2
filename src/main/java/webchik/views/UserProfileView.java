package webchik.views;

import webchik.models.Offer;

import java.util.List;

public class UserProfileView {
    private String username;

    private String firstName;

    private String lastName;
    private List<Offer> offers;
    private String imageUrl;

    public UserProfileView() {
    }

    public UserProfileView(String username, String firstName, String lastName, List<Offer> offers,String imageUrl) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.offers = offers;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
