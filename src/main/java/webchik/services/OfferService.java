package webchik.services;


import webchik.models.Offer;
import webchik.services.dtos.AddOfferDto;
import webchik.services.dtos.ShowOfferInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferService <I extends UUID>{
    void delete(UUID id);

    List<ShowOfferInfoDto> getAll();

    Optional<ShowOfferInfoDto> findOffer(UUID id);

    AddOfferDto add(AddOfferDto offer);
    AddOfferDto update(AddOfferDto offerDto);

    double averagePrice();

    long getCountByTransmission(Offer.Transmission transmission);
}
