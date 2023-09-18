package com.crud.tasks.trello.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.TrelloBoardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Test
    public void shouldFetchEmptyListOfTrelloBoards2() {
        //GIVEN
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://example.com/api");
        when(trelloConfig.getTrelloUsername()).thenReturn("exampleUser");
        when(trelloConfig.getTrelloAppKey()).thenReturn("apiKey");
        when(trelloConfig.getTrelloToken()).thenReturn("apiToken");

        when(restTemplate.getForObject(any(), eq(TrelloBoardDto[].class)))
                .thenThrow(new RestClientException("Simulated RestClientException"));

        // WHEN
        List<TrelloBoardDto> boards = trelloClient.getTrelloBoards();

        // THEN
        assertEquals(Collections.emptyList(), boards);
    }
}