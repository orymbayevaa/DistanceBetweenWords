package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        File file=new File("/Users/aishaorymbayeva/Desktop/minDistance/words.txt");
        Scanner sc=new Scanner(file);
        HashMap<String,Integer> hm = new HashMap<>();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> integers = new ArrayList<>();
        int count = 0;
        while(sc.hasNextLine()){
            String[] s = sc.nextLine().split("\\s+");
            words.add(s[0]);
            integers.add(s[1]);
        }



        ArrayList<BigInteger> bigIntegers = new ArrayList<>();
        BigInteger sum = BigInteger.ZERO;
        for(String s : integers){
            bigIntegers.add(new BigInteger(s));
            sum = sum.add(new BigInteger(s));
        }


        String[] targetWords = findWords(words,"shep",3);
        BigDecimal[] targetIntegers = findProbs(words,bigIntegers,"shep",3,sum);



        for(int j = 0 ; j < 10 ;j++){
            System.out.println(targetWords[j] + " ---- "+ targetIntegers[j]);
        }


        // write your code here
    }

    public static String[] findWords(ArrayList<String> words, String word, int distance){
        ArrayList<String> result = new ArrayList<>();
        for(String s: words){
            if(distance == minDistance(s,word)){
                result.add(s);
            }
        }
        Object[] objectList = result.toArray();
        String[] stringArray =  Arrays.copyOf(objectList,objectList.length,String[].class);
        return stringArray;
    }

    public static BigDecimal[] findProbs(ArrayList<String> words, ArrayList<BigInteger> integers, String word, int distance,BigInteger sum){
        ArrayList<BigDecimal> result = new ArrayList<>();
        for(int i = 0; i < words.size(); i++){
            String s = words.get(i);
            BigInteger j = integers.get(i);
            if(distance == minDistance(s,word)){
                result.add(new BigDecimal(j));
            }
        }

        ArrayList<BigDecimal>  ret = new ArrayList<>();
        for(BigDecimal b : result){
            ret.add(b.divide(new BigDecimal(sum),30, RoundingMode.CEILING));
        }

        Object[] objectList = ret.toArray();
        BigDecimal[] bigIntegers =  Arrays.copyOf(objectList,objectList.length,BigDecimal[].class);


        return bigIntegers;

    }





    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }

}
