package com.example.automatedtextprocessing.DataManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.print.Doc;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    private final Document newDoc = new Document("1","myDoc","Hello world");


    @Test
    void getId() {
        assertEquals("1", newDoc.getId());
    }

    @Test
    void getTitle() {
        assertEquals("myDoc",newDoc.getTitle());
    }

    @Test
    void getContent() {
        assertEquals("Hello world",newDoc.getContent());
    }

    @Test
    void setTitle() {
        newDoc.setTitle("NewTitle");

        assertEquals("NewTitle", newDoc.getTitle());
    }

    @Test
    void setTitle_shouldNotBeEmpty(){
        assertThrows(IllegalArgumentException.class,() -> {
            newDoc.setTitle("");
        });
    }

    @Test
    void setContent() {
        newDoc.setContent("Changed content");

        assertEquals("Changed content", newDoc.getContent());
    }
}