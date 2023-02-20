import java.util.Random;
import java.util.Scanner;

public class bataille{

    public static int [][] grilleOrdi = new int[10][10];
    public static int [][] grilleJeu = new int[10][10];

    public static boolean posOk(int [][]grille, int l, int c, int d, int t){
        boolean isPosOk = true;

        if(d == 1){
            if(c+t-1 >= 10){
                isPosOk = false;
            }
            else{
                for(int i = 0; i < t; i++){
                    if(grille[l][c+i] != 0){
                        isPosOk = false;
                    }
                }
            }
        }

        if(d == 2){
            if(l+t-1 >= 10){
                isPosOk = false;
            }
            else{
                for(int i = 0; i < t; i++){
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
        int[] tailleBateaux = new int[]{0,5,4,3,3,2};

        int l = randRange(0,10);
        int c = randRange(0,10);
        int d = randRange(1,3);

        for(int i = 1; i < 6; i++){
            while(posOk(grilleOrdi, l, c, d, tailleBateaux[i]) != true) {
                l = randRange(0, 10);
                c = randRange(0, 10);
                d = randRange(1, 3);
            }
            if (posOk(grilleOrdi, l, c, d, tailleBateaux[i]) == true) {
                initBateaux(grilleOrdi,l, c, d, tailleBateaux[i], i);
            }
        }
    }

    public static void initGrilleJeu(){
        String[] tableauDeBateaux = new String[6];
        tableauDeBateaux[1] = "porte-avions";
        tableauDeBateaux[2] = "croiseur";
        tableauDeBateaux[3] = "contre-torpilleurs";
        tableauDeBateaux[4] = "sous-marin";
        tableauDeBateaux[5] = "torpilleur";

        int[] tailleBateaux = new int[]{ 0,5,4,3,3,2 };

        String expressionRegLettre = "^[A-J]";
        String expressionRegChiffre = "^[1-9]|10";
        String expresssionRegSens = "^[1-2]";

        String lettreUtilisateur;
        String chiffreUtilisateur;
        String sensUtilisateur;

        for(int i = 1; i < 6; i++){
            do {
                System.out.println("Entrez la lettre pour le " + tableauDeBateaux[i]);
                Scanner scanner = new Scanner(System.in);
                lettreUtilisateur = scanner.nextLine();
                while (!lettreUtilisateur.matches(expressionRegLettre)) {
                    System.out.println("Erreur. Vous n'avez pas saisi une lettre entre A et J. Veuillez recommencer");
                    System.out.println("Entrez la lettre pour le " + tableauDeBateaux[i]);
                    lettreUtilisateur = scanner.nextLine();
                }

                System.out.println("Entrez le chiffre pour le " + tableauDeBateaux[i]);
                chiffreUtilisateur = scanner.nextLine();
                while (!chiffreUtilisateur.matches(expressionRegChiffre)) {
                    System.out.println("Erreur. Vous n'avez pas saisi un chiffre entre 1 et 10. Veuillez recommencer");
                    System.out.println("Entrez le chiffre pour le " + tableauDeBateaux[i]);
                    chiffreUtilisateur = scanner.nextLine();
                }

                System.out.println("Voulez-vous qu'il soit horizontal (1) ou vertical (2) ?");
                sensUtilisateur = scanner.nextLine();
                while (!sensUtilisateur.matches(expresssionRegSens)) {
                    System.out.println("Erreur. Vous n'avez pas saisi un chiffre entre 1 et 2. Veuillez recommencer");
                    System.out.println("Voulez-vous qu'il soit horizontal (1) ou vertical (2) ?");
                    sensUtilisateur = scanner.nextLine();
                }


                System.out.println(Integer.parseInt(chiffreUtilisateur)-1);
                System.out.println((int) lettreUtilisateur.charAt(0) - 65);
                System.out.println(Integer.parseInt(sensUtilisateur));
                System.out.println(tailleBateaux[i]);

                if(posOk(grilleJeu, Integer.parseInt(chiffreUtilisateur)-1,(int) lettreUtilisateur.charAt(0) - 65, Integer.parseInt(sensUtilisateur),tailleBateaux[i]) != true){
                    System.out.println("Erreur. Le bateau ne rentre pas dans la grille. Veuillez recommencer");
                }

            } while(posOk(grilleJeu, Integer.parseInt(chiffreUtilisateur)-1,(int) lettreUtilisateur.charAt(0) - 65, Integer.parseInt(sensUtilisateur),tailleBateaux[i]) != true);

            initBateaux(grilleJeu, Integer.parseInt(chiffreUtilisateur)-1, (int) lettreUtilisateur.charAt(0) - 65, Integer.parseInt(sensUtilisateur), tailleBateaux[i], i);
        }
    }

    public static void initBateaux(int grille[][], int l, int c, int d, int t, int typeDeBateau){
        if (d == 1){
            for(int i = 0; i<t; i++){
                grille[l][c+i] = typeDeBateau;
            }
        }
        if (d == 2){
            for(int i = 0; i<t; i++){
                grille[l+i][c] = typeDeBateau;
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

    public static boolean couler(int[][] grille, int numBateau){
        boolean estCouler = true;

        for(int i = 0; i < grille.length; i++){
            for(int j = 0; j < grille.length; i++){
                if(grille[i][j] == numBateau){
                    estCouler = false;
                }
            }
        }

        return estCouler;
    }

    public static void mouvement(int[][] grille, int l, int c){
        if(grille[l][c] == 0){
            System.out.println("A l'eau !");
        }
        else{
            if(couler(grille, grille[l][c]) == true){
                System.out.println("Couler !");
            }
            else{
                System.out.println("Toucher !");
            }
            grille[l][c] = 6;
        }
    }

    public static int[] tirOrdinateur(){
        int[] tableauPosTir = new int[2];
        tableauPosTir[0] = randRange(0,10);
        tableauPosTir[1] = randRange(0,10);
        return tableauPosTir;
    }

    public static boolean vainqueur(int grille[][]){
        boolean isVainqueur = false;
        int nbBateauCouler = 0;

        for(int i = 1; i<6; i++){
            if(couler(grille, i) == true){
                nbBateauCouler = nbBateauCouler + 1;
            }
        }

        if(nbBateauCouler == 5){
            isVainqueur = true;
        }

        return isVainqueur;
    }

    public static void main(String[] args){

        //initGrilleJeu();

        initGrilleOrdi();
        AfficherGrille(grilleOrdi);

        initGrilleJeu();
        AfficherGrille(grilleJeu);
    }
}