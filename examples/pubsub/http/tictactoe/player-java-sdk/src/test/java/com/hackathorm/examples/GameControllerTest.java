package com.hackathorm.examples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReplyWithPositions() throws Exception {
        mockMvc.perform(asyncDispatch(
                mockMvc.perform(
                        post(GameController.MOVE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"board\":[[\"X\",\"O\",\"X\"],[null,null,null],[null,null,null]],\"playerMark\":\"X\"}"))
                        .andReturn()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.x", lessThanOrEqualTo(2)))
                .andExpect(jsonPath("$.y", lessThanOrEqualTo(2)));
    }

    @Test(expected = NestedServletException.class)
    public void shouldReplyWithErrorOnEmptyBoard() throws Exception {
        mockMvc.perform(asyncDispatch(
                mockMvc.perform(
                        post(GameController.MOVE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"board\":[],\"playerMark\":\"X\"}"))
                        .andReturn()));
    }

    @Test(expected = NestedServletException.class)
    public void shouldReplyWithErrorOnMissingEmptySpaceOnBoard() throws Exception {
        mockMvc.perform(asyncDispatch(
                mockMvc.perform(
                        post(GameController.MOVE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"board\":[[\"X\",\"O\",\"X\"],[\"X\",\"X\",\"X\"],[\"X\",\"X\",\"X\"]],\"playerMark\":\"X\"}"))
                        .andReturn()));
    }
}