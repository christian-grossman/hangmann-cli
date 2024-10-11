package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    //TODO: Set word and input to lowercase
    //TODO: Add functionality to allow guessing of entire word
    //TODO: Keep track of total number of guesses per round
    //TODO: Add hangman image to print screen
    //TODO: Give word on failure

    //Word bank
    //TODO: Alter to accept file input or API
    public static String[] words = {"HALTING", "CABLE", "VASE", "LOSS", "COPPER", "ELEGANT"};
    public static Random random = new Random();
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<String> arr = new ArrayList<>();

    //Function to create the word display in play
    public static void printBoard(ArrayList<String> arr, int guesses){
        for(String x: arr){
            System.out.print(x);
        }
        if(guesses == 1){
            System.out.println(" " + guesses + " guess left.");
        }else {
            System.out.println(" " + guesses + " guesses left.");
        }
    }

    //Check end game conditions (successful word find or out of guesses)
    //Break out of main method if met, to restart game (return false)
    public static boolean checkWinner(ArrayList<String> arr, String theWord, int guesses) {
        //Create string to hold current guess
        String winnerCheck = "";
        //Populate string with guesses
        for(String x: arr){
            winnerCheck += x;
        }
        //Check win condition
        if(winnerCheck.equals(theWord)){
            System.out.println("You won!");
            System.out.println();
            return false;
        }
        //Check loss condition
        else if(guesses==0){
            System.out.println("No guesses left.");
            System.out.println("Game Over.");
            System.out.println();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        //while(true) creates loop to restart game upon completion
        while(true){
            //Create string to hold word in play
            String theWord = "";
            //Clear any existing data in guessing array for new game
            arr.clear();

            //Set of guesses for new game
            int guesses = 6;

            //Welcome menu
            System.out.println("Welcome to Hangman, ready to play?");
            System.out.println();
            System.out.println("Press n for new game.");
            System.out.println("Press q to quit.");
            String menu = scanner.nextLine();

            //Quit
            if(menu.equals("q")){
                System.out.println("Exiting...");
                break;
            }
            //Play
            else if(menu.equals("n")){

                //Find random word from source
                int index = random.nextInt(words.length);

                //Set as word in play
                theWord += words[index];

                //Populate guessing array with correct number of blank spaces
                for(int i = 0; i < theWord.length(); i++){
                    arr.add("_");
                }

                //Display board to player
                printBoard(arr, guesses);

                //Gameplay loop
                while(checkWinner(arr, theWord, guesses)){
                    System.out.println("Enter a letter: (A-Z)");
                    String answer = scanner.nextLine();
                    //Checks valid input
                    if(!Pattern.matches("[A-Z]", answer)){
                        System.out.println("Only capital letters.");
                        //Back to beginning of loop
                        continue;
                    }

                    //Stores answer as char for comparison
                    char letter = answer.charAt(0);
                    //Check for repeat guess
                    if(arr.contains(answer)){
                        System.out.println("Already Guessed");
                    }
                    //Successful guess removes blank and inserts correct character into all matching indices
                    else{
                        for(int i = 0; i < theWord.length(); i++){
                            if(theWord.charAt(i) == letter){
                                arr.remove(i);
                                arr.add(i, answer);
                            }
                        }

                        //Incorrect guess reduces remaining guesses
                        if(!arr.contains(answer)){
                            guesses -= 1;
                        }
                    }
                    //Display board after each guess
                    //TODO: drawHangman function to display image
                    System.out.println();
                    printBoard(arr, guesses);
                    System.out.println();
                }
            }
        }
    }
}