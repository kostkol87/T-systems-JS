package com.tsystems.javaschool.tasks;

import java.util.List;

/**
 * Created by kostkol87 on 10.12.14.
 */
public class SubsequenceImpl implements Subsequence{


    @Override
    public boolean find(List x, List y) {
        boolean result = true;
        int tmp = 0;
        for (Object oX : x){
            if (!result) break;
            result = false;
            //looking for equal element in second list, if get one:
            //break current iteration and save current index, then
            //continue search from saved index, but for next element of the first list
            for (int i = tmp; i < y.size(); i++) {
                 if(oX.equals(y.get(i))){
                     result = true;
                     tmp = i + 1;
                     break;
                 }
            }
        }
        return result;
    }
}
