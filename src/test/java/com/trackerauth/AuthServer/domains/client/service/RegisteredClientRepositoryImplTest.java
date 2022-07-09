package com.trackerauth.AuthServer.domains.client.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class RegisteredClientRepositoryImplTest {

    @Mock
    ClientService service;
    @InjectMocks
    RegisteredClientRepositoryImpl victim;

    @Test
    void save() {
    }

    @Test
    void findById_whenServiceReturnsNull_thenReturnsNull() {
        String id = "id";
        when(service.findById(id)).thenReturn(null);

        RegisteredClient result = victim.findById(id);

        assertNull(result);
        verify(service, times(1)).findById(id);
    }

    @Test
    void findById_whenServiceReturnsClient_thenReturnsRegisteredClient() {

    }

    @Test
    void findByClientId() {
    }
}