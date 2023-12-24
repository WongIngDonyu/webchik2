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
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BrandService brandService;

    private final ModelService modelService;

    private final UserRoleService userRoleService;

    private final UserService userService;

    private final OfferService offerService;
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    private final String defaultPassword;
    public DataInitializer(BrandService brandService, ModelService modelService, UserRoleService userRoleService, UserService userService, OfferService offerService, UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder,@Value("aaaaa") String defaultPassword) {
        this.brandService = brandService;
        this.modelService = modelService;
        this.userRoleService = userRoleService;
        this.userService = userService;
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
        var userRole = userRoleRepository.
                findByRole(UserRole.Role.ADMIN).orElseThrow();
        var user = new User("Ameruk", passwordEncoder.encode(defaultPassword), "User","User",true,"User",null,null);
       user.setUserRoles(List.of(userRole));
       userRepository.save(user);
        AddBrandDto addBrandDto = new AddBrandDto();
        addBrandDto.setName("akakakak");
        brandService.add(addBrandDto);
        AddModelDto addModelDto = new AddModelDto();
        addModelDto.setCategory(Model.Category.Truck);
        addModelDto.setName("Azaazazza");
        addModelDto.setEndYear(200001);
        addModelDto.setStartYear(20000);
        addModelDto.setImageUrl("213123");
        addModelDto.setBrandName("akakakak");
        modelService.add(addModelDto);
        AddOfferDto addOfferDto=new AddOfferDto();
        addOfferDto.setDescription("qwqweqwewqeqeqeqweqwe");
        addOfferDto.setImageUrl("3213213");
        addOfferDto.setEngine(Offer.Engine.Diesel);
        addOfferDto.setMileage(288898);
        addOfferDto.setPrice(100000);
        addOfferDto.setTransmission(Offer.Transmission.Automatic);
        addOfferDto.setModelName("Azaazazza");
        addOfferDto.setUsername("Ameruk");
        addOfferDto.setYear(100000);
        offerService.add(addOfferDto);
    }
}