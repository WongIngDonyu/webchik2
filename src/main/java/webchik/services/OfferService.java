package webchik.services;


import webchik.services.dtos.AddOfferDto;
import webchik.services.dtos.OfferDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferService <I extends UUID>{
    void delete(OfferDto offer);

    void delete(UUID id);

    List<OfferDto> getAll();

    Optional<OfferDto> findOffer(UUID id);

    AddOfferDto add(AddOfferDto offer);
    OfferDto update(OfferDto offerDto);
}
