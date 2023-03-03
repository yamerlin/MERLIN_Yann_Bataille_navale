import java.util.Random;
import java.util.Scanner;
/**
 * Nom du projet : MERLIN_Yann_Bataille_navale <br>
 * Nom du programme : bataille.java <br>
 * Ce programme est un jeu de bataille navale contre un ordinateur qui tire aléatoirement <br>
 * Réalisé dans le cadre du cours de programmation d'environnement en Java <br>
 * Auteur : MERLIN Yann <br>
 * Professeur : Martin CARIGNAN et Samuel LINDSAY <br>
 * Date de remise : 06/03/2023 <br>
 * CEGEP de Sept-Iles <br>
 * Session d'hiver 2023
 *
 * @author Yann MERLIN
 * @version 1
 */
public class bataille{

    /**
     * Tableau 2D d'entiers contenant les positions des tirs reçus et des bateaux de l'ordinateur
     */
    public static int [][] grilleOrdi = new int[10][10];

    /**
     * Tableau 2D d'entiers contenant les positions des tirs reçus et des bateaux du joueur
     */
    public static int [][] grilleJeu = new int[10][10];

    /**
     * Tableau 2D d'entiers contenant les positions des tirs déjà effectués par le joueur durant la partie
     */
    public static int [][] grilleAffichage = new int[10][10];

    /**
     * Fonction utilisée pour l'initialisation des grilles. Elle teste si les positions choisies pour les bateaux sont correctes.
     * C'est-à-dire si le bateau ne dépasse pas de la grille ou s'il ne se superpose pas à un autre bateau.
     *
     * @param grille Grille sur laquelle le bateau est positionné
     * @param l Entier compris entre 1 et 10 indiquant le numéro de ligne
     * @param c Entier compris entre 1 et 10 indiquant le numéro de colonne
     * @param d Entier compris entre 1 et 2 indiquant le sens du bateau
     * @param t Entier compris entre 2 et 5 indiquant la taille du bateau
     * @return isPosOk booléen indiquant si la position du bateau est valide ou non
     */
    public static boolean posOk(int [][]grille, int l, int c, int d, int t){
        boolean isPosOk = true;

        /*
         * On vérifie si le bateau est horizontale
         */
        if(d == 1){
            /*
             * On vérifie si la position + la longueur du bateau est plus petite que la taille de la grille
             */
            if(c+t-1 >= 10){
                isPosOk = false;
            }
            else{
                /*
                 * On vérifie sur chaque case il n'y a pas déjà un autre bateau
                 */
                for(int i = 0; i < t; i++){
                    if(grille[l][c+i] != 0){
                        isPosOk = false;
                    }
                }
            }
        }

        /*
         * On vérifie si le bateau est vertical
         */
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

    /*
     * Objet utilisé pour la génération de nombres aléatoires
     */
    public static Random rand = new Random();

    /**
     * Fonction qui retourne un entier aléatoire compris entre a (inclus) et b (exclus).
     * @param a Entier borne inférieur
     * @param b Entier borne supérieur
     * @return Un nombre aléatoire compris entre a (inclus) et b (exclus).
     */
    public static int randRange(int a, int b){
        return rand.nextInt(b-a)+a;
    }

    /**
     * Fonction qui place aléatoirement les bateaux de l'ordinateur sur sa grille.
     * Utilise posOk pour valider la position de chaque bateau.
     * Elle ne retourne rien puisqu'elle modifie seulement (indirectement, grâce à la fonction initBateaux) la grille de l'ordinateur qui est une variable commune à toute la classe bataille.
     */
    public static void initGrilleOrdi(){
        /*
         * Tableau contenant les tailles de chaque bateau, il commence à la position 1 au lieu de commencer à la position 0 pour correspondre au numéro du bateau (1,2,3,4 ou 5)
         */
        int[] tailleBateaux = new int[]{0,5,4,3,3,2};

        /*
         * On commence par assigner des valeurs aléatoires aux variables ligne, colonne et sens du bateau.
         */
        int l = randRange(0,10);
        int c = randRange(0,10);
        int d = randRange(1,3);

        /*
         * On répète ensuite l'opération pour chacun des 5 bateaux, tant que leur position n'est pas validée par la fonction posOk
         */
        for(int i = 1; i < 6; i++){
            while(posOk(grilleOrdi, l, c, d, tailleBateaux[i]) != true) {
                l = randRange(0, 10);
                c = randRange(0, 10);
                d = randRange(1, 3);
            }
            /*
             * Si la position est validée, on peut alors envoyer les informations du bateau à la fonction initBateaux qui va s'occuper de le placer sur la grille.
             */
            if (posOk(grilleOrdi, l, c, d, tailleBateaux[i]) == true) {
                initBateaux(grilleOrdi,l, c, d, tailleBateaux[i], i /*Numéro du bateau*/);
            }
        }
    }

    /**
     * Fonction qui demande à l'utilisateur de positionner ses bateaux, puis les initialise grâce à initBateaux
     */
    public static void initGrilleJeu(){
        /*
         * Tableau contenant le nom de chaque bateau pour simplifier le code et éviter d'écrire 5 fois la question avec seulement le nom du bateau qui change
         */
        String[] tableauDeBateaux = new String[6];
        tableauDeBateaux[1] = "porte-avions";
        tableauDeBateaux[2] = "croiseur";
        tableauDeBateaux[3] = "contre-torpilleurs";
        tableauDeBateaux[4] = "sous-marin";
        tableauDeBateaux[5] = "torpilleur";

        /*
         * Tableau contenant les tailles de chaque bateau, il commence à la position 1 au lieu de commencer à la position 0 pour correspondre au numéro du bateau (1,2,3,4 ou 5)
         */
        int[] tailleBateaux = new int[]{ 0,5,4,3,3,2 };

        /*
         * Expressions régulières pour la vérification des entrées de l'utilisateur
         */
        String expressionRegLettre = "^[A-J]";
        String expressionRegChiffre = "^[1-9]|10";
        String expressionRegSens = "^[1-2]";

        /*
         * Variables contenant les entrées utilisateur
         */
        String lettreUtilisateur;
        String chiffreUtilisateur;
        String sensUtilisateur;

        /*
         * À répéter pour chacun des 5 bateaux
         */
        for(int i = 1; i < 6; i++){
            do {
                System.out.println(" ");
                System.out.println("Entrez la lettre majuscule (A - J) pour le " + tableauDeBateaux[i]);
                Scanner scanner = new Scanner(System.in);
                /*
                 * Lecture entrée utilisateur
                 */
                lettreUtilisateur = scanner.nextLine();
                /*
                 * Répéter tant qu'il n'y a pas match avec l'expression régulière
                 */
                while (!lettreUtilisateur.matches(expressionRegLettre)) {
                    System.out.println("Erreur. Vous n'avez pas saisi une lettre entre A et J. Veuillez recommencer");
                    System.out.println("Entrez la lettre pour le " + tableauDeBateaux[i]);
                    lettreUtilisateur = scanner.nextLine();
                }

                System.out.println("Entrez le chiffre (1 - 10) pour le " + tableauDeBateaux[i]);
                /*
                 * Lecture entrée utilisateur
                 */
                chiffreUtilisateur = scanner.nextLine();
                /*
                 * Répéter tant qu'il n'y a pas match avec l'expression régulière
                 */
                while (!chiffreUtilisateur.matches(expressionRegChiffre)) {
                    System.out.println("Erreur. Vous n'avez pas saisi un chiffre entre 1 et 10. Veuillez recommencer");
                    System.out.println("Entrez le chiffre pour le " + tableauDeBateaux[i]);
                    chiffreUtilisateur = scanner.nextLine();
                }

                System.out.println("Voulez-vous qu'il soit horizontal (1) ou vertical (2) ?");
                /*
                 * Lecture entrée utilisateur
                 */
                sensUtilisateur = scanner.nextLine();
                /*
                 * Répéter tant qu'il n'y a pas match avec l'expression régulière
                 */
                while (!sensUtilisateur.matches(expressionRegSens)) {
                    System.out.println("Erreur. Vous n'avez pas saisi un chiffre entre 1 et 2. Veuillez recommencer");
                    System.out.println("Voulez-vous qu'il soit horizontal (1) ou vertical (2) ?");
                    sensUtilisateur = scanner.nextLine();
                }

                if(posOk(grilleJeu, Integer.parseInt(chiffreUtilisateur)-1,(int) lettreUtilisateur.charAt(0) - 65, Integer.parseInt(sensUtilisateur),tailleBateaux[i]) != true){
                    System.out.println("Erreur. Le bateau ne rentre pas dans la grille. Veuillez recommencer");
                }
            /*
             * Validation avec posOk
             * Pour envoyer la ligne, il suffit convertir la chaine de caractères en entier (avec Integer.parseInt()) et de soustraire 1 au chiffre de l'utilisateur, car un tableau commence à 0 et non à 1.
             * Pour envoyer la colonne, il faut convertir le caractère en int (donc en sa valeur ASCII), puis retirer 65, car "A" est 65 en ASCCI, donc (int)A - 65 = 0 ce qui correspond à la colonne 0.
             * Pour envoyer le sens du bateau, il suffit convertir la chaine de caractères en entier.
             */
            } while(posOk(grilleJeu, Integer.parseInt(chiffreUtilisateur)-1,(int) lettreUtilisateur.charAt(0) - 65, Integer.parseInt(sensUtilisateur),tailleBateaux[i]) != true);

            /*
             * Si la position est validée, on peut poser le bateau sur la grille grâce à initBateaux
             * On convertit les données de la même manière qu'avec posOk
             */
            initBateaux(grilleJeu, Integer.parseInt(chiffreUtilisateur)-1, (int) lettreUtilisateur.charAt(0) - 65, Integer.parseInt(sensUtilisateur), tailleBateaux[i], i);

            System.out.println("Votre grille : ");
            AfficherGrille(grilleJeu);
        }
    }

    /**
     *Fonction qui place les bateaux sur la grille, en modifiant les 0 par les numéros des bateaux.
     * @param grille Grille sur laquelle on doit placer le bateau
     * @param l Entier compris entre 1 et 10 indiquant le numéro de ligne
     * @param c Entier compris entre 1 et 10 indiquant le numéro de colonne
     * @param d Entier compris entre 1 et 2 indiquant le sens du bateau
     * @param t Taille
     * @param typeDeBateau Numéro correspondant au type du bateau à placer
     */
    public static void initBateaux(int grille[][], int l, int c, int d, int t, int typeDeBateau){
        /*
         * On teste si le bateau est horizontale
         */
        if (d == 1){
            for(int i = 0; i<t; i++){
                /*
                 * On incrémente la coordonnée de la colonne, de 0 a la taille du bateau, pour remplacer chaque 0 par le numéro du bateau
                 */
                grille[l][c+i] = typeDeBateau;
            }
        }
        /*
         * On teste si le bateau est vertical
         */
        if (d == 2){
            for(int i = 0; i<t; i++){
                /*
                 * On incrémente la coordonnée de la ligne, de 0 a la taille du bateau, pour remplacer chaque 0 par le numéro du bateau
                 */
                grille[l+i][c] = typeDeBateau;
            }
        }
    }

    /**
     *Fonction permettant l'affichage des grilles de jeu en console
     * @param grille Grille à afficher
     */
    public static void AfficherGrille(int grille[][]){

        /*
         * Variable utilisée pour afficher le numéro de la ligne
         */
        int numero_de_la_ligne = 0;

        /*
         * Affichage des lettres des colonnes
         */
        System.out.print("   A B C D E F G H I J");

        /*
         * Défiler chaque ligne
         */
        for(int i = 0; i <10; i++){
            /*
             * Incrémenter le numéro de la ligne
             */
            numero_de_la_ligne++;

            /*
             * Aller a la ligne suivante
             */
            System.out.println("");
            System.out.print(numero_de_la_ligne);

            /*
             * Pour un affichage propre, on ne veut pas mettre d'espace apres le numéro 10, car il prend deja deux caractères de place
             */
            if(numero_de_la_ligne != 10){
                System.out.print(" ");
            }

            /*
             * Défiler chaque colonne
             */
            for(int j = 0; j <10; j++) {
                System.out.print(" ");

                /*
                 * Affichage de la grille
                 */
                System.out.print(grille[i][j]);
            }
        }
    }

    /**
     * Fonction qui vérifie si le bateau est coulé en cherchant si un numéro du bateau est toujours présent sur la grille.
     * @param grille La grille dans laquelle on cherche le bateau
     * @param numBateau Le numéro du bateau à chercher
     * @return Vrai si le bateau n'est plus présent sur la grille (=coulé) et faux s'il est toujours là (pas coulé)
     */
    public static boolean couler(int[][] grille, int numBateau){
        boolean estCouler = true;

        /*
         * Défiler sur chaque élément de chaque ligne
         */
        for(int i = 0; i < 10; i++){
            /*
             * Défiler sur chaque élément de chaque colonne
             */
            for(int j = 0; j < 10; j++){
                /*
                 * Tester si l'élément correspond au numéro du bateau
                 */
                if(grille[i][j] == numBateau){
                    /*
                     * S'il correspond alors il n'est pas coulé et l'on doit retourner faux
                     */
                    estCouler = false;
                }
            }
        }

        return estCouler;
    }

    /**
     * Fonction qui inscrit chaque tire sur la grille et vérifie si un bateau est touché
     * @param grille Grille sur laquelle on tire
     * @param l Entier compris entre 1 et 10 indiquant le numéro de ligne
     * @param c Entier compris entre 1 et 10 indiquant le numéro de colonne
     */
    public static void mouvement(int[][] grille, int l, int c){
        /*
         * Récupérer l'élément du tableau sur lequel le tire est effectué
         */
        int numBateau = grille[l][c];
        /*
         * Si l'élément est un 0 alors le tir est à l'eau
         */
        if(grille[l][c] == 0){
            System.out.println("A l'eau !");
        }
        else{
            /*
             * Sinon c'est un touché si la fonction couler retourne faux et un coulé si la fonction couler retourne un true
             */
            grille[l][c] = 6;
            if(couler(grille, numBateau) == true){
                System.out.println("Couler !");
            }
            else {
                System.out.println("Toucher !");
            }
        }
    }

    /**
     * Fonction qui simule un tir de l'ordinateur en choisissant aléatoirement deux entiers entre 0 et 10 pour les numéros de ligne et de colonne
     * @return Un tableau contenant les deux entiers des coordonnées de ligne et colonne
     */
    public static int[] tirOrdinateur(){
        int[] tableauPosTir = new int[2];
        tableauPosTir[0] = randRange(0,10);
        tableauPosTir[1] = randRange(0,10);
        return tableauPosTir;
    }

    /**
     * Fonction qui vérifie si l'un des joueurs a remporté la partie
     * @param grille Correspond à la grille du joueur pour lequel on souhaite vérifier la victoire
     * @return True si le joueur a gagné, faux si le jouer n'a pas encore gagné
     */
    public static boolean vainqueur(int grille[][]){
        boolean isVainqueur = true;

        /*
         * On vérifie tout le tableau à la recherche d'un élément différent de 0 (de l'eau) ou 6 (un bateau coulé) qui signifierait que la partie n'est pas encore gagnée (un bateau toujours présent)
         */
        for(int i = 0; i<10; i++){
            for (int j=0; j<10; j++){
                if(grille[i][j] != 0 && grille[i][j] != 6){
                    isVainqueur = false;
                }
            }
        }

        return isVainqueur;
    }

    /**
     * Fonction qui lance une partie en appelant tour à tour toutes les fonctions nécessaires au fonctionnement du jeu
     */
    public static void engagement(){
        AfficherGrille(grilleJeu);

        Scanner scanner = new Scanner(System.in);

        /*
         * Variables utilisées pour stocker les tirs de l'ordinateur, tirOrdiL = ligne et tirOrdiC = colonne
         */
        int tirOrdiL;
        int tirOrdiC;

        /*
         * Expressions régulières utilisées pour la vérification des entrées de l'utilisateur
         */
        String expressionRegLettre = "^[A-J]";
        String expressionRegChiffre = "^[1-9]|10";

        /*
         * Variables utilisées pour stocker les entrées de l'utilisateur
         */
        String lettreUtilisateur;
        String chiffreUtilisateur;

        /*
         * Initialisation des grilles des joueurs
         */
        initGrilleOrdi();
        initGrilleJeu();

        /*
         * On joue tant que personne n'est vainqueur
         */
        while(vainqueur(grilleJeu) != true && vainqueur(grilleOrdi) != true){
            /*
             * Tour de l'ordinateur
             */
            System.out.println(" ");
            System.out.println("Tour de l'ordinateur : ");
            tirOrdiL = tirOrdinateur()[0];
            tirOrdiC = tirOrdinateur()[1];
            mouvement(grilleJeu, tirOrdiL, tirOrdiC);

            /*
             * On vérifie si l'ordinateur à gagner avant de passer au tour du joueur
             */
            if(vainqueur(grilleJeu) != true) {
                /*
                 * Tour du joueur
                 */
                System.out.println("Tour du joueur : ");

                /*
                 * On demande une ligne de tir tant que celle-ci ne match pas l'expression régulière
                 */
                do {
                    System.out.println("Veuillez entrer la lettre majuscule (A - J) de votre position de tir : ");
                    lettreUtilisateur = scanner.nextLine();
                    if (!lettreUtilisateur.matches(expressionRegLettre)) {
                        System.out.println("Ce n'est pas une lettre majuscule comprise entre A et J, veuillez recommencer.");
                    }
                } while (!lettreUtilisateur.matches(expressionRegLettre));

                /*
                 * On demande une colonne de tir tant que celle-ci ne match pas l'expression régulière
                 */
                do {
                    System.out.println("Veuillez entrer le chiffre (1 - 10) de votre position de tir : ");
                    chiffreUtilisateur = scanner.nextLine();
                    if (!chiffreUtilisateur.matches(expressionRegChiffre)) {
                        System.out.println("Ce n'est pas un chiffre compris entre 1 et 10, veuillez recommencer.");
                    }
                } while (!chiffreUtilisateur.matches(expressionRegChiffre));

                /*
                 * On envoie les positions de tir à la fonction mouvement de la manière suivante :
                 * Pour envoyer la ligne, il suffit convertir la chaine de caractères en entier (avec Integer.parseInt()) et de soustraire 1 au chiffre de l'utilisateur, car un tableau commence à 0 et non à 1.
                 * Pour envoyer la colonne, il faut convertir le caractère en int (donc en sa valeur ASCII), puis retirer 65, car "A" est 65 en ASCCI, donc (int)A - 65 = 0 ce qui correspond à la colonne 0.
                 */
                mouvement(grilleOrdi, Integer.parseInt(chiffreUtilisateur) - 1, (int) lettreUtilisateur.charAt(0) - 65);
                /*
                 * On inscrit le tir sur la grille d'affichage en tant que tir à l'eau (un 1)
                 */
                grilleAffichage[Integer.parseInt(chiffreUtilisateur) - 1][(int) lettreUtilisateur.charAt(0) - 65] = 1;
                /*
                 * S'il y avait un bateau à cette position alors le tir sur la grille d'affichage devient un 6
                 */
                if(grilleOrdi[Integer.parseInt(chiffreUtilisateur) - 1][(int) lettreUtilisateur.charAt(0) - 65] != 0){
                    grilleAffichage[Integer.parseInt(chiffreUtilisateur) - 1][(int) lettreUtilisateur.charAt(0) - 65] = 6;
                }

                /*
                 * Afficher la grille d'affichage pour permettre à l'utilisateur de voir les endroits de ses tirs
                 */
                System.out.println("La grille de vos tirs (6 = bateau touche, 1 = tir a l'eau) : ");
                AfficherGrille(grilleAffichage);
            }
            else{
                System.out.println("");
                System.out.println("Victoire de l'ordinateur !");
            }
        }
        if(vainqueur(grilleOrdi) == true) {
            System.out.println("");
            System.out.println("Vous remportez la partie !");
        }
    }

    /**
     * Fonction main exécutée en première, ne sert qu'à appeler la fonction engagement.
     * @param args
     */
    public static void main(String[] args){
        engagement();
    }
}