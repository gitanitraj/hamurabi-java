package hammurabi;

import java.util.InputMismatchException;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    static Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) { // required in every Java program
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
        int acresPlantedLastYear = 0;
        int harvestedLastYear = 0;
        int ratsAte = 200;
        
    //other methods go here
        while (year <= 10) {
            
            printSummary(year, starvedLastYear, immigrantsLastYear,
             population, harvestPerAcre, ratsAte, grain, acres, landPrice, acresPlantedLastYear, harvestedLastYear);

            // Land Buy

            int acresToBuy = askHowManyAcresToBuy(landPrice, grain);
            acres += acresToBuy;
            grain -= acresToBuy * landPrice;

            // Land Sell
            int acresToSell = askHowManyAcresToSell(acres);
            acres -= acresToSell;
            grain += acresToSell * landPrice;

            // Grain Fed
            int grainFed = askHowMuchGrainToFeedPeople(grain);
            grain -= grainFed;

            // Plant Crops
            int acresPlanted = askHowManyAcresToPlant(acres, population, grain);
            grain -= acresPlanted * 2;

            // store for next year's summary
            acresPlantedLastYear = acresPlanted;

            // Plague
            int plague = plagueDeaths(population);
            population -= plague;

            // Starvation
            int starved = starvationDeaths(population, grainFed);
            population -= starved;

            //Uprising
            if (uprising(population + starved, starved)) {
                System.out.println("You have been overthrown!");
                finalSummary(population, acres, grain, starvedLastYear);
                return;
            }
            
            // Immigrants
            int immigrants = 0;
            if (starved == 0) {
                immigrants = immigrants(population,acres, grain);
                population += immigrants;
            }

            // Harvest
            int harvested = harvest(acresPlanted); 
            grain += harvested;

            // store for next year's summary
            harvestedLastYear = harvested;

            // compute yield per acre
            harvestPerAcre = (acresPlanted == 0) ? 0 : harvested / acresPlanted;

            //Rats
            ratsAte = grainEatenByRats(grain);
            grain -= ratsAte;

            //Land Price Update
            landPrice = newCostOfLand();

            //Update Tracking
            starvedLastYear = starved;
            immigrantsLastYear = immigrants;
        
            year++;

        }

        finalSummary(population, acres, grain, starvedLastYear);
}
           
        void printSummary(int year, int starved, int immigrants,int population, int yield, int rats, int grain, int acres, int price, int harvested, int acresPlanted) {

        System.out.println("\nO Anitra, ruler of the great Hammurabi!");
        System.out.println("You are in year " + year + " of your ten year rule.");
        System.out.println("In the previous year " + starved + " people starved.");
        System.out.println("In the previous year " + immigrants + " people entered the kingdom.");
        System.out.println("The population is now " + population + ".");
        System.out.println("We harvested " + harvested + " bushels at " + yield + " bushels per acre.");
        System.out.println("Rats destroyed " + rats + " bushels.");
        System.out.println("We now have " + grain + " bushels in storage.");
        System.out.println("The city owns " + acres + " acres of land.");
        System.out.println("Land is currently worth " + price + " bushels per acre.");
    }

    int askHowManyAcresToBuy(int price, int grain) {
        while (true) { 
            int acres = getNumber("How many acres do you want to buy? ");
            if (acres * price <= grain) {
                return acres; 
            }   
            System.out.println("You don't have enough grain!");
        }   
    }    

    int askHowManyAcresToSell(int acresOwned) {
        while (true) { 
            int acresToSell = getNumber("How many acres do you want to sell? ");

            if (acresToSell < 0) {
                System.out.println("You cannot sell negative acres!");
            }
            else if (acresToSell > acresOwned) {
                System.out.println("You don't own enough acres!");
            }
            else {
                return acresToSell;
            }
        }
    }
    int askHowMuchGrainToFeedPeople(int grain) {
        while (true) {
            int grainFed = getNumber("How much grain do you want to feed your people? ");
            
            if (grainFed <= grain && grainFed >= 0) {
                return grainFed;
            }
            System.out.println("O Anitra, ruler of the great Hammurabi, we don't have that much grain!");  
        }
    }
    int askHowManyAcresToPlant(int acresOwned, int population, int grain) {
        while (true) {
            int acresToPlant = getNumber("How many acres do you want to plant? ");

            int maxByPeople = population * 10;
            int maxByGrain = grain / 2;

            if (acresToPlant < 0) {
                System.out.println("You cannot plant negative acres!");
            }
            else if (acresToPlant > acresOwned) {
                System.out.println("You do not own enough acres!");
            }
            else if (acresToPlant > maxByGrain) {
                System.out.println("Not enough grain for seeds!");
            }
            else if (acresToPlant > maxByPeople) {
                System.out.println("Not enough people to farm these acres!");
            }
            else {
                return acresToPlant;
            }
            
        }
    }

    int plagueDeaths(int population) {
        int chance = rand.nextInt(100); 
            if (chance < 15) {
                return population / 2;
            }
            return 0;
    }

    int starvationDeaths(int population, int grainFed) {
        int peopleFed = grainFed / 20;
        if (peopleFed >= population) {
            return 0;
        }
        return population - peopleFed;
    }

    boolean uprising(int population, int howManyPeopleStarved) {
        if (population == 0) {
        return true;
    }
        int percentStarved = (howManyPeopleStarved * 100) / population;
        return percentStarved > 45;
    }

    int immigrants(int population, int acres, int grain) {
        if (population <= 0) {
        return 0;
    }
        return (20 * acres + grain) / (100 * population) + 1;
    }

    int harvest(int acresPlanted) {
        int yield = rand.nextInt(6) + 1;
        return acresPlanted * yield;
}

    int grainEatenByRats(int bushels) {
    int chance = rand.nextInt(100);

        if (chance < 40) {
        int percent = rand.nextInt(21) + 10; // 10 to 30
        return (bushels * percent) / 100;
    }
        return 0;
    }

    int newCostOfLand() {
        return rand.nextInt(7) + 17;
    }

    void finalSummary(int population, int acres, int grain, int starvedLastYear) {

    System.out.println("\n===== FINAL SUMMARY =====");

    System.out.println("Final population: " + population);
    System.out.println("Final land owned: " + acres);
    System.out.println("Grain in storage: " + grain);
    System.out.println("People starved last year: " + starvedLastYear);

    int acresPerPerson = (population == 0) ? acres : acres / population;

    System.out.println("Acres per person: " + acresPerPerson);

    // Simple performance rating
    if (starvedLastYear > population / 2) {
        System.out.println("Rating: TERRIBLE - You mismanaged the city or were overthrown.");
    }
    else if (acresPerPerson < 5) {
        System.out.println("Rating: POOR - Not enough land per person.");
    }
    else if (acresPerPerson < 10) {
        System.out.println("Rating: AVERAGE - Acceptable leadership.");
    }
    else {
        System.out.println("Rating: EXCELLENT - You ruled wisely!");
    }

    System.out.println("=========================");
}

    int getNumber(String message) {

    while (true) {
        System.out.print(message);

        try {
            return scanner.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("\"" + scanner.next() + "\" isn't a number!");
        }
    }
}


}
