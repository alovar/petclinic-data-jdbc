package io.amplicode;

import io.amplicode.model.BaseEntity;
import io.amplicode.model.Owner;
import io.amplicode.model.Pet;
import io.amplicode.model.PetType;
import io.amplicode.repository.OwnerRepository;
import io.amplicode.repository.PetRepository;
import io.amplicode.repository.PetTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.event.BeforeConvertEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class PetclinicDataJdbcAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetclinicDataJdbcAdminApplication.class, args);
    }

    private Map<String, Long> idMap = new ConcurrentHashMap<>();

    @Bean
    public ApplicationListener<?> idSetting() {
        return (ApplicationListener<BeforeConvertEvent>) event -> {
            Object entity = event.getEntity();
            if (entity instanceof BaseEntity && ((BaseEntity) entity).getId() == null) {
                String key = entity.getClass().getName();
                Long id = idMap.getOrDefault(key, 1L);
                long nextId = id + 1;
                ((BaseEntity) entity).setId(nextId);
                idMap.put(key, nextId);
            }
        };
    }

//    @Bean
    public CommandLineRunner commandLineRunner(OwnerRepository ownerRepository, PetTypeRepository petTypeRepository) {
        return (args) -> {

            // types
            PetType dogType = new PetType();
            dogType.setName("dog");

            PetType catType = new PetType();
            dogType.setName("cat");

            petTypeRepository.saveAll(List.of(dogType, catType));

            // pets
            Pet dog = new Pet();
            dog.setName("dag");
            dog.setBirthDate(LocalDate.of(2001, 12, 12));
            dog.setTypeId(AggregateReference.to(dogType.getId()));

            Pet cat = new Pet();
            cat.setName("cot");
            cat.setBirthDate(LocalDate.of(2000, 11, 11));
            cat.setTypeId(AggregateReference.to(catType.getId()));

//            petRepository.saveAll(List.of(dog, cat));

            // owner
            Owner owner = new Owner();
            owner.setFirstName("Den");
            owner.setLastName("Ned");
            owner.setAddress("LA");
            owner.setPets(List.of(dog, cat));
            ownerRepository.save(owner);
        };
    }
}
