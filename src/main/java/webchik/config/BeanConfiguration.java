package webchik.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import webchik.models.Offer;
import webchik.services.dtos.ShowOfferInfoDto;

@Configuration
public class BeanConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.createTypeMap(Offer.class, ShowOfferInfoDto.class)
                .addMapping(src -> src.getUser().getUsername(), ShowOfferInfoDto::setUsername);
        return modelMapper;
    }
}

