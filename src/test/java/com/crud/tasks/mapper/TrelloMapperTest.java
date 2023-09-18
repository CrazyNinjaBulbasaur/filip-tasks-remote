package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloMapperTest {

    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    public void testMapToBoardsWithEmptyTrelloList() {
        //GIVEN
        List<TrelloListDto> listDtos = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("sampleId", "Sample Board", listDtos);

        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);

        //WHEN
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtoList);

        //THEN
        assertEquals(1, trelloBoards.size());
        TrelloBoard mappedBoard = trelloBoards.get(0);
        assertEquals("sampleId", mappedBoard.getId());
        assertEquals("Sample Board", mappedBoard.getName());
        assertEquals(0, mappedBoard.getLists().size());
    }

    @Test
    public void testMapToBoards() {
        //GIVEN
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "List 1", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "List 2", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("3", "List 3", false);
        List<TrelloListDto> listDtos = List.of(trelloListDto1, trelloListDto2, trelloListDto3);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto();
        trelloBoardDto.setId("sampleId");
        trelloBoardDto.setName("Sample Board");
        trelloBoardDto.setLists(listDtos);

        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);

        //WHEN
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtoList);

        //THEN
        assertEquals(1, trelloBoards.size());
        TrelloBoard mappedBoard = trelloBoards.get(0);
        assertEquals("sampleId", mappedBoard.getId());
        assertEquals("Sample Board", mappedBoard.getName());
        assertEquals(3, mappedBoard.getLists().size());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloList> lists = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("sampleId", "Sample Board", lists);

        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard);

        //WHEN
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoardList);

        //THEN
        assertEquals(1, trelloBoardDtos.size());
        TrelloBoardDto mappedDto = trelloBoardDtos.get(0);
        assertEquals("sampleId", mappedDto.getId());
        assertEquals("Sample Board", mappedDto.getName());
        assertEquals(0, mappedDto.getLists().size());
    }

    @Test
    public void testMapToList() {
        //GIVEN
        TrelloListDto trelloListDto = new TrelloListDto();
        trelloListDto.setId("sampleId");
        trelloListDto.setName("Sample List");
        trelloListDto.setClosed(true);

        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListDto);

        //WHEN
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtoList);

        //THEN
        assertEquals(1, trelloLists.size());
        TrelloList mappedList = trelloLists.get(0);
        assertEquals("sampleId", mappedList.getId());
        assertEquals("Sample List", mappedList.getName());
        assertTrue(mappedList.isClosed());
    }

    @Test
    public void testMapToListDto() {
        //GIVEN
        TrelloList trelloList = new TrelloList("sampleId", "Sample List", false);

        List<TrelloList> trelloListList = new ArrayList<>();
        trelloListList.add(trelloList);

        //WHEN
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloListList);

        //THEN
        assertEquals(1, trelloListDtos.size());
        TrelloListDto mappedDto = trelloListDtos.get(0);
        assertEquals("sampleId", mappedDto.getId());
        assertEquals("Sample List", mappedDto.getName());
        assertFalse(mappedDto.isClosed());
    }

    @Test
    public void testMapToCard() {
        //GIVEN
        TrelloCardDto trelloCardDto = new TrelloCardDto();
        trelloCardDto.setName("Card Name");
        trelloCardDto.setDescription("Card Description");
        trelloCardDto.setPos("Card Position");
        trelloCardDto.setListId("List ID");

        //WHEN
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //THEN
        assertEquals("Card Name", trelloCard.getName());
        assertEquals("Card Description", trelloCard.getDescription());
        assertEquals("Card Position", trelloCard.getPos());
        assertEquals("List ID", trelloCard.getListId());
    }

    @Test
    public void testMapToCardDto() {
        // GIVEN
        TrelloCard trelloCard = new TrelloCard("Card Name", "Card Description", "Card Position", "List ID");

        // WHEN
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        // THEN
        assertEquals("Card Name", trelloCardDto.getName());
        assertEquals("Card Description", trelloCardDto.getDescription());
        assertEquals("Card Position", trelloCardDto.getPos());
        assertEquals("List ID", trelloCardDto.getListId());
    }


}