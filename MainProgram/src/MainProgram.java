import java.util.Scanner;

public class MainProgram {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n;
        System.out.print("Masukkan banyaknya n: ");
        n = in.nextInt();
        Polinom A = new Polinom(n, true);
        Polinom B = new Polinom(n,true);

        System.out.print("A(x) = ");
        A.printPolinom();
        System.out.print("B(x) = ");
        B.printPolinom();
        System.out.println();

        long StartTime = System.nanoTime();
        Polinom bruteForce = A.BruteForceMulti(A,B);
        long EndTime = System.nanoTime();
        long TimeElapsed = EndTime-StartTime;
        double Microseconds = ((double)TimeElapsed)/1000;

        System.out.println("Perkalian dengan algoritma Brute Force:");
        System.out.print("A(x)B(x) = ");
        bruteForce.printPolinom();
        System.out.println("Kompleksitas waktu : " + Microseconds + " mikrosekon");
        System.out.println("Total operasi tambah : " + bruteForce.countTambah);
        System.out.println("Total operasi kali : " + bruteForce.countKali);
        System.out.println();

        bruteForce.countTambah = 0;
        bruteForce.countKali = 0;

        long StartTime2 = System.nanoTime();
        Polinom DnC = A.DivideAndConquerMulti(A,B);
        long EndTime2 = System.nanoTime();
        long TimeElapsed2 = EndTime2-StartTime2;
        double Microseconds2 = ((double)TimeElapsed2)/1000;

        System.out.println("Perkalian dengan algoritma Divide and Conquer:");
        System.out.print("A(x)B(x) = ");
        DnC.printPolinom();
        System.out.println("Kompleksitas waktu : " + Microseconds2 + " mikrosekon");
        System.out.println("Total operasi tambah : " + DnC.countTambah);
        System.out.println("Total operasi kali : " + DnC.countKali);
        }
}
