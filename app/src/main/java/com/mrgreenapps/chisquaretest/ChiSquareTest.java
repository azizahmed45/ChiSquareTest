package com.mrgreenapps.chisquaretest;

import java.util.List;

public class ChiSquareTest {

    private static double chiSquare(double observedValue, double expectedValue){
        return Math.pow(observedValue - expectedValue, 2) / expectedValue;
    }

    public static double chiSquare(List<Double> observedValueList, List<Double> expectedValueList){
        double result = 0;

        if(observedValueList.size() == expectedValueList.size()){

            for(int i = 0; i < observedValueList.size(); i++){

                result += chiSquare(observedValueList.get(i), expectedValueList.get(i));

            }

        }

        return result;
    }

    public static boolean isAccepted(double chiSquare, double chiSquareWithCondition){
        return chiSquare <= chiSquareWithCondition;
    }
}
