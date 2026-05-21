package com.rvaTests;

import static org.junit.jupiter.api.Assertions.*;

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

import com.bolnica.project.models.Odeljenje;
import com.bolnica.project.models.Bolnica;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdeljenjeControllerIntegrationTest {

    @Autowired
    TestRestTemplate template;
    
    int getHighestId() {
        ResponseEntity<List<Odeljenje>> response = template.exchange(
                "/odeljenja",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Odeljenje>>() {}
        );

        int highestId = 0;
        if (response.getBody() != null) {
            for (Odeljenje o : response.getBody()) {
                if (o.getId() > highestId)
                    highestId = o.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllOdeljenja() {
        ResponseEntity<List<Odeljenje>> response = template.exchange(
                "/odeljenja",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Odeljenje>>() {}
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetOdeljenjeById() {
        int id = 1;
        ResponseEntity<Odeljenje> response = template.exchange(
                "/odeljenje/" + id,
                HttpMethod.GET,
                null,
                Odeljenje.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(3)
    void testGetOdeljenjeByNaziv() {
        String naziv = "Hirurgija";
        ResponseEntity<List<Odeljenje>> response = template.exchange(
                "/odeljenje/naziv/" + naziv,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Odeljenje>>() {}
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(naziv, response.getBody().get(0).getNaziv());
    }

    @Test
    @Order(4)
    void testGetOdeljenjaByBolnica() {
        Bolnica bolnica = new Bolnica();
        bolnica.setId(1);

        HttpEntity<Bolnica> entity = new HttpEntity<>(bolnica);

        ResponseEntity<List<Odeljenje>> response = template.exchange(
                "/odeljenja/po-bolnici",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<Odeljenje>>() {}
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().stream().allMatch(o -> o.getBolnica().getId() == 1));
    }


    @Test
    @Order(5)
    void testCreateOdeljenje() {
        Odeljenje odeljenje = new Odeljenje();
        odeljenje.setNaziv("Kardiologija");
        odeljenje.setLokacija("Sprat 4");

        Bolnica bolnica = new Bolnica();
        bolnica.setId(2);
        odeljenje.setBolnica(bolnica);

        HttpEntity<Odeljenje> entity = new HttpEntity<>(odeljenje);

        ResponseEntity<String> response = template.exchange(
                "/odeljenje",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Odeljenje successfully created"));
    }

    @Test
    @Order(6)
    void testUpdateOdeljenje() {
        int id = getHighestId();

        Odeljenje updated = new Odeljenje();
        updated.setNaziv("Izmenjeno Odeljenje");
        updated.setLokacija("Sprat X");

        Bolnica bolnica = new Bolnica();
        bolnica.setId(1);
        updated.setBolnica(bolnica);

        HttpEntity<Odeljenje> entity = new HttpEntity<>(updated);

        ResponseEntity<String> response = template.exchange(
                "/odeljenje/" + id,
                HttpMethod.PUT,
                entity,
                String.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("successfully updated"));
    }

    @Test
    @Order(7)
    void testDeleteOdeljenje() {
        int id = getHighestId();

        ResponseEntity<String> response = template.exchange(
                "/odeljenje/" + id,
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("successfully deleted"));
    }
}
