import java.util.Random;
import static java.lang.Math.abs;

public class Polinom {
    //atribut
    private int ordeP;
    private int[] koefPolinom;
    public static int countTambah = 0;
    public static int countKali = 0;

    //constructor
    public Polinom(int orde, boolean random){
        this.ordeP = orde + 1;
        this.koefPolinom = new int[this.ordeP];

        if(random){
            Random r = new Random();
            for (int i = 0; i < this.ordeP; i++) {
                this.koefPolinom[i] = (r.nextInt(21) - 10);
            }
        }
        else{
            for (int i = 0; i < this.ordeP; i++) {
                this.koefPolinom[i] = 0;
            }
        }
    }

    //copy constructor
    public Polinom(Polinom P){
        this.ordeP = P.ordeP;
        this.koefPolinom = P.koefPolinom;
    }

    private int getLength(){
        return (this.ordeP);
    }

    private int getLastIdx(){
        return (this.ordeP - 1);
    }

    public void printPolinom(){
        boolean firstPass = false;
        for (int i = 0; i < this.ordeP; i++) {
            if (this.koefPolinom[i] != 0) {
                if(!firstPass){
                    System.out.print(this.koefPolinom[i]);
                    firstPass = true;
                }
                else {
                    if(this.koefPolinom[i] != 1 && this.koefPolinom[i] != -1) {
                            System.out.print(abs(this.koefPolinom[i]));
                        }
                    }
                if (i != 0) {
                    if (i != 1) {
                        System.out.print("x^" + i);
                    } else {
                        System.out.print("x");
                    }
                }
                if(i != (this.ordeP - 1)) {
                    if (this.koefPolinom[i+1] >= 0) {
                        System.out.print(" + ");
                    }
                    else if (this.koefPolinom[i+1] < 0) {
                        System.out.print(" - ");
                    }
                }
            }
        }
        System.out.println();
    }

    public static Polinom BruteForceMulti(Polinom P1, Polinom P2){
        countTambah = 0;
        countKali = 0;
        Polinom bf = new Polinom(P1.ordeP + P2.ordeP - 2, false);
        for (int i = 0; i < P1.ordeP; i++) {
            for (int j = 0; j < P2.ordeP; j++) {
                bf.koefPolinom[i+j] += P1.koefPolinom[i] * P2.koefPolinom[j];
                countTambah++;
                countKali++;
            }
        }
        return bf;
    }

    private Polinom tambahPolinom(Polinom P){
        Polinom sum;
        if (this.ordeP >= P.ordeP){
            sum = new Polinom(this.ordeP-1, false);
        }
        else{
            sum = new Polinom(P.ordeP-1, false);
        }
        for (int i = 0; i < this.ordeP; i++) {
            sum.koefPolinom[i] = this.koefPolinom[i];
        }
        for (int i = 0; i < P.ordeP; i++) {
            sum.koefPolinom[i] += P.koefPolinom[i];
            countTambah++;
        }
        return sum;
    }

    private Polinom kurangPolinom(Polinom P){
        Polinom sub;
        if (this.ordeP >= P.ordeP){
            sub = new Polinom(this.ordeP-1, false);
        }
        else{
            sub = new Polinom(P.ordeP-1, false);
        }
        for (int i = 0; i < this.ordeP; i++) {
            sub.koefPolinom[i] = this.koefPolinom[i];
        }
        for (int i = 0; i < P.ordeP; i++) {
            sub.koefPolinom[i] -= P.koefPolinom[i];
            countTambah++;
        }
        return sub;
    }

    private static Polinom subPolinom(Polinom P, int startIdx, int endIdx){
        Polinom subP = new Polinom(endIdx-startIdx, false);
        for (int i = 0, j = startIdx; i < subP.ordeP && j <= endIdx; i++, j++) {
            subP.koefPolinom[i] = P.koefPolinom[j];
        }
        return subP;
    }

    private Polinom Pengali(int pengali){
        Polinom newP = new Polinom(this.getLastIdx() + pengali, false);
        for (int i = 0, j = pengali; i < this.getLength() && j < this.getLength() + pengali; i++, j++) {
            newP.koefPolinom[j] = this.koefPolinom[i];
        }
        return newP;
    }

    public static Polinom DivideAndConquerMulti(Polinom A, Polinom B){
        Polinom DnC = new Polinom(A.ordeP+B.ordeP - 2, false);
        if(A.getLength() == 1 && B.getLength() == 1){
            DnC.koefPolinom[0] = A.koefPolinom[0] * B.koefPolinom[0];
            countKali++;
            return DnC;
        }
        else{
            Polinom A0 = subPolinom(A, 0, (A.ordeP/2) - 1);
            Polinom A1 = subPolinom(A, A.ordeP/2, A.getLastIdx());
            Polinom B0 = subPolinom(B, 0, (B.ordeP/2) - 1);
            Polinom B1 = subPolinom(B, B.ordeP/2, B.getLastIdx());

            Polinom A0A1sum = A0.tambahPolinom(A1);
            Polinom B0B1sum = B0.tambahPolinom(B1);

            Polinom Y = DivideAndConquerMulti(A0A1sum, B0B1sum);
            Polinom U = DivideAndConquerMulti(A0,B0);
            Polinom Z = DivideAndConquerMulti(A1,B1);

            Polinom subYUZ = Y.kurangPolinom(U).kurangPolinom(Z);

            Polinom PengaliSubYUZ = subYUZ.Pengali(A.ordeP/2);
            Polinom PengaliZ = Z.Pengali((A.ordeP/2)*2);

            DnC = U.tambahPolinom(PengaliSubYUZ).tambahPolinom(PengaliZ);
            return DnC;
        }
    }

    private void printIsiArray(){
        for (int i = 0; i < this.ordeP; i++) {
            System.out.print(this.koefPolinom[i] + " ");
        }
        System.out.println();
    }

//    public static void main(String[] args) {
//        int n = 5;
//        Polinom A = new Polinom(n, true);
//        Polinom B = new Polinom(n,true);
//
//        System.out.print("A(x) = ");
//        A.printPolinom();
//        System.out.print("B(x) = ");
//        B.printPolinom();
//        System.out.println();
//
//        long StartTime = System.nanoTime();
//        Polinom bruteForce = BruteForceMulti(A,B);
//        long EndTime = System.nanoTime();
//        long TimeElapsed = EndTime-StartTime;
//        double Miliseconds = ((double)TimeElapsed)/1000;
//
//        System.out.println("Perkalian dengan algoritma Brute Force:");
//        System.out.print("A(x)B(x) = ");
//        bruteForce.printPolinom();
//        System.out.println("Kompleksitas waktu : " + Miliseconds + " mikrosekon");
//        System.out.println("Total operasi tambah : " + countTambah);
//        System.out.println("Total operasi kali : " + countKali);
//        System.out.println();
//
//        countTambah = 0;
//        countKali = 0;
//
//        long StartTime2 = System.nanoTime();
//        Polinom DnC = DivideAndConquerMulti(A,B);
//        long EndTime2 = System.nanoTime();
//        long TimeElapsed2 = EndTime2-StartTime2;
//        double Miliseconds2 = ((double)TimeElapsed2)/1000;
//
//        System.out.println("Perkalian dengan algoritma Divide and Conquer:");
//        System.out.print("A(x)B(x) = ");
//        DnC.printPolinom();
//        System.out.println("Kompleksitas waktu : " + Miliseconds2 + " mikrosekon");
//        System.out.println("Total operasi tambah : " + countTambah);
//        System.out.println("Total operasi kali : " + countKali);
//    }
}
