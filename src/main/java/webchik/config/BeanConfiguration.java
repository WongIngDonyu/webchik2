package webchik.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import webchik.models.Offer;
import webchik.services.dtos.OfferDto;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.createTypeMap(Offer.class, OfferDto.class)
                .addMapping(src -> src.getUser().getUsername(), OfferDto::setUsername);

        return modelMapper;
    }
}
