package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private AdminConfig adminConfig;

    @Test
    public void shouldFetchTrelloBoards() {
        //GIVEN
        List<TrelloBoardDto> expectedBoards = new ArrayList<>();
        expectedBoards.add(new TrelloBoardDto("custom Id", "custom name", new ArrayList<>()));

        when(trelloClient.getTrelloBoards()).thenReturn(expectedBoards);

        //WHEN
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();

        //THEN
        assertEquals(expectedBoards, result);
    }



    @Test
    public void testCreateTrelloCard() {
        // GIVEN
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card Name", "Card Description", "", "List ID");
        CreatedTrelloCardDto createdCardDto = new CreatedTrelloCardDto("123", "Card Name", "shortUrl");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdCardDto);

        //WHEN
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);

        //THEN
        assertEquals(createdCardDto, result);
        verify(simpleEmailService, times(1)).send(any(Mail.class));
    }
}