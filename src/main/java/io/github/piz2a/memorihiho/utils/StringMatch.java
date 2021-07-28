package io.github.piz2a.memorihiho.utils;

public class StringMatch {

    public static double ratio(String a, String b) {
        int lengthA = a.length(), lengthB = b.length();
        int[][] table = new int[lengthA+1][lengthB+1];

        for (int i=1; i<=lengthA; i++) {
            for (int j=1; j<=lengthB; j++) {
                table[i][j] = a.charAt(i-1) == b.charAt(j-1) ?
                        table[i-1][j-1] + 1 : Math.max(table[i-1][j], table[i][j-1]);
                // System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }

        return (double) table[lengthA][lengthB] / lengthA;
    }

    public static void test() {
        System.out.println(StringMatch.ratio("Hello World!", "Hello Word!"));
    }

}
