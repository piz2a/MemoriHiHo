package io.github.piz2a.memorihiho.utils;

import java.util.Scanner;

public class StringMatch {

    // String a가 b와 일치하는 정도를 반환
    public static double ratio(String a, String b) {
        int lengthA = a.length(), lengthB = b.length();
        int[][] table = new int[lengthA+1][lengthB+1];

        for (int i=1; i<=lengthA; i++) {
            for (int j=1; j<=lengthB; j++) {
                table[i][j] = a.charAt(i-1) == b.charAt(j-1) ?
                        table[i-1][j-1] + 1 : Math.max(table[i-1][j], table[i][j-1]);
                // System.out.print(table[i][j] + " ");
            }
            // System.out.println();
        }

        if (lengthA == 0)
            return (lengthB == 0) ? 1 : 0;
        return (double) table[lengthA][lengthB] / lengthB;
    }

    public static void test() {
        System.out.println(StringMatch.ratio("Hello World!", "Hello Word!"));
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String s1 = s.nextLine();
        String s2 = s.nextLine();
        System.out.println(StringMatch.ratio(s1, s2));
    }

}
