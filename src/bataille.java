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

    public static Random rand = new Random();
    public static int randRange(int a, int b){
        return rand.nextInt(b-a)+a;
    }

    public static void initGrilleOrdi(){

    }

    public static void AfficherGrille(int grille[][]){

        //Variable utilisée pour afficher le numéro de la ligne
        int numero_de_la_ligne = 0;

        //Affichage des lettres des colonnes
        System.out.print("   A B C D E F G H I J");

        //Défiler chaque ligne
        for(int i = 0; i <10; i++){

            //Incrémenter le numéro de la ligne
            numero_de_la_ligne++;

            //Aller a la ligne suivante
            System.out.println("");
            System.out.print(numero_de_la_ligne);

            //Pour un affichage propre, on ne veut pas mettre d'espace apres le numéro 10, car il prend deja deux caractères de place
            if(numero_de_la_ligne != 10){
                System.out.print(" ");
            }

            //Défiler chaque colonne
            for(int j = 0; j <10; j++) {
                System.out.print(" ");

                //Affichage de la grille
                System.out.print(grille[i][j]);
            }
        }

    }

    public static void main(String[] args){

        grilleJeu[5][2] = 6;

        AfficherGrille(grilleJeu);

        for(int i = 0; i<10; i++){
            System.out.println(randRange(0,10));
        }
    }
}