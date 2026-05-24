package MorePiont;
import java.util.Random;
import java.util.Scanner;

public class MorePiont {

    /*Améliorations: 
    - Commentaires plus clairs
    - Version backtracking pour jouer à un seul joueur 
     */

    public static int[][] grille = new int[3][3]; // Variable globale (grille de jeux)

    // Constantes
    public static final int X = 1;
    public static final int O = 2;


    
    public static void jouer() {

        /*
            JEUX DU MORE PIONT
            Joueur 1 = X (1)
            Joueur 2 = O (2)
            Case vide (0)
        */


        String RESET = "\u001B[0m"; // Fonctionne avec n'importe quel langage qui agit dans le terminal
        String RED = "\u001B[31m"; // 4 à la place de 3 pour le fond
        String CYAN = "\u001B[36m";

        Random random = new Random();
        int numeroFirst = random.nextInt(2) + 1;
        int joueurActuel = numeroFirst;
        System.out.println(CYAN + "Le joueur n° " + numeroFirst + " Commence. " + RESET);
        Scanner input = new Scanner(System.in);

        


        // Erreur : erreur de logique, s'arrête si un gagnant et si la grille et pleine
        //  while (!gagnant() || !grillePleine())


        while (!gagnant() && !grillePleine()) { // On joue tant que personne n'a gagné et que la grille n'est pas pleine
            // On part du fait que les joueurs savent qui dois jouer


            Case caseprov; // Préparation avant la boucle, variable vide


                do {

                        // Le joueur choisit une ligne
                    System.out.println(CYAN + "Choisissez une ligne comprise dans : (1,2,3). " + RESET);
                    int ligne = input.nextInt();
                    while (ligne < 1 || ligne > 3) {
                        System.out.println(RED + "X Erreur : (Ligne). " + RESET);
                        System.out.println(CYAN + "Veuillez choisir une valeur comprise dans (1,2,3). " + RESET);
                        ligne = input.nextInt();
                    }
                    ligne -= 1; // Car les lignes sont comprises entre 0 et 2

                    
                    // Le joueur choisit une colonne
                    System.out.println(CYAN + "Maintenant veuillez choisir une colonne comprise dans : (1,2,3). " + RESET);
                    int colonne = input.nextInt();
                    while (colonne < 1 || colonne > 3) {
                        System.out.println(RED + "X Erreur : (Colonne). " + RESET);
                        System.out.println(CYAN + "Veuillez choisir une valeur comprise dans (1,2,3). " + RESET);
                        colonne = input.nextInt();
                    }
                    colonne -= 1;


                    // Il faut vérifier que la case est valide
                    caseprov = new Case(ligne, colonne);


                    if (!caseValide(caseprov)) {
                        System.out.println(RED + "X Erreur : case invalide, veuillez réessayer. " + RESET); // \u001B[0m Code ANSI (indique au terminal comment afficher les données qui suivent)
                    }

                }   while (!caseValide(caseprov)); // Attention à la portée de la variable qui est bloquée dans le do (variable fantôme) -> bidouillage en haut


            

            if (joueurActuel % 2 == 0) { // Joueur 2
                grille[caseprov.x][caseprov.y] = O;
            }   else { // Joueur 1
                grille[caseprov.x][caseprov.y] = X;
            }

            joueurActuel ++;

            // Après chaque tour on affiche la grille
            afficherGrille();


        }


        



        // Un gagnant ou liste pleine

        if (gagnant()) {
            System.out.println(" \u001B[36m Le joueur n° " + joueurGagnant() + " à gagné. \u001B[0m");
            return; // On sort de la fonction
        }

        // Cas si pas de gagant mais grille pleine
        if (grillePleine()) {
            System.out.println(" \u001B[36m Vous pouvez rejouer si vous le souhaitez ! \u001B[0m");
            return;
        }


    }


    public static boolean grillePleine() {

        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                if (grille[i][j] == 0) { // Cas d'une case vide
                    return false;
                }
            }
        }

        return true;

    }


    public static boolean gagnant() {

        /*
         ________
        |__|__|__|
        |__|__|__|
        |__|__|__|
        */

        // Cas verticale en partant du haut

        //Attention, vérifier que la valeur de la case n'est pas 0 (Configuration initiale)

        for (int i = 0; i < grille.length; i++) {
            if (grille[0][i] != 0 && grille[0][i] == grille[1][i] &&  grille[0][i] == grille[2][i]) {
                return true; // Trois mêmes signes alignés
            }
        }

        // Cas horizontale en partant de la gauche

        for (int i = 0; i < grille.length; i++) {
            if (grille[i][0] != 0 && grille[i][0] == grille[i][1] &&  grille[i][0] == grille[i][2] ) {
                return true; // Trois mêmes signes alignés
            }
        }

        // Cas diagonales (Celle de gauche)

        // Remarques: Trop itératif (Trop géré cas par cas)
        if (grille[0][0] != 0 && grille[0][0] == grille[1][1] && grille[0][0] == grille[2][2]) {
            return true;
        }

        if (grille[0][2] != 0 && grille[0][2]  == grille[1][1] && grille[0][2] == grille[2][0]) {
            return true;
        }

        // Sinon on renvoie false;
        return false;


    }


     public static int joueurGagnant() {

        /*
         ________
        |__|__|__|
        |__|__|__|
        |__|__|__|
        */

        // Cas verticale en partant du haut

        for (int i = 0; i < grille.length; i++) {
            if (grille[0][i] != 0 && grille[0][i] == grille[1][i] && grille[0][i] == grille[2][i]) {
                return grille[0][i]; // Trois mêmes signes alignés
            }
        }

        // Cas horizontale en partant de la gauche

        for (int i = 0; i < grille.length; i++) {
            if (grille[i][0] != 0 && grille[i][0] == grille[i][1] && grille[i][0] == grille[i][2] ) {
                return grille[i][0]; // Trois mêmes signes alignés
            }
        }

        // Cas diagonales (Celle de gauche)

        // Remarques: Trop itératif (Trop géré cas par cas)
        if (grille[0][0] != 0 && grille[0][0] == grille[1][1] && grille[0][0] == grille[2][2]) {
            return grille[0][0];
        }

        if (grille[0][2] != 0 && grille[0][2] == grille[1][1] && grille[1][2] == grille[2][0]) {
            return grille[0][2];
        }

        // Sinon on renvoie 0;
        return 0;


    }



    public static boolean caseValide(Case caseprov) {

        return grille[caseprov.x][caseprov.y] == 0;

    }


    // Version GEMINI, revoir quand j'ai le temps

    public static void afficherGrille() {

        // ╔, ═, ╦, ╗, ╠, ╬, ╣, ╚, ╩, ╝, ║


        // 1. Le toit avec un PRINTLN pour descendre d'une ligne
            System.out.println("╔══╦══╦══╗");
    
                for (int i = 0; i < grille.length; i++) {
                    
                    // 2. On dessine les cases de la ligne i
                    for (int j = 0; j < grille[i].length; j++) {
                        // On affiche la barre de gauche et le pion (suivi d'un espace pour la largeur de 2)
                        if (grille[i][j] == 1) {
                            System.out.print("║" + "X" + " ");
                        }   else if(grille[i][j] == 2) {
                            System.out.print("║" + "O" + " ");
                        }   else {
                            System.out.print("║  ");
                        }
                       
                    }
                    
                    // 3. UNE FOIS la ligne de pions finie, on met la barre de fermeture TOUT À DROITE 
                    // et on descend d'une ligne avec PRINTLN
                    System.out.println("║");

                    // 4. Si ce n'est pas la dernière ligne, on met le séparateur
                    if (i < grille.length - 1) {
                        System.out.println("╠══╬══╬══╣");
                    }
                }
    
            // 5. Le sol final
            System.out.println("╚══╩══╩══╝");
    }



}

    /*System.out.println("╔══╦══╦══╗");
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                System.out.println("║" + grille[i][j] + "║" );
                if (j == grille.length - 1) {
                    System.out.println("║");
                }
            }
            if (i < grille.length - 1) {
                System.out.println("╠══╬══╬══╣");
            }
        }
        System.out.println("╚══╩══╩══╝"); */





    


    

