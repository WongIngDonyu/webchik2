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


        // UserRoleDto admin = new UserRoleDto(null, UserRole.Role.Admin);
       // UserRoleDto user = new UserRoleDto(null, UserRole.Role.User);
       // userRoleService.add(admin);
        //userRoleService.add(user);
        /*  BrandDto b1 = new BrandDto(null,"Zoo");
        BrandDto sb1 = brandService.add(b1);
        BrandDto b2 = new BrandDto(null,"ZOV");
        BrandDto sb2 = brandService.add(b2);
        sb1.setName("LOX");
        brandService.update(sb1);
        ModelDto m1 = new ModelDto(null,"Zoo1", Model.Category.Motorcycle, "SomeUrl", 2023, 2054,"Zoo");
        ModelDto modelDto = modelService.add(m1);
        modelDto.setName("alallala");
        modelDto.setBrandName("ZOV");
        modelService.update(modelDto);
        ModelDto m2 = new ModelDto(null,"Zoo2222", Model.Category.Motorcycle, "SomeUrl", 2023, 2054,"LOX");
        ModelDto modelDto1 = modelService.add(m2);
        //System.out.println(modelDto.getBrand());
        UserRoleDto userRoleDto = new UserRoleDto(null, UserRole.Role.User);
        UserRoleDto userRoleDto1 = userRoleService.add(userRoleDto);
        userRoleDto1.setRole(UserRole.Role.Admin);
        userRoleService.update(userRoleDto1);
        UserDto userDto = new UserDto(null, "1","1488","Egor", "Ananasiy", true, "someUrl", UserRole.Role.Admin);
        UserDto userDro1 = userService.add(userDto);
        userDro1.setActive(false);
        userService.update(userDro1);
        UserRoleDto userRoleDto2 = new UserRoleDto(null, UserRole.Role.User);
        UserRoleDto userRoleDto3 = userRoleService.add(userRoleDto2);
        userDro1.setRole(UserRole.Role.User);
        userService.update(userDro1);
        OfferDto offerDto = new OfferDto(null,"somekool", Offer.Engine.DIESEL, "someUrl", 100, 100000, Offer.Transmission.AUTOMATIC, 2003,"1", "alallala");
        OfferDto offerDto1 = offerService.add(offerDto);
        System.out.println(userService.findByUsername(offerDto1.getUsername()));
        offerDto1.setPrice(6666);
        offerService.update(offerDto1);*/

    }
}