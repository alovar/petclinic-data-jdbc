package io.amplicode;

import io.amplicode.api.dto.OwnerFilter;
import io.amplicode.model.Owner;
import io.amplicode.repository.OwnerRepository;
import liquibase.parser.core.xml.XMLChangeLogSAXParser;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.jdbc.core.mapping.schema.DefaultSqlTypeMapping;
import org.springframework.data.jdbc.core.mapping.schema.LiquibaseChangeSetWriter;
import org.springframework.data.jdbc.core.mapping.schema.SqlTypeMapping;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataJdbcOwnerServiceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RelationalMappingContext context;

    @Test
    void test() throws IOException {
        context.setInitialEntitySet(Collections.singleton(Owner.class));
        LiquibaseChangeSetWriter writer = new LiquibaseChangeSetWriter(context);
        writer.setChangeLogParser(new XMLChangeLogSAXParser());
        writer.setChangeLogSerializer(new XMLChangeLogSerializer());

        writer.setSqlTypeMapping(((SqlTypeMapping) property -> {
            if (property.getType().getName().equals("org.springframework.data.jdbc.core.mapping.AggregateReference")) {
                return "INT";
            }
            return null;
        }).and(new DefaultSqlTypeMapping()));

        writer.writeChangeSet(new FileSystemResource("cs-minimum.xml"));
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
