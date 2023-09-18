package com.crud.tasks.trello.validator;


import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class TrelloValidatorTest {

    @InjectMocks
    TrelloValidator trelloValidator;

    @Mock
    Logger logger;

    @Test
    public void testLoggerMsgInValidateCard() {
        //GIVEN
        TrelloCard trelloCard = new TrelloCard("CardName", "CardDescription", "CardPos", "ListId");

        String loggerMsg = "Seems that my application is used in proper way.";

        //WHEN
        trelloValidator.validateCard(trelloCard);

        //THEN
        System.out.println("LOGGER should be: " + loggerMsg);
    }

    @Test
    public void testLoggerMsgInValidateCardWithTestKeyword() {
        //GIVEN
        TrelloCard trelloCard = new TrelloCard("testCase", "CardDescription", "CardPos", "ListId");

        String loggerMsg = "Someone is testing my application!";

        //WHEN
        trelloValidator.validateCard(trelloCard);

        //THEN
        System.out.println("LOGGER should be: " + loggerMsg);
    }

    @Test
    public void shouldReturnListOfTrelloBoards() {
        //GIVEN
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("Board ID", "Board1", List.of()));
        trelloBoards.add(new TrelloBoard("Board ID", "Board2", List.of()));
        trelloBoards.add(new TrelloBoard("Board ID", "Test board", List.of()));

        //WHEN
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //THEN
        assertEquals(3, filteredBoards.size());
    }

    @Test
    public void shouldReturnListOfTrelloBoardsFilteredByName() {
        //GIVEN
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("Board ID", "Board1", List.of()));
        trelloBoards.add(new TrelloBoard("Board ID", "Board2", List.of()));
        trelloBoards.add(new TrelloBoard("Board ID", "Test", List.of()));

        //WHEN
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //THEN
        assertEquals(2, filteredBoards.size());
    }

    @Test
    public void shouldReturnEmptyListOfTrelloBoards() {
        //GIVEN
        List<TrelloBoard> trelloBoards = new ArrayList<>();

        //WHEN
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //THEN
        assertNotNull(filteredBoards);
        assertEquals(0, filteredBoards.size());
    }
}