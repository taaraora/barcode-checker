package org.tsapko;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.tsapko.service.ShipmentService;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BarcodeCheckerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class BarcodeCheckerApplicationTests {

    private final String BARCODE = "SB8517783557620NZ";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ShipmentService shipmentService;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {

        shipmentService.saveShipment(BARCODE);

        mvc.perform(MockMvcRequestBuilders.get("/shipment/" + BARCODE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.barcode", equalTo(BARCODE)));
    }

    @Test
    public void contextLoads() {
    }

}
