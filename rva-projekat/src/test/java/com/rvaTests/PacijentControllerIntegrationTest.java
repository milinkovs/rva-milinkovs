package com.rvaTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

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

import com.bolnica.project.models.Pacijent;
import com.bolnica.project.models.Dijagnoza;
import com.bolnica.project.models.Odeljenje;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacijentControllerIntegrationTest {

    @Autowired
    TestRestTemplate template;

    int getHighestId() {
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijenti", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Pacijent>>() {});
        int highestId = 0;
        if (response.getBody() != null) {
            for (Pacijent p : response.getBody()) {
                if (p.getId() > highestId) highestId = p.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllPacijenti() {
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijenti", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Pacijent>>() {});

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetPacijentById() {
        int id = 1;
        ResponseEntity<Pacijent> response =
                template.exchange("/pacijent/" + id, HttpMethod.GET, null, Pacijent.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(3)
    void testGetPacijentByOsiguranje() {
        boolean osiguranje = true;
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijent/osiguranje/" + osiguranje, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Pacijent>>() {});

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        for (Pacijent p : response.getBody()) {
            assertTrue(p.isZdrOsiguranje());
        }
    }

    @Test
    @Order(4)
    void testGetPacijentiByDijagnoza() {
        Dijagnoza dijagnoza = new Dijagnoza();
        dijagnoza.setId(1);

        HttpEntity<Dijagnoza> entity = new HttpEntity<>(dijagnoza);
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijent/po-dijagnozi", HttpMethod.POST, entity,
                        new ParameterizedTypeReference<List<Pacijent>>() {});

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    void testGetPacijentiByOdeljenje() {
        Odeljenje odeljenje = new Odeljenje();
        odeljenje.setId(1);

        HttpEntity<Odeljenje> entity = new HttpEntity<>(odeljenje);
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijent/po-odeljenju", HttpMethod.POST, entity,
                        new ParameterizedTypeReference<List<Pacijent>>() {});

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    void testCreatePacijent() {
        Pacijent pacijent = new Pacijent();
        pacijent.setIme("Test");
        pacijent.setZdrOsiguranje(true);

        HttpEntity<Pacijent> entity = new HttpEntity<>(pacijent);
        ResponseEntity<String> response =
                template.exchange("/pacijent", HttpMethod.POST, entity, String.class);

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().contains("created"));
    }

    @Test
    @Order(7)
    void testUpdatePacijent() {
        int highestId = getHighestId();

        Pacijent pacijent = new Pacijent();
        pacijent.setIme("Izmenjen");
        pacijent.setZdrOsiguranje(false);

        HttpEntity<Pacijent> entity = new HttpEntity<>(pacijent);
        ResponseEntity<String> response =
                template.exchange("/pacijent/" + highestId, HttpMethod.PUT, entity, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("updated"));
    }

    @Test
    @Order(8)
    void testDeletePacijent() {
        int highestId = getHighestId();

        ResponseEntity<String> response =
                template.exchange("/pacijent/" + highestId, HttpMethod.DELETE, null, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("deleted"));
    }
}