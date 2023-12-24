package webchik.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
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
import webchik.services.dtos.AddOfferDto;
import webchik.services.dtos.OfferDto;
import webchik.services.dtos.ShowOfferInfoDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching

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
    //@Cacheable("offers")
    public List<ShowOfferInfoDto> getAll() {
        return offerRepository.findAll().stream().map((m)->modelMapper.map(m, ShowOfferInfoDto.class)).collect(Collectors.toList());

    }

    @Override
    public Optional<ShowOfferInfoDto> findOffer(UUID id) {
        return Optional.ofNullable(modelMapper.map(offerRepository.findById(id), ShowOfferInfoDto.class));
    }

    @Override
    public AddOfferDto add(AddOfferDto offer) {
        Offer o = modelMapper.map(offer, Offer.class);
        o.setUser(modelMapper.map(userService.findByUsername(offer.getUsername()), User.class));
        o.setModel(modelService.findByName(offer.getModelName()));
        o.setCreated(LocalDateTime.now());
        return modelMapper.map(offerRepository.saveAndFlush(o), AddOfferDto.class);
    }

    @Override
    public  AddOfferDto update(AddOfferDto offerDto){
        Optional<Offer> dbOffer = offerRepository.findById(offerDto.getId());
        if (dbOffer.isEmpty()) {
            throw new NoSuchElementException("Offer not found");
        }
            Offer offer = modelMapper.map(offerDto, Offer.class);
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
            return modelMapper.map(offerRepository.saveAndFlush(offer), AddOfferDto.class);

    }

    @Override
    public double averagePrice() {
        List<Offer> offers = offerRepository.findAll();
        if (offers.isEmpty()) {
            return 0.0;
        }
        double totalPrice = offers.stream().mapToDouble(Offer::getPrice).sum();
        return totalPrice/offers.size();
    }

    @Override
    public long getCountByTransmission(Offer.Transmission transmission) {
        return offerRepository.countByTransmission(transmission);
    }
}
