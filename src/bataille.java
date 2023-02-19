import java.util.Random;
import java.util.Scanner;

public class bataille{

    public static int [][] grilleOrdi = new int[10][10];
    public static int [][] grilleJeu = new int[10][10];

    public static boolean posOk(int [][]grille, int l, int c, int d, int t){
        boolean isPosOk = true;

        if(d == 1){
            if(c+t >= 10){
                isPosOk = false;
            }
            else{
                for(int i = 0; i < t+1; i++){
                    if(grille[l][c+i] != 0){
                        isPosOk = false;
                    }
                }
            }
        }

        if(d == 2){
            if(l+t >= 10){
                isPosOk = false;
            }
            else{
                for(int i = 0; i < t+1; i++){
                    if(grille[l+i][c] != 0){
                        isPosOk = false;
                    }
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
        int l = randRange(0,10);
        int c = randRange(0,10);
        int d = randRange(1,3);

        //Initialiser porte-avions
        while(posOk(grilleOrdi, l, c, d, 5) != true) {
            l = randRange(0, 10);
            c = randRange(0, 10);
            d = randRange(1, 3);
        }
        if (posOk(grilleOrdi, l, c, d, 5) == true) {
            initBateaux(l, c, d, 5, 1);
        }

        //Initialiser croiseur
        while(posOk(grilleOrdi, l, c, d, 4) != true) {
            l = randRange(0, 10);
            c = randRange(0, 10);
            d = randRange(1, 3);
        }
        if (posOk(grilleOrdi, l, c, d, 4) == true) {
            initBateaux(l, c, d, 4, 2);
        }

        //Initialiser contre-torpilleurs
        while(posOk(grilleOrdi, l, c, d, 3) != true) {
            l = randRange(0, 10);
            c = randRange(0, 10);
            d = randRange(1, 3);
        }
        if (posOk(grilleOrdi, l, c, d, 3) == true) {
            initBateaux(l, c, d, 3, 3);
        }

        //Initialiser sous-marin
        while(posOk(grilleOrdi, l, c, d, 3) != true) {
            l = randRange(0, 10);
            c = randRange(0, 10);
            d = randRange(1, 3);
        }
        if (posOk(grilleOrdi, l, c, d, 3) == true) {
            initBateaux(l, c, d, 3,4);
        }

        //Initialiser torpilleur
        while(posOk(grilleOrdi, l, c, d, 2) != true) {
            l = randRange(0, 10);
            c = randRange(0, 10);
            d = randRange(1, 3);
        }
        if (posOk(grilleOrdi, l, c, d, 2) == true) {
            initBateaux(l, c, d, 2,5);
        }
    }

    public static void initGrilleJeu(){
        String[] tableauDeBateaux = new String[6];
        tableauDeBateaux[1] = "porte-avions";
        tableauDeBateaux[2] = "croiseur";
        tableauDeBateaux[3] = "contre-torpilleurs";
        tableauDeBateaux[4] = "sous-marin";
        tableauDeBateaux[5] = "torpilleur";

        String expressionRegLettre = "^[a-jA-J]";
        String expressionRegChiffre = "^[1-9]|10";

        String inputUtilisateur;

        for(int i = 1; i < 6; i++){
            System.out.println("Entrez la lettre pour le " + tableauDeBateaux[i]);
            Scanner scanner = new Scanner(System.in);
            inputUtilisateur = scanner.nextLine();
            while(!inputUtilisateur.matches(expressionRegLettre)){
                System.out.println("Erreur. Vous n'avez pas saisi une lettre entre A et J. Veuillez recommencer");
                System.out.println("Entrez la lettre pour le " + tableauDeBateaux[i]);
                inputUtilisateur = scanner.nextLine();
            }

            System.out.println("Entrez le chiffre pour le " + tableauDeBateaux[i]);
            inputUtilisateur = scanner.nextLine();
            while(!inputUtilisateur.matches(expressionRegChiffre)){
                System.out.println("Erreur. Vous n'avez pas saisi un chiffre entre 1 et 10. Veuillez recommencer");
                System.out.println("Entrez le chiffre pour le " + tableauDeBateaux[i]);
                inputUtilisateur = scanner.nextLine();
            }
        }
    }

    public static void initBateaux(int l, int c, int d, int t, int typeDeBateau){
        if (d == 1){
            for(int i = 0; i<t; i++){
                grilleOrdi[l][c+i] = typeDeBateau;
            }
        }
        if (d == 2){
            for(int i = 0; i<t; i++){
                grilleOrdi[l+i][c] = typeDeBateau;
            }
        }
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

        initGrilleJeu();
    }
}