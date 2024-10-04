package io.amplicode;

import io.amplicode.model.Owner;
import io.amplicode.model.Pet;
import io.amplicode.model.PetType;
import io.amplicode.repository.OwnerRepository;
import io.amplicode.repository.PetRepository;
import io.amplicode.repository.PetTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class PetclinicDataJdbcAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetclinicDataJdbcAdminApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(OwnerRepository ownerRepository, PetTypeRepository petTypeRepository,
                                               PetRepository petRepository) {
        return (args) -> {

            // types
            PetType dogType = new PetType();
            dogType.setId(1L);
            dogType.setName("dog");

            PetType catType = new PetType();
            catType.setId(2L);
            dogType.setName("cat");

            petTypeRepository.saveAll(List.of(dogType, catType));

            // pets
            Pet dog = new Pet();
            dog.setId(1L);
            dog.setName("dag");
            dog.setBirthDate(LocalDate.of(2001, 12, 12));
            dog.setPetTypeId(AggregateReference.to(dogType.getId()));

            Pet cat = new Pet();
            cat.setId(2L);
            cat.setName("cot");
            cat.setBirthDate(LocalDate.of(2000, 11, 11));
            cat.setPetTypeId(AggregateReference.to(catType.getId()));

//            petRepository.saveAll(List.of(dog, cat));

            // owner
            Owner owner = new Owner();
            owner.setId(1L);
            owner.setAddress("LA");
            owner.setPets(List.of(dog, cat));
            ownerRepository.save(owner);
        };
    }
}
