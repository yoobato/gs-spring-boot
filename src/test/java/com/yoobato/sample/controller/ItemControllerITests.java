package com.yoobato.sample.controller;

import com.yoobato.sample.entity.Item;
import com.yoobato.sample.repositoy.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ItemControllerITests {

    // MUST be bigger than 1
    private static final int TEST_ITEM_COUNT = 3;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void setup() {
        // Create items before each test
        for (int i = 1; i <= TEST_ITEM_COUNT; i++) {
            Item item = new Item();
            item.setName("TestItem" + i);
            item.setOwner("TestOwner" + i);
            itemRepository.save(item);
        }
    }

    @AfterEach
    public void teardown() {
        itemRepository.deleteAll();
    }

    @Test
    public void getAllItems_OK() throws Exception {
        this.mockMvc.perform(get("/items"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(TEST_ITEM_COUNT))
                .andExpect(jsonPath("$[0].name").value("TestItem1"))
                .andExpect(jsonPath("$[0].owner").value("TestOwner1"))
                .andExpect(jsonPath("$[1].name").value("TestItem2"))
                .andExpect(jsonPath("$[1].owner").value("TestOwner2"));
    }

    @Test
    public void getItem_OK() throws Exception {
        Item item = itemRepository.findAll().getFirst();

        this.mockMvc.perform(get("/items/" + item.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value("TestItem1"))
                .andExpect(jsonPath("$.owner").value("TestOwner1"));
    }

    @Test
    public void getItem_NotFound() throws Exception {
        int fakeId = Integer.MAX_VALUE;

        this.mockMvc.perform(get("/items/" + fakeId))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string("Item not found"));
    }

    @Test
    public void createItem_OK() throws Exception {
        String requestBody = "{\"name\": \"TestItem4\", \"owner\": \"TestOwner4\"}";

        this.mockMvc.perform(post("/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestItem4"))
                .andExpect(jsonPath("$.owner").value("TestOwner4"));
    }

    @Test
    public void updateItem_OK() throws Exception {
        Item item = itemRepository.findAll().getFirst();
        String requestBody = "{\"name\": \"TestItem10\", \"owner\": \"TestOwner10\"}";

        this.mockMvc.perform(put("/items/" + item.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value("TestItem10"))
                .andExpect(jsonPath("$.owner").value("TestOwner10"));
    }

    @Test
    public void deleteItem_OK() throws Exception {
        Item item = itemRepository.findAll().getFirst();
        this.mockMvc.perform(delete("/items/" + item.getId()))
                .andDo(print()).andExpect(status().isOk());
    }
}
