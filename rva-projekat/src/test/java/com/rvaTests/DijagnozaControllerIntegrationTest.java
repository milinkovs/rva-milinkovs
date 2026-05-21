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
import org.springframework.web.client.RestClientException;

import com.bolnica.project.models.Dijagnoza;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DijagnozaControllerIntegrationTest {

    @Autowired
    TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Dijagnoza>> response =
                template.exchange("/dijagnoze", HttpMethod.GET, null, new ParameterizedTypeReference<List<Dijagnoza>>() {});
        
        if (response.getBody().isEmpty()) return highestId;
        for (Dijagnoza d : response.getBody()) {
            if (highestId < d.getId()) {
                highestId = d.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllDijagnoze() {
        ResponseEntity<List<Dijagnoza>> response =
                template.exchange("/dijagnoze", HttpMethod.GET, null, new ParameterizedTypeReference<List<Dijagnoza>>() {});

        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetDijagnozeByOznaka() {
        String oznaka = "A"; 
        ResponseEntity<List<Dijagnoza>> response =
                template.exchange("/dijagnoza/oznaka/" + oznaka, HttpMethod.GET, null, new ParameterizedTypeReference<List<Dijagnoza>>() {});

        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
        assertTrue(response.getBody().get(0).getOznaka().contains(oznaka));
    }

    @Test
    @Order(3)
    void testGetDijagnozaById() {
        int id = 1; // pretpostavi da postoji dijagnoza sa ID = 1
        ResponseEntity<Dijagnoza> response = null;
        try {
            response = template.exchange("/dijagnoza/" + id, HttpMethod.GET, null, Dijagnoza.class);
        } catch (RestClientException e) {
            fail("No data found with primary key: " + id);
        }

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testCreateDijagnoza() {
        Dijagnoza dijagnoza = new Dijagnoza();
        dijagnoza.setOznaka("TEST-OZN");
        dijagnoza.setOpis("Test opis dijagnoze");

        HttpEntity<Dijagnoza> entity = new HttpEntity<Dijagnoza>(dijagnoza);

        ResponseEntity<String> response =
                template.exchange("/dijagnoza", HttpMethod.POST, entity, String.class);

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().contains("successfully created"));
    }

    @Test
    @Order(5)
    void testUpdateDijagnoza() {
        int highestId = getHighestId();
        Dijagnoza dijagnoza = new Dijagnoza();
        dijagnoza.setOznaka("IZMENJENA-OZN");
        dijagnoza.setOpis("Izmenjen opis dijagnoze");

        HttpEntity<Dijagnoza> entity = new HttpEntity<Dijagnoza>(dijagnoza);
        ResponseEntity<String> response =
                template.exchange("/dijagnoza/" + highestId, HttpMethod.PUT, entity, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("successfully updated"));
    }

    @Test
    @Order(6)
    void testDeleteDijagnoza() {
        int highestId = getHighestId();
        ResponseEntity<String> response =
                template.exchange("/dijagnoza/" + highestId, HttpMethod.DELETE, null, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("successfully deleted"));

        ResponseEntity<String> responseGet =
                template.exchange("/dijagnoza/" + highestId, HttpMethod.GET, null, String.class);

        assertEquals(404, responseGet.getStatusCode().value());
        assertTrue(responseGet.getBody().contains("does not exist"));
    }
}
