package io.amplicode;

import io.amplicode.api.dto.OwnerFilter;
import io.amplicode.model.Owner;
import io.amplicode.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataJdbcOwnerServiceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void findAllQueryAndSort() {
        OwnerFilter filter = new OwnerFilter("St.", null, null);
        Sort sort = Sort.by(Sort.Order.by("city"));
        PageRequest pageable = PageRequest.of(0, 20, sort);
        List<Owner> owners = ownerRepository.findAll(filter, pageable).toList();

        assertEquals(5, owners.size());
        assertEquals("George", owners.get(0).getFirstName());
        assertEquals("Maria", owners.get(1).getFirstName());
        assertEquals("Eduardo", owners.get(2).getFirstName());
        assertEquals("Jean", owners.get(3).getFirstName());
        assertEquals("Harold", owners.get(4).getFirstName());
    }

    @Test
    void findAllFilterByQueryAll() {
        OwnerFilter filter = new OwnerFilter(null, "mad", "6085559435");
        PageRequest pageable = PageRequest.of(0, 20);
        List<Owner> owners = ownerRepository.findAll(filter, pageable).toList();

        assertEquals(1, owners.size());
        assertEquals("David", owners.get(0).getFirstName());
    }

    @Test
    void findAllSize5Page2SortName() {
        OwnerFilter filter = new OwnerFilter(null, null, null);
        PageRequest pageable = PageRequest.of(1, 5, Sort.by("firstName"));
        List<Owner> owners = ownerRepository.findAll(filter, pageable).toList();

        assertEquals(5, owners.size());
        assertEquals("Harold", owners.get(0).getFirstName());
        assertEquals("Jean", owners.get(1).getFirstName());
        assertEquals("Jeff", owners.get(2).getFirstName());
        assertEquals("Maria", owners.get(3).getFirstName());
        assertEquals("Peter", owners.get(4).getFirstName());
    }

    @Test
    void findAllById() {
        OwnerFilter filter = new OwnerFilter(null, null, null);
        PageRequest pageable = PageRequest.of(1, 5, Sort.by("firstName"));
        List<Owner> owners = ownerRepository.findAll(filter, pageable).toList();

        assertEquals(5, owners.size());
        assertEquals("Harold", owners.get(0).getFirstName());
        assertEquals("Jean", owners.get(1).getFirstName());
        assertEquals("Jeff", owners.get(2).getFirstName());
        assertEquals("Maria", owners.get(3).getFirstName());
        assertEquals("Peter", owners.get(4).getFirstName());
    }


    @Test
    void getOne() {
        Owner owner = ownerRepository.findById(3L).orElse(null);

        assertNotNull(owner);
        assertEquals("Eduardo", owner.getFirstName());
        assertEquals("Rodriquez", owner.getLastName());
        assertEquals("2693 Commerce St.", owner.getAddress());
        assertEquals("McFarland", owner.getCity());
        assertEquals("6085558763", owner.getTelephone());
    }

    @Test
    void getOneNotFound() {
        Owner owner = ownerRepository.findById(Long.MAX_VALUE).orElse(null);
        assertNull(owner);
    }

    @Test
    void getMany() {
        List<Owner> owners = ownerRepository.findAllById(List.of(1L, 3L, 5L, 7L));
        assertEquals(4, owners.size());
        assertEquals("George", owners.get(0).getFirstName());
        assertEquals("Eduardo", owners.get(1).getFirstName());
        assertEquals("Peter", owners.get(2).getFirstName());
        assertEquals("Jeff", owners.get(3).getFirstName());
    }

    @Test
    public void createAndDelete() {
        Owner owner = new Owner();
        owner.setFirstName("Ivan");
        owner.setLastName("Ivanov");
        owner.setAddress("Gastelo 43a");
        owner.setCity("Samara");
        owner.setTelephone("9271111111");

        Owner savedOwner = ownerRepository.save(owner);
        assertNotNull(savedOwner);
        assertEquals("Ivan", savedOwner.getFirstName());
        assertEquals("Ivanov", savedOwner.getLastName());
        assertEquals("Gastelo 43a", savedOwner.getAddress());
        assertEquals("Samara", savedOwner.getCity());
        assertEquals("9271111111", savedOwner.getTelephone());

        ownerRepository.deleteById(savedOwner.getId());
    }

    @Test
    public void purePut() {
        Owner owner = new Owner();
        owner.setFirstName("Alex");
        owner.setLastName("Alexov");
        owner.setAddress("Gastelo");
        owner.setCity("Samara");
        owner.setTelephone("9271112233");

        Owner savedOwner = ownerRepository.save(owner);

        savedOwner.setLastName("Ivanov");
        savedOwner.setAddress("Gastelo 43a");
        ownerRepository.save(savedOwner);

        ownerRepository.deleteById(savedOwner.getId());
    }

    @Test
    public void deleteMany() {
        Owner alex = new Owner();
        alex.setFirstName("Alex");
        alex.setLastName("Alexov");
        alex.setAddress("Gastelo");
        alex.setCity("Samara");
        alex.setTelephone("9271112233");
        Owner savedAlex = ownerRepository.save(alex);

        Owner ivan = new Owner();
        ivan.setFirstName("Ivan");
        ivan.setLastName("Ivanov");
        ivan.setAddress("My Address");
        ivan.setCity("My City");
        ivan.setTelephone("0001112233");
        Owner savedIvan = ownerRepository.save(ivan);

        List<Long> toDeleteIds = List.of(savedAlex.getId(), savedIvan.getId());
        ownerRepository.deleteAllById(toDeleteIds);

        List<Owner> owners = ownerRepository.findAllById(toDeleteIds);
        assertTrue(owners.isEmpty());
    }
}
