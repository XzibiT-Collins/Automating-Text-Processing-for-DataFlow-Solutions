package com.example.automatedtextprocessing.FileAnalysis;

import com.example.automatedtextprocessing.FileProcessing.ReadAndWrite;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StreamProcessing {
    StreamProcessing(){}

    //word frequency
    public static Map<String, Long> wordFrequency(String filePath)throws IOException {
        //read file
        List<String> lines = ReadAndWrite.readFile(filePath);

        //use streams for word count
        return lines.stream().flatMap(
                line -> Arrays.stream(line.toLowerCase().replaceAll("[a-zA-Z0-9\\s]","").split("\\s+")) //remove punctuations and empty spaces.
        ).filter(
                word -> !word.isBlank()
        ).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
    }

    //Pattern recognition
    public static List<String > patternRecognition(String filePath,String regex)throws IOException{
        //read file
        List<String> lines = ReadAndWrite.readFile(filePath);

        //generate pattern
        Pattern pattern = Pattern.compile(regex);

        //return patterns
        return lines.stream().flatMap(
                line -> pattern.matcher(line).results().map(MatchResult::group)).collect(Collectors.toList());
    }

    //Text summarization
    public static List<Map.Entry<String,Long>> wordSummary(String filePath, int n)throws IOException{
        //map to hold summary data
        Map<String,Long> summaryFreq = wordFrequency(filePath);

        return summaryFreq.entrySet().stream()
                .sorted(Map.Entry.<String,Long>comparingByValue(Comparator.reverseOrder())) //sort highest frequency first
                .limit(n)
                .collect(Collectors.toList());
    }
}
