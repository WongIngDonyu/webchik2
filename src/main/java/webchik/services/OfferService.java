package webchik.services;


import webchik.models.Offer;
import webchik.services.dtos.AddOfferDto;
import webchik.services.dtos.OfferDto;
import webchik.services.dtos.ShowOfferInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferService <I extends UUID>{
    void delete(OfferDto offer);

    void delete(UUID id);

    List<ShowOfferInfoDto> getAll();

    Optional<ShowOfferInfoDto> findOffer(UUID id);

    AddOfferDto add(AddOfferDto offer);
    ShowOfferInfoDto update(ShowOfferInfoDto offerDto);

    double averagePrice();

    long getCountByTransmission(Offer.Transmission transmission);
}
