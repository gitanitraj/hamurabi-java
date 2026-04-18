import java.io.IOException;
import java.util.Scanner;

// this seems to be an example of a solution that mimics the original BASIC code the author was writing from.
//
// it's a great example of Very Bad Java.
// Do not write Java like this. If you do, do NOT tell people you went to Zip Code.
// I'm serious.
// (how the hell would you ever be able to TEST this piece of code?)
//
package hammurabi;               // package declaration 
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    public static void main(String\[\] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        // declare local variables here: grain, population, etc.
        int population = 100;
        int grain = 2800;
        int acres = 1000;
        int landPrice = 19;

        int year = 1;

        // statements go after the declations
        int starvedLastYear = 0;
        int immigrantsLastYear = 5;
        int harvestPerAcre = 3;
        int ratsAte = 200;

    }
    //other methods go here
    while (year <= 10) {
        printSummary(year, starvedLastYear, immigrantsLastYear, population, harvestPerAcre, ratsAte, grain, acres, landPrice);
        // decisions and calculations go here
        year++;
    }
}