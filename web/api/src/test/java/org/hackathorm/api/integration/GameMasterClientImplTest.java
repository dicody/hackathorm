package org.hackathorm.api.integration;

import io.dapr.client.DaprClient;
import org.hackathorm.api.domain.gamemaster.GameRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameMasterClientImplTest {

    @Mock
    private DaprClient daprClient;

    @InjectMocks
    private GameMasterClientImpl masterClient;

    @Test
    void publishGame() {
        GameRequest request = new GameRequest();
        when(daprClient.publishEvent(anyString(), anyString(), eq(request))).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), masterClient.publishGame(request));
        verify(daprClient).publishEvent(anyString(), anyString(), eq(request));
        verifyNoMoreInteractions(daprClient);
    }
}