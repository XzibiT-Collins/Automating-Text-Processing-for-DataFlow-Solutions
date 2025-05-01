package com.example.automatedtextprocessing.FileAnalysis;

import com.example.automatedtextprocessing.FileProcessing.ReadAndWrite;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class StreamProcessingTest {

    //logger
    private final Logger logger = Logger.getLogger(StreamProcessingTest.class.getName());
    //file path
    final String filePath = "src/test/java/com/example/automatedtextprocessing/TestFile/Test.txt";


    @Test
    void wordFrequency() throws IOException {
        ReadAndWrite.writeFile(filePath,List.of(
                "Hello, my name is Collins",
                "I am 25 years and live in Ghana",
                "I am a backend Engineer at Amalitech",
                "I joined GTP in 2025."
        ));

        Map<String, Long> frequency = null;


        frequency = StreamProcessing.wordFrequency(filePath);

        //assertions
        assertNotNull(frequency);
        assertEquals(3, frequency.get("i"));
        assertEquals(1,frequency.get("hello"));
        assertNotEquals(1,frequency.get("Hello"));

    }

    @Test
    void wordFrequency_shouldIgnorePunctuationsAndCase() throws IOException{
        //write to file

        ReadAndWrite.writeFile(filePath,List.of("Hello, my name is Collins. I work at Amalitech; + /"));

        //call wordFrequency
        Map<String,Long> frequency = StreamProcessing.wordFrequency(filePath);

        assertNull(frequency.get("/"));
        assertEquals(1,frequency.get("hello"));
        assertEquals(9,frequency.size());
    }

    @Test
    void wordFrequency_shouldHandleEmptyFile() throws IOException{
        //Empty file
        ReadAndWrite.writeFile(filePath,List.of(""));

        //call wordFrequency
        Map<String,Long> frequency = StreamProcessing.wordFrequency(filePath);

        assertTrue(frequency.isEmpty());
    }


    //PatternRecognition
    @Test
    void patternRecognition() throws IOException{
        //wirte to file
        ReadAndWrite.writeFile(filePath, List.of("This are some various types " +
                "of email addresses, abdx@ghs,jhs.com" +
                "myname@gmail.com, test@xzy.com, collins12@abc.xzy.org"));
        //Pattern
        String regex = "\\b[\\w.%-]+@[\\w.-]+\\.[A-Za-z]{2,4}\\b";

        List<String> lines = StreamProcessing.patternRecognition(filePath,regex);

        //assertions
        assertTrue(lines.contains("collins12@abc.xzy.org"));
        assertFalse(lines.contains("abdx@ghs,jhs.com"));
        assertEquals(3,lines.size());
    }

    @Test
    void patternRecognition_shouldReturnEmptyListForNoMatches() throws IOException{
        //wirte to file
        ReadAndWrite.writeFile(filePath, List.of("This are some various types " +
                "of email addresses, abdx@ghs,jhs.com"));
        //Pattern
        String regex = "\\b[\\w.%-]+@[\\w.-]+\\.[A-Za-z]{2,4}\\b";

        List<String> lines = StreamProcessing.patternRecognition(filePath,regex);

        //assertions
        assertTrue(lines.isEmpty());
        assertFalse(lines.contains("abdx@ghs,jhs.com"));
    }

    //WordSummary Tests
    @Test
    void wordSummary() throws IOException{
        //write to file
        ReadAndWrite.writeFile(filePath,List.of(
                "Hello, my name is Collins",
                "I am 25 years and live in Ghana",
                "I am a backend Engineer at Amalitech",
                "I joined GTP in 2025."
        ));
        List<Map.Entry<String, Long>> summary = null;

        try{
            summary = StreamProcessing.wordSummary(filePath,5);
        }catch (IOException e){
            logger.info("Error getting summary: " + e.getMessage());
        }

        //assertions
        assertNotNull(summary);
        assertEquals(5,summary.size());
        assertEquals("i",summary.get(0).getKey());
        assertEquals(3,summary.get(0).getValue());
    }


    @Test
    void countPatternOccurrence() throws IOException{
        //write to file
        ReadAndWrite.writeFile(filePath, List.of(
                "Phone Number: 024-054-1125",
                "SSN 1: 125-15-1125, SNN 2: 023-55-7789"
        ));

        String regex = "\\d{3}-\\d{2}-\\d{4}";

        long count = StreamProcessing.countPatternOccurrence(filePath,regex);

        //assertions
        assertTrue(count == 2);
    }


    @Test
    void countPatternOccurrence_shouldReturnZeroForNoMatches() throws IOException{
        //write to file
        ReadAndWrite.writeFile(filePath, List.of(
                "Phone Number: 024-054-1125",
                "WRONG SSN 1: 125-154-1125, WRONG SNN 2: 023-551-7789"
        ));

        String regex = "\\d{3}-\\d{2}-\\d{4}";

        long count = StreamProcessing.countPatternOccurrence(filePath,regex);

        //assertions
        assertTrue(count == 0);
    }
}