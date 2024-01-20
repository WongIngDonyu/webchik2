package webchik.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import webchik.models.Model;
import webchik.models.Offer;
import webchik.models.User;
import webchik.models.UserRole;
import webchik.repositories.UserRepository;
import webchik.repositories.UserRoleRepository;
import webchik.services.*;
import webchik.services.dtos.AddBrandDto;
import webchik.services.dtos.AddModelDto;
import webchik.services.dtos.AddOfferDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BrandService brandService;

    private final ModelService modelService;

    private final OfferService offerService;
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    private final String defaultPassword;
    public DataInitializer(BrandService brandService, ModelService modelService, OfferService offerService, UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder,
                           @Value("aaaaa") String defaultPassword) {
        this.brandService = brandService;
        this.modelService = modelService;
        this.offerService = offerService;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() throws IOException {
        if (userRoleRepository.count() == 0) {
            var adminRole = new UserRole(null,UserRole.Role.ADMIN);
            var normalUserRole = new UserRole(null,UserRole.Role.USER);
            userRoleRepository.save(adminRole);
            userRoleRepository.save(normalUserRole);
        }
        var userRole = userRoleRepository.findByRole(UserRole.Role.ADMIN).orElseThrow();
        var user = new User("Ameruk", passwordEncoder.encode(defaultPassword), "User","User",true,"",null,null);
        user.setUserRoles(List.of(userRole));
        user.setCreated(LocalDateTime.now());
        userRepository.save(user);

        String[] carBrands = {"Toyota", "Ford", "Chevrolet", "Honda", "BMW"};
        String[] modelNames = {"Sedan", "Cherokee", "Coupe", "Hatchback", "Truck"};
        String[] descriptions = {"Luxurious sedan with advanced features.","Powerful Cherokee built for off-road adventures.","Sporty coupe with cutting-edge technology." ,"Fuel-efficient compact car for city driving.","Spacious truck designed for heavy-duty tasks."};
        String[] usernames = {"Lololoshka", "Smurf", "Wong", "Gazzy", "Neerry"};
        String[] passwords = {"123456789", "Smurf123", "WongIngDonyu", "GazzyXgod", "NeerrySergo"};
        String[] firsNames = {"Egor", "Grisha", "Oleg", "Vova", "Sergai"};
        String[] lastNames = {"Popov", "Sergov", "Plyhev", "Vorov", "Antonov"};
        String[] urlsCars = {"https://tmna.aemassets.toyota.com/is/image/toyota/toyota/seo-category/desktop/hybridcarssummary2desktop.png?fit=constrain&qlt=100&wid=550&resMode=bisharp","https://cdn.ototeknikveri.com/Files/News/Big/fordun-buyuk-suv-modeli-explorera-makyaj.jpg", "https://www.autoscout24.fr/cms-content-assets/3r2vkWujvhyg4mcaL32ZFs-ef281aca7692905e71e7fbe1e902e807-chevrolet-camaro-l-01-1100.jpg", "https://i.gaw.to/content/photos/43/75/437594-honda-civic-hatchback-2020-l-alternative-pratique.jpeg", "https://cdn.motor1.com/images/mgl/q1VlL/s1/bmw-x7-m50i-pickup-truck-rendering.jpg"};
        String[] urlsUsers = {"https://images.pexels.com/photos/771742/pexels-photo-771742.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "https://wallpapers.com/images/hd/cool-profile-picture-awled9dwo4qq2yv2.jpg", "https://www.befunky.com/images/wp/wp-2021-01-linkedin-profile-picture-after.jpg?auto=avif,webp&format=jpg&width=944" , "https://image.winudf.com/v2/image1/bmV0LndsbHBwci5ib3lzX3Byb2ZpbGVfcGljdHVyZXNfc2NyZWVuXzBfMTY2NzUzNzYxN18wOTk/screen-0.webp?fakeurl=1&type=.webp" ,"https://assets-global.website-files.com/650865454c2393ac25711ff7/650865454c2393ac25714a3e_The%20best%20selfie%20Ideas%20for%20sm%20pfp.jpeg"};

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            AddBrandDto addBrandDto = new AddBrandDto();
            addBrandDto.setName(carBrands[i]);
            brandService.add(addBrandDto);
            AddModelDto addModelDto = new AddModelDto();
            Model.Category[] categories = Model.Category.values();
            addModelDto.setCategory(categories[random.nextInt(categories.length)]);
            addModelDto.setName(modelNames[i]);
            int startYear = 2000 + random.nextInt(101);
            int endYear = startYear + random.nextInt(101);
            addModelDto.setStartYear(startYear);
            addModelDto.setEndYear(endYear);
            addModelDto.setImageUrl(urlsCars[i]);
            addModelDto.setBrandName(carBrands[i]);
            modelService.add(addModelDto);
            String username = usernames[i];
            String password = passwords[i];
            String firsName = firsNames[i];
            String lastName = lastNames[i];
            var userRole2 = userRoleRepository.findByRole(UserRole.Role.USER).orElseThrow();
            var user2 = new User(username, passwordEncoder.encode(password), firsName, lastName, true, urlsUsers[i], null, null);
            user2.setUserRoles(List.of(userRole2));
            user2.setCreated(LocalDateTime.now());
            userRepository.save(user2);
            AddOfferDto addOfferDto = new AddOfferDto();
            addOfferDto.setDescription(descriptions[i]);
            addOfferDto.setImageUrl(urlsCars[i]);
            Offer.Engine[] engines = Offer.Engine.values();
            addOfferDto.setEngine(engines[random.nextInt(engines.length)]);
            int randomMileage = random.nextInt(991) + 10;
            addOfferDto.setMileage(randomMileage);
            int randomPrice = random.nextInt(9001) + 1000;
            addOfferDto.setPrice(randomPrice);
            Offer.Transmission[] transmissions = Offer.Transmission.values();
            addOfferDto.setTransmission(transmissions[random.nextInt(transmissions.length)]);
            addOfferDto.setModelName(modelNames[i]);
            addOfferDto.setUsername(usernames[i]);
            int randomYear = random.nextInt(15) + 1;
            addOfferDto.setYear(randomYear);
            offerService.add(addOfferDto);
        }
    }
}