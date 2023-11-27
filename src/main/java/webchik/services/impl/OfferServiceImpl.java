package webchik.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webchik.models.Model;
import webchik.models.Offer;
import webchik.models.User;
import webchik.repositories.ModelRepository;
import webchik.repositories.OfferRepository;
import webchik.repositories.UserRepository;
import webchik.services.ModelService;
import webchik.services.OfferService;
import webchik.services.UserService;
import webchik.services.dtos.OfferDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService<UUID> {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final ModelRepository modelRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelService modelService;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, ModelRepository modelRepository, UserRepository userRepository, UserService userService, ModelService modelService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.modelRepository = modelRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.modelService = modelService;
    }

    @Override
    public void delete(OfferDto offer) {
        offerRepository.deleteById(offer.getId());
    }

    @Override
    public void delete(UUID id) {
        offerRepository.deleteById(id);
    }

    @Override
    public List<OfferDto> getAll() {
        return offerRepository.findAll().stream().map((m)->modelMapper.map(m, OfferDto.class)).collect(Collectors.toList());

    }

    @Override
    public Optional<OfferDto> findOffer(UUID id) {
        return Optional.ofNullable(modelMapper.map(offerRepository.findById(id), OfferDto.class));
    }

    @Override
    public OfferDto add(OfferDto offer) {
        Offer o = modelMapper.map(offer, Offer.class);
        o.setUser(userService.findByUsername(offer.getUsername()));
        o.setModel(modelService.findByName(offer.getModelName()));
        o.setCreated(LocalDateTime.now());
        return modelMapper.map(offerRepository.saveAndFlush(o), OfferDto.class);
    }

    @Override
    public  OfferDto update(OfferDto offerDto){
        Optional<Offer> dbOffer = offerRepository.findById(offerDto.getId());
        if (dbOffer.isEmpty()) {
            throw new NoSuchElementException("Offer not found");
        }
            Offer offer = dbOffer.get();
            modelMapper.map(offerDto, offer);
        Optional<User> dbUser = userRepository.findByUsername(offerDto.getUsername());
        if (dbUser.isEmpty()){
            throw new NoSuchElementException("User not found");
        }
        offer.setUser(userService.findByUsername(offerDto.getUsername()));
        Optional<Model> dbModel = modelRepository.findByName(offerDto.getModelName());
        if (dbModel.isEmpty()){
            throw new NoSuchElementException("Model not found");
        }
        offer.setModel(modelService.findByName(offerDto.getModelName()));
            offer.setModified(LocalDateTime.now());
            offer.setCreated(dbOffer.get().getCreated());
            return modelMapper.map(offerRepository.saveAndFlush(offer), OfferDto.class);

    }
}
