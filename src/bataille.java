import java.util.Random;

public class bataille{

    public static int [][] grilleOrdi = new int[10][10];
    public static int [][] grilleJeu = new int[10][10];

    public static boolean posOk(int [][]grille, int l, int c, int d, int t){
        boolean isPosOk = true;
            for(int i = 0; i<t; i++){
                if(d == 1){
                    l = l + i;
                    if (grille[l][c] != 0){
                        isPosOk = false;
                    }
                }
                else{
                    c = c + i;
                    if (grille[l][c] != 0){
                        isPosOk = false;
                    }
                }
            }
        return isPosOk;
    }

    public static void initGrilleOrdi(){

    }

    public static void main(String[] args){
        System.out.println("Hello world");
    }
}