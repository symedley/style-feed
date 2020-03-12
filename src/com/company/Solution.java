package com.company;

import java.util.List;
import java.util.Scanner;

/*
 * HackerRank challenge:
 *
 * description
 *
 */
public class Solution {

    public static void main(String[] args) {
        try {
            StyleFeed sf = new StyleFeed();

        List<Integer> answer = sf.getFeed( 1);
        System.out.println(answer);
        }catch (InterruptedException e) {}
    }
}
