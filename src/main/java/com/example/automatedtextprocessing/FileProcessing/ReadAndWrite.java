package com.example.automatedtextprocessing.FileProcessing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadAndWrite {
    ReadAndWrite(){
        //empty constructor
    }

    //Read from a file
    public static List<String> readFile(String filePath) throws IOException{
        List<String> lines = new ArrayList<>(); // list to hold all lines for further processing

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;

            while((line = reader.readLine()) != null){
                lines.add(line); // add line to lines list
            }
        }
        return lines;
    }

    //Write to file
    public static void writeFile(String filePath, List<String> lines)throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for(String line : lines){
                writer.write(line); //write line to file
                writer.newLine(); //move to next line
            }
        }
    }
}
