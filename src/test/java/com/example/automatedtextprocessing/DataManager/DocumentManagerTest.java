package com.example.automatedtextprocessing.DataManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentManagerTest {
    private final DocumentManager database = new DocumentManager();

    @BeforeEach
    void setup(){
        Document doc1 = new Document("1","Doc-1","Hello world");
        Document doc2 = new Document("2","Doc-2","Hello earth");
        Document doc3 = new Document("3","Doc-3","Hello Ghana");

        database.addDocument(doc1);
        database.addDocument(doc2);
        database.addDocument(doc3);
    }

    @Test
    void getDocument() {
        Document doc = database.getDocument("1");

        assertEquals("Doc-1", doc.getTitle());
        assertEquals("Hello world", doc.getContent());
    }

    @Test
    void addDocument() {
        Document testDoc = new Document("4","Doc-5","Test Doc");
        database.addDocument(testDoc);

        assertEquals(4,database.getAllDocuments().size());
    }

    @Test
    void getAllDocuments() {
        assertEquals(3,database.getAllDocuments().size());
    }

    @Test
    void updateDocument() {
        database.updateDocument("1","Updated-Doc","Updated Content");

        assertEquals("Updated-Doc", database.getDocument("1").getTitle());
    }

    @Test
    void deleteDocument() {
        database.deleteDocument("1");

        assertEquals(2, database.getAllDocuments().size());
    }
}