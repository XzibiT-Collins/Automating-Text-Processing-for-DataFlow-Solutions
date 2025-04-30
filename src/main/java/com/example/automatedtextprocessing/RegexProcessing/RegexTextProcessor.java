package com.example.automatedtextprocessing.RegexProcessing;

import com.example.automatedtextprocessing.FileProcessing.ReadAndWrite;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTextProcessor {

    //match and extract method
    public static List<String> matchPattern(String filePath, String regex) throws IOException {
        if(regex.isEmpty()){
            return Collections.emptyList();
        }

        //create a new list
        List<String> matchingWords = new ArrayList<>();

        //compile regex
        Pattern pattern = Pattern.compile(regex);

        //read from file
        List<String> lines = ReadAndWrite.readFile(filePath);

        //find matches
        for(String line: lines){
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()){
                matchingWords.add(matcher.group());
            }
        }
        return matchingWords; //return List of matching words
    }

    //Search method
    public static List<String> search(String filePath, String regex) throws IOException{
        //compile pattern
        Pattern pattern = Pattern.compile(regex);

        //list to hold results
        List<String> searchResults = new ArrayList<>();

        //read file
        List<String> lines = ReadAndWrite.readFile(filePath);

        //search for patterns
        for(String line : lines){
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()){
                searchResults.add(line);
            }
        }
        return searchResults;
    }

    //Replace text in file
    public static void replaceText(String filePath, String replacement,String regex)throws IOException{
        Pattern pattern = Pattern.compile(regex);

        //read file
        List<String> lines = ReadAndWrite.readFile(filePath);

        //List to hold updated lines
        List<String> updatedLines = new ArrayList<>();

        //find and replace matches
        for(String line : lines){
            //replaces match with replacement
            updatedLines.add(pattern.matcher(line).replaceAll(replacement));
        }

        //update original file with new lines
        ReadAndWrite.writeFile(filePath,updatedLines);
    }

}
