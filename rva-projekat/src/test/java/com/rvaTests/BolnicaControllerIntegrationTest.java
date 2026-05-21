package com.rvaTests;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bolnica.project.BolnicaSpringBootApplication;
import com.bolnica.project.models.Bolnica;

@SpringBootTest( classes = BolnicaSpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BolnicaControllerIntegrationTest {

    @Autowired
    TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Bolnica>> response =
                template.exchange("/bolnice", HttpMethod.GET, null, new ParameterizedTypeReference<List<Bolnica>>() {});
        if (response.getBody() == null || response.getBody().isEmpty()) return highestId;
        for (Bolnica b : response.getBody()) {
            if (highestId < b.getId()) {
                highestId = b.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllBolnice() {
        ResponseEntity<List<Bolnica>> response =
                template.exchange("/bolnice", HttpMethod.GET, null, new ParameterizedTypeReference<List<Bolnica>>() {});

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetBolnicaByNaziv() {
        String naziv = "KBC";
        ResponseEntity<List<Bolnica>> response =
                template.exchange("/bolnica/naziv/" + naziv, HttpMethod.GET, null, new ParameterizedTypeReference<List<Bolnica>>() {});

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertTrue(response.getBody().get(0).getNaziv().toLowerCase().contains(naziv.toLowerCase()));
    }

    @Test
    @Order(3)
    void testGetBolnicaById() {
        int id = 1; 
        ResponseEntity<Bolnica> response = template.exchange("/bolnica/" + id, HttpMethod.GET, null, Bolnica.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testCreateBolnica() {
        Bolnica bolnica = new Bolnica();
        int highestId = getHighestId() + 1;
        bolnica.setId(highestId);
        bolnica.setNaziv("Test Bolnica");
        bolnica.setAdresa("Test Adresa");

        HttpEntity<Bolnica> entity = new HttpEntity<>(bolnica);
        ResponseEntity<String> response =
                template.exchange("/bolnica", HttpMethod.POST, entity, String.class);

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().contains("has been created successfully!"));
    }

    @Test
    @Order(5)
    void testUpdateBolnica() {
        int highestId = getHighestId();
        Bolnica updated = new Bolnica();
        updated.setId(highestId);
        updated.setNaziv("Izmenjena Bolnica");
        updated.setAdresa("Nova Adresa");

        HttpEntity<Bolnica> entity = new HttpEntity<>(updated);
        ResponseEntity<String> response =
                template.exchange("/bolnica/" + highestId, HttpMethod.PUT, entity, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("has been updated successfully"));
    }
}
