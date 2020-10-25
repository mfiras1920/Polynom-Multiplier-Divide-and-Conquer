import java.util.Random;

import static java.lang.Math.abs;

public class DnCPolynom {
    //atribut
    static int[] polyA;
    static int[] polyB;

    public static void BuatPolinom(int N){
        int orde = N+1;
        polyA = new int[orde];
        polyB = new int[orde];

        Random r = new Random();
        for (int i = 0; i < orde; i++) {
            polyA[i] = (r.nextInt(50 + 50) - 50);
            polyB[i] = (r.nextInt(50 + 50) - 50);
        }
    }

    private static void printPolinomWithParameter(int[] polinom){
        int polLength = polinom.length;

        for (int i = 0; i < polLength; i++) {
            if (polinom[i] != 0) {
                System.out.print(abs(polinom[i]));
                if (i != 0) {
                    System.out.print("x^" + i);
                }
                if ((polinom[i] > 0) && (i != (polLength - 1))) {
                    System.out.print(" + ");
                }
                else if ((polinom[i] < 0) && (i != (polLength - 1))) {
                    System.out.print(" - ");
                }
            }
        }
        System.out.println();
    }

    public static void printPolinom(){
        System.out.print("A(x) = ");
        printPolinomWithParameter(polyA);
        System.out.print("B(x) = ");
        printPolinomWithParameter(polyB);
    }

    public static void main(String[] args) {
        int n = 5;
        BuatPolinom(n);
        printPolinom();
    }
}
