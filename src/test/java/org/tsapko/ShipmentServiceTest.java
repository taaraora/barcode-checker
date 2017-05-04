package org.tsapko;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.tsapko.dao.ShipmentRepository;
import org.tsapko.entity.Shipment;
import org.tsapko.exception.ShipmentNotFoundException;
import org.tsapko.service.ShipmentService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ShipmentServiceTest {

    private final String BARCODE = "SB8517783557620NZ";

    @TestConfiguration
    static class ShipmentServiceTstContecstConfiguration {

        @Bean
        public ShipmentService shipmentService() {
            return new ShipmentService();
        }

        @Bean
        BloomFilter<String> createBloomFilter() {
            BloomFilter<String> bf = new FilterBuilder(1000, 0.01).buildBloomFilter();
            return bf;
        }
    }

    @Autowired
    ShipmentService instance;

    @MockBean
    private ShipmentRepository repository;

    //Let's insert shipment directly in DB aside from Bloom filter
    @Before
    public void setUp() {
        Shipment shipment = new Shipment(BARCODE);
        shipment.setId(1L);

        Mockito.when(repository.findByBarcode(shipment.getBarcode()))
                .thenReturn(shipment);
    }

    @Test(expected = ShipmentNotFoundException.class)
    public void whenBarCodeWasInsertedDirectkyInDB_thenBloomFilterGivesFalseNegative() {
        ;
        Shipment found = instance.getShipment(BARCODE);
        System.out.println(found);

        assertThat(found.getBarcode())
                .isEqualTo(BARCODE);
    }

    @Test
    public void whenBarCodeWasInsertedUsingService_thenBloomFilterGivesPositive() {
        instance.saveShipment(BARCODE);

        Shipment found = instance.getShipment(BARCODE);
        System.out.println(found);

        assertThat(found.getBarcode())
                .isEqualTo(BARCODE);
    }

}
