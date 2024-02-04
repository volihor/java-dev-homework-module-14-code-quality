package goit.edu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameXO {
    static Scanner scan = new Scanner(System.in);
    static char[] box = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    static boolean boxAvailable = true;
    static boolean isGameRunning = true;

    public void start() {
        char chX = 'X';
        char chO = 'O';

        System.out.println("\nEnter box number to select. Enjoy!");

        printNumericBoxesField(box);
        clearBoxesField(box);

        while (isGameRunning) {

            // Get input from player to choose the box
            playerChoice(chX, chO);
            // Check if player won
            checkForWinAndAnnounceWinner(chX);

            // Get random number from PC to choose the box
            getRandomNumber(chX, chO);
            // Check if PC won
            if (isGameRunning) {
                checkForWinAndAnnounceWinner(chO);
            }

            // Draw if box is fool and exit the game
            if (isGameRunning && !boxAvailable) {
                printNumericBoxesField(box);
                announceTheWinner((byte) 3);
            }

            if (isGameRunning)
                printNumericBoxesField(box);
        }
    }

    private void checkForWinAndAnnounceWinner(char ch) {
        if (isMatchedForWin(ch, box)) {
            printNumericBoxesField(box);
            byte winner;
            if (ch == 'X') {
                winner = 1;
            } else {
                winner = 2;
            }
            // Checking for the winner if box is fool and exit the game
            announceTheWinner(winner);
            isGameRunning = false;
        }
    }

    private void printNumericBoxesField(char[] box) {
        // Invert matrix for PC
        System.out.println(" " + box[6] + " | " + box[7] + " | " + box[8] + " ");
        System.out.println("-----------");
        System.out.println(" " + box[3] + " | " + box[4] + " | " + box[5] + " ");
        System.out.println("-----------");
        System.out.print(" " + box[0] + " | " + box[1] + " | " + box[2] + " \n");
    }

    private void clearBoxesField(char[] box) {
        for (int i = 0; i < 9; i++) {
            box[i] = ' ';
        }
    }

    private void announceTheWinner(byte winner) {
        switch (winner) {
            case 1 -> System.out.println("\nYou won the game!");
            case 2 -> System.out.println("\nYou lost the game!");
            default -> System.out.println("\nIt's a draw!");
        }
        System.out.println("Created by Shreyas Saha. Thanks for playing!\n");
        isGameRunning = false;
    }

    private void playerChoice(char chX, char chO) {
        byte input = 0;
        if (boxAvailable) {
            do {
                input = getPlayerInput(GameXO.scan, input);

            } while (!isValidPlayerInput(chX, chO, input));
        }
        checkAndChangeBoxesAvailability();
    }

    private byte getPlayerInput(Scanner scan, byte input) {
        do {
            System.out.println("Please enter number from 1 to 9 inclusive.");
            try {
                input = scan.nextByte();
            } catch (InputMismatchException e) {
                System.out.println("You entered Invalid data. Please enter one digit from 1 to 9 inclusive.");
                // To avoid the loop on the next iteration ! Because scan.nextByte() reads the same token again and throws the same exception again.
                scan.next();
            }
        } while (1 > input || input > 9);
        return input;
    }

    private boolean isValidPlayerInput(char chX, char chO, byte input) {
        if (input > 0 && input < 10) {
            if (box[input - 1] == chX || box[input - 1] == chO)
                System.out.println("That one is already in use. Enter another.");
            else {
                setPlayerInput(chX, box, input);
                return true;
            }
        } else
            System.out.println("Invalid input. Enter again.");
        return false;
    }

    private void setPlayerInput(char ch, char[] box, byte input) {
        box[input - 1] = ch;
    }

    private void getRandomNumber(char chX, char chO) {
        byte rand;
        if (boxAvailable) {
            while (true) {
                rand = (byte) (Math.random() * 9 + 1);
                if (box[rand - 1] != chX && box[rand - 1] != chO) {
                    box[rand - 1] = chO;
                    break;
                }
            }
        }
        checkAndChangeBoxesAvailability();
    }

    private boolean isMatchedForWin(char c, char[] box) {
        return (box[0] == c && box[1] == c && box[2] == c)
                || (box[3] == c && box[4] == c && box[5] == c)
                || (box[6] == c && box[7] == c && box[8] == c)
                || (box[0] == c && box[3] == c && box[6] == c)
                || (box[1] == c && box[4] == c && box[7] == c)
                || (box[2] == c && box[5] == c && box[8] == c)
                || (box[0] == c && box[4] == c && box[8] == c)
                || (box[2] == c && box[4] == c && box[6] == c);
    }

    private void checkAndChangeBoxesAvailability() {
        // if no more empty box
        int count = 0;
        for (char c : box) {
            if (c == ' ') {
                count++;
                break;
            }
        }
        boxAvailable = (count != 0);
    }
}
