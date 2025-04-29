package com.example.automatedtextprocessing.RegexProcessing;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTextProcessor {

    //match and extract method
    public List<String> matchPattern(String filePath, String regex) throws IOException {
        if(regex.isEmpty()){
            return Collections.emptyList();
        }

        //create a new list
        List<String> matchingWords = new ArrayList<>();

        //compile regex
        Pattern p = Pattern.compile(regex);

        //read from file using bufferedReader
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;

            while((line = reader.readLine()) != null){//read line by line
                Matcher matcher = p.matcher(line); //create a matcher
                while (matcher.find()){
                    matchingWords.add(matcher.group()); //add matching words to group
                }
            }
        }
        return matchingWords; //return List of matching words
    }

    //Search method
    public boolean search(String filePath, String regex){
        //compile pattern
        Pattern pattern = Pattern.compile(regex);
        return true;
    }

}
