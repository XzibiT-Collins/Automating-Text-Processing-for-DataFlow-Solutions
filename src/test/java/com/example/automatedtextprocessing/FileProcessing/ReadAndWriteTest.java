package com.example.automatedtextprocessing.FileProcessing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ReadAndWriteTest {
    //logger
    private final Logger logger = Logger.getLogger(ReadAndWriteTest.class.getName());
    //file path
    final String filePath = "src/test/java/com/example/automatedtextprocessing/TestFile/Test.txt";

    @BeforeEach
    void setUp() throws IOException{
        //test content
        List<String> content = List.of(
                "Hello, my name is Collins",
                "I am 25 years old",
                "I am a backend Engineer at Amalitech,",
                "I joined the GTP in 04-01-2025",
                "Thank you."
        );

        //write content to file
        try{
            ReadAndWrite.writeFile(filePath,content);
        }catch (IOException e){
            logger.info("Error writing to file: " + e.getMessage());
        }
    }


    @AfterEach
    void teardown() throws IOException{
        Files.deleteIfExists(Path.of(filePath)); // delete file
    }

    @Test
    void readFile() throws IOException {
        //Try read file
        List<String> lines = ReadAndWrite.readFile(filePath);

        //assertions
        assertTrue(Files.exists(Path.of(filePath))); //checks if file exists
        assertEquals(5,lines.size()); //check if lines match
        assertEquals("Hello, my name is Collins", lines.getFirst());
        assertEquals("Thank you.", lines.getLast());

    }

    @Test
    void readFile_shouldThroeIOErrorForInvalidFile()throws IOException{
        //assertions
        assertThrows(IOException.class,()->{
            ReadAndWrite.readFile("file.txt");
        });
    }

    @Test
    void writeFile() throws IOException {
        //add a new line to the file
        try{
            ReadAndWrite.writeFile(filePath, List.of("Added new line for Testing Purposes.."));
        }catch (IOException e){
            logger.info("Error occurred while writing to file: "+ e.getMessage());
        }

        List<String> lines = null;

        //Read file
        try{
            lines = ReadAndWrite.readFile(filePath);
        }catch (IOException e){
            logger.info("Error reading file: "+ e.getMessage());
        }

        assertEquals(1,lines.size());
        assertEquals("Added new line for Testing Purposes..", lines.getFirst());
    }
}