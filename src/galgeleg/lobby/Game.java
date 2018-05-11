package galgeleg.lobby;

import galgeleg.logik.GalgeInterface;

import java.rmi.RemoteException;
import java.util.Scanner;

public class Game {

    private GalgeInterface game;

    private Scanner scan = new Scanner(System.in);
    private String input;

    public Game(GalgeInterface galgeInterface) {
        this.game = galgeInterface;
    }

    public void mainGame () {
        System.out.println("\n\tBrugbare kommandoer: " +
                "\n1 - Start et nyt spil med flere mulige ord" +
                "\n9 - Luk forbindelsen og konsolen" +
                "\n");


        while (true) {
            System.out.println("Indtast bogstav");
            input = scan.next();
            if (input.equalsIgnoreCase("?") || Character.isDigit(input.charAt(0))) {
                helpPage(input);
            } else {
                try {
                    guessWord(input);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void guessWord(String input) throws RemoteException {
        game.guessLetter(input);
        System.out.println(game.outputToClient());

        game.logStatus();
        System.out.println(game.outputToClient());

        gameOver();
    }

    private void gameOver() throws RemoteException {
        if (game.getGameOver()) {
            setScore(game.getScore());
            System.out.println("Vil du spille igen? Y/N");
            input = scan.next();
            if (input.equalsIgnoreCase("y")) {
                game.restart();
            } else {
                //ToDo Exit the game, not the entire console.
                System.exit(1);
            }
        }
    }

    private void setScore(int score) {
        //ToDo
        //Score is time spend, which substract a value from the max.
        //Call getWrongLetterCount and substract a value from the max.
        //Remaining is highscore, return it.
    }

    private void helpPage(String input) {

        String helpInput;
        Boolean helperPage = true;

        while (helperPage) {
            if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("2") ||
                    input.equalsIgnoreCase("4") || input.equalsIgnoreCase("9") ||
                    input.equalsIgnoreCase("?")) {
                helpInput = input.toLowerCase();
            } else {
                helpMessage();
                helpInput = scan.next().toLowerCase();
            }

            switch (helpInput) {
                case "1":
                    try {
                        game.restart();
                    } catch (Exception e) {
                        System.out.println("Noget gik galt. Beklager!");
                        e.printStackTrace();
                    }
                    break;
                case "9":
                    try {
                        game.restart();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    System.exit(1);
                default:
                    helpMessage();
            }
        }
    }

    private static void helpMessage() {
        System.out.println("Du har tilgået hjælp-siden. Venligst indtast en af følgende kommandoer: " +
                "\n1 - Start et nyt spil med flere mulige ord" +
                "\n9 - Luk forbindelsen og konsolen");
    }

}
