package com.example.automatedtextprocessing.RegexProcessing;

import com.example.automatedtextprocessing.FileProcessing.ReadAndWrite;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegexTextProcessorTest {

    //file path
    final String filePath = "src/test/java/com/example/automatedtextprocessing/TestFile/Test.txt";

    @Test
    void matchPattern() throws IOException {
        // Create a test file
        ReadAndWrite.writeFile(filePath, List.of(
                "Email: test@example.com",
                "Phone: 123-456-7890",
                "Another email: user@domain.org"
        ));

        // Test email pattern
        String regex = "\\b[\\w.%-]+@[\\w.-]+\\.[A-Za-z]{2,4}\\b";
        List<String> result = RegexTextProcessor.matchPattern(filePath, regex);

        // Verify results
        assertEquals(2, result.size());
        assertTrue(result.contains("test@example.com"));
        assertTrue(result.contains("user@domain.org"));
    }

    @Test
    void matchPattern_shouldReturnEmptyListForNoMatches() throws IOException {
        // Create a test file
        ReadAndWrite.writeFile(filePath,List.of(
                "Just some regular text without patterns"
        ));

        String regex = "\\d{3}-\\d{2}-\\d{4}";

        // Test SSN pattern
        List<String> result = RegexTextProcessor.matchPattern(filePath, regex);

        // Verify results
        assertTrue(result.isEmpty());
    }

    @Test
    void matchPattern_shouldReturnEmptyListForEmptyRegex() throws IOException {
        // Create a test file
        ReadAndWrite.writeFile(filePath, List.of("Some content"));

        String regex = "";

        List<String> result = RegexTextProcessor.matchPattern(filePath, regex);

        assertTrue(result.isEmpty());
    }

    @Test
    void matchPattern_shouldHandleMultipleMatchesPerLine() throws IOException {
        // Create a test file
        ReadAndWrite.writeFile(filePath, List.of("aa bb aa cc aa dd"));

        String regex = "aa";

        List<String> result = RegexTextProcessor.matchPattern(filePath, regex);

        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(s -> s.equals("aa")));
    }

    @Test
    void search_shouldReturnLinesContainingMatches() throws IOException {
        // Create a test file
        ReadAndWrite.writeFile(filePath, List.of(
                "First line",
                "Second line with email: test@example.com",
                "Third line",
                "Fourth line with email: user@domain.org"
        ));

        // Test email pattern
        String regex = "\\b[\\w.%-]+@[\\w.-]+\\.[A-Za-z]{2,4}\\b";

        List<String> result = RegexTextProcessor.search(filePath, regex);

        // assertions
        assertEquals(2, result.size());
        assertTrue(result.contains("Second line with email: test@example.com"));
        assertTrue(result.contains("Fourth line with email: user@domain.org"));
    }

    @Test
    void search_shouldReturnEmptyListForNoMatches() throws IOException {
        // Create a test file
        ReadAndWrite.writeFile(filePath, List.of(
                "Just some regular text without patterns"
        ));

        String regex = "\\d{3}-\\d{2}-\\d{4}";

        List<String> result = RegexTextProcessor.search(filePath, regex);

        assertTrue(result.isEmpty());
    }

    @Test
    void replaceText_shouldReplaceAllMatches() throws IOException {
        // Create a test file
        ReadAndWrite.writeFile(filePath, List.of(
                "Secret: 123-45-6789",
                "Another secret: 987-65-4321"
        ));

        String regex = "\\d{3}-\\d{2}-\\d{4}";

        // Replace SSNs with [REPLACED]
        RegexTextProcessor.replaceText(filePath, "[REPLACED]", regex);

        // read file
        List<String> lines = ReadAndWrite.readFile(filePath);

        //assertions
        assertEquals(2, lines.size());
        assertEquals("Secret: [REPLACED]", lines.get(0));
        assertEquals("Another secret: [REPLACED]", lines.get(1));
    }

    @Test
    void replaceText_shouldNotModifyFileWhenNoMatches() throws IOException {
        // Create a test file
        String originalContent = "Just some regular text";
        ReadAndWrite.writeFile(filePath, List.of(
                "Just some regular text"
        ));

        String regex = "\\d{3}-\\d{2}-\\d{4}";

        // Try to replace SSNs
        RegexTextProcessor.replaceText(filePath, "[REPLACED]", regex);

        // Verify file content remains unchanged
        assertEquals(originalContent, ReadAndWrite.readFile(filePath).getFirst());
    }

    @Test
    void replaceText_shouldHandleEmptyFile() throws IOException {
        // Create an empty test file
        ReadAndWrite.writeFile(filePath, List.of());

        // Try to replace something
        RegexTextProcessor.replaceText(filePath, "new", "old");

        // Verify file remains empty
        assertTrue(ReadAndWrite.readFile(filePath).isEmpty());
    }

    @Test
    void matchPattern_shouldThrowIOExceptionForInvalidFile() {
        assertThrows(IOException.class, () -> {
            RegexTextProcessor.matchPattern("nonexistent.txt", ".*");
        });
    }

    @Test
    void search_shouldThrowIOExceptionForInvalidFile() {
        assertThrows(IOException.class, () -> {
            RegexTextProcessor.search("nonexistent.txt", ".*");
        });
    }

    @Test
    void replaceText_shouldThrowIOExceptionForInvalidFile() {
        assertThrows(IOException.class, () -> {
            RegexTextProcessor.replaceText("nonexistent.txt", "replacement", "pattern");
        });
    }

    @Test
    void replaceText_shouldPreserveNonMatchingLines() throws IOException {
        // Create a test file

        ReadAndWrite.writeFile(filePath, List.of(
                "Line 1",
                "Line 2 with 123-45-6789",
                "Line 3",
                "Line 4 with 987-65-4321"
        ));

        String regex = "\\d{3}-\\d{2}-\\d{4}";

        // Replace SSNs
        RegexTextProcessor.replaceText(filePath, "[REPLACED]", regex);

        // read file
        List<String> lines = ReadAndWrite.readFile(filePath);

        //assertions
        assertEquals(4, lines.size());
        assertEquals("Line 1", lines.get(0));
        assertEquals("Line 2 with [REPLACED]", lines.get(1));
        assertEquals("Line 3", lines.get(2));
        assertEquals("Line 4 with [REPLACED]", lines.get(3));
    }
}