package org.tsapko;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tsapko.dao.ShipmentRepository;
import org.tsapko.entity.Shipment;

import javax.validation.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ShipmentRepositoryTest {

    @Autowired
    private ShipmentRepository repository;

    @Test
    public void whenFindByBarcode_thenReturnShipment() {
        Shipment given = new Shipment("SB8517783557620NZ");
        repository.save(given);

        Shipment found = repository.findByBarcode(given.getBarcode());

        assertThat(found.getBarcode()).isEqualTo(given.getBarcode());

    }

    @Test(expected = ValidationException.class)
    public void whenSaveLOngBarcode_thenThrowException() {
        Shipment given = new Shipment("SSSSSSSSSSSSB8517783557620NZZZZZZZZZZZZ");
        repository.save(given);

    }

}
