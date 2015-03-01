package com.tsystems.javaschool.tasks;


import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kostkol87 on 10.12.14.
 */
public class DuplicateFinderImpl implements DuplicateFinder {

    private ArrayList<String> fileContent;
    private TreeMap<String, Integer> resultMap;
    private BufferedWriter fileWriter;
    private BufferedReader fileReader;

    @Override
    public boolean process(File sourceFile, File targetFile) {
        resultMap = new TreeMap<String, Integer>();
        String str;

            try{

                fileReader = new BufferedReader(new FileReader(sourceFile));
                //read and save content from src file to TreeMap as map's key and count of duplicate to map's value
                while ((str = fileReader.readLine()) != null) {
                    if(!resultMap.containsKey(str)){
                        resultMap.put(str, 1);
                    }else {
                        int tmp = resultMap.get(str) + 1;
                        resultMap.put(str,tmp);
                    }
                }
                //write formated content to target file
                if(targetFile.length() == 0){
                    fileWriter = new BufferedWriter(new FileWriter(targetFile));

                    for (Map.Entry<String, Integer> entry : resultMap.entrySet()){
                        fileWriter.write(entry.getKey() + " [" + entry.getValue() + "] \n");
                    }
                //if target file is not empty add content to end
                }else {
                    fileWriter = new BufferedWriter(new FileWriter(targetFile,true));

                    for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
                        fileWriter.write(entry.getKey() + " [" + entry.getValue() + "] \n");
                    }
                }
                //close all streams and flush buffer
                fileReader.close();
                fileWriter.flush();
                fileWriter.close();
                //on any IO Exception return false
            }catch (IOException e){
                return false;
            }
        return true;
    }
}
