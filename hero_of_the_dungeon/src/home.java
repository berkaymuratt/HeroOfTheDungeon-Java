import character.Hero;
import item.*;
import level_design.Dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class home {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("-------------------------------- HERO OF THE DUNGEON -------------------------------------");

        // TOP 5 HIGH SCORE
        File highScore1 = new File("high-score-1.txt");
        File highScore2 = new File("high-score-2.txt");
        File highScore3 = new File("high-score-3.txt");
        File highScore4 = new File("high-score-4.txt");
        File highScore5 = new File("high-score-5.txt");

        ArrayList<File> highScoresFiles = new ArrayList<>();

        highScoresFiles.add(highScore1);
        highScoresFiles.add(highScore2);
        highScoresFiles.add(highScore3);
        highScoresFiles.add(highScore4);
        highScoresFiles.add(highScore5);

        printHighScores(highScoresFiles);

        System.out.println(" - Welcome to Hero Of the Dungeon. The fate of the towns people depends on you. Save the villagers, defeat the monsters and get your cookie. We count on you warrior.");
        System.out.println();

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.print("Enter your hero's name (just a word) : ");
        String name = scanner.next();
        scanner.nextLine();

        System.out.println("Hero's name: " + name);

        System.out.println();

        String gender;

        while (true) {

            String option;

            try {
                System.out.println("1: Male");
                System.out.println("2: Female");

                System.out.print("Gender Selection: ");
                option = scanner.next();

                int genderValue = Integer.parseInt(option);

                switch (genderValue) {
                    case 1:
                        gender = "Male";
                        break;
                    case 2:
                        gender = "Female";
                        break;
                    default:
                        System.out.println("Invalid Option..");
                        System.out.println();
                        continue;
                }
            } catch (Exception e) {
                System.out.println("Invalid Option..");
                System.out.println();
                continue;
            }
            break;
        }


        System.out.println();
        System.out.println("Loading..");
        System.out.println();

        //All Items
        AllWeapons.createItems();
        AllClothing.createItems();

        // Creation of Dungeons
        ArrayList<Dungeon> dungeons = new ArrayList<Dungeon>();

        int levelCount = Dungeon.dungeonsCount;

        for (int i = 0; i < levelCount; i++) {
            dungeons.add(new Dungeon());
        }

        orderDungeons(dungeons);

        for (int i = 0; i < levelCount; i++) {
            dungeons.get(i).putStairs(dungeons);

            Dungeon dungeon = dungeons.get(i);
            System.out.println("Dungeon " + dungeon.getDungeonNumber() + " created, Monsters Number: " + dungeon.getAllMonsters().size());
        }

        Weapon startingWeapon = AllWeapons.getDagger();
        Clothing startingClothing = AllClothing.getLightClothing();

        //User Inventory
        Inventory userInventory = new Inventory(60);

        //Hero
        Hero hero = new Hero(name, gender, startingWeapon, startingClothing, userInventory, 15000, dungeons.get(0));

        while (!hero.isDead() && !hero.isGameWon()) {

            Dungeon currentDungeon = hero.getCurrentDungeon();
            currentDungeon.enter(hero);
        }

        System.out.println();

        if (hero.isGameWon()) {
            System.out.println("YOU WON.. CONGRATULATIONS..");
            System.out.println("- I didn't expect it to come this far in the game. Here is your cookie.");

            System.out.println();

            System.out.println("You have been rewarded with a double crisped cookie.. (+500 pts)");
            hero.gainPoints(500);

        } else {
            System.out.println("GAME OVER.. " + hero.getName() + " died..");
            System.out.println("No cookie for " + hero.getName() + "..");
        }

        System.out.println();

        //Calculation the score of the hero
        System.out.println("-------------------------------");

        System.out.println("Your Hero: " + hero.getName());
        System.out.println("Gender: " + hero.getGender());

        System.out.println();

        System.out.println("YOUR SCORE: " + hero.getScore());

        System.out.println();

        System.out.println("Cleared Dungeons: " + hero.getFinishedLevels().size());
        System.out.println("Killed Monsters: " + hero.getKilledMonsters().size());
        System.out.println("Saved Townspeople: " + hero.getSavedTownspeople().size());
        System.out.println("-------------------------------");

        System.out.println();

        saveScore(hero, highScoresFiles);
    }

    private static void orderDungeons(ArrayList<Dungeon> dungeons) {

        ArrayList<Integer> monsterNumbers = new ArrayList<>();

        for(Dungeon dungeon : dungeons) {
            monsterNumbers.add(dungeon.getAllMonsters().size());
        }

        for(int i=0; i<dungeons.size(); i++) {
            for(int j=0; j< dungeons.size(); j++) {

                if(monsterNumbers.get(i) < monsterNumbers.get(j)) {
                    Dungeon dungeon1 = dungeons.get(i);
                    Dungeon dungeon2 = dungeons.get(j);

                    int monstersCount1 = monsterNumbers.get(i);
                    int monstersCount2 = monsterNumbers.get(j);

                    dungeons.set(i, dungeon2);
                    dungeons.set(j, dungeon1);

                    monsterNumbers.set(i, monstersCount2);
                    monsterNumbers.set(j, monstersCount1);
                }
            }
        }

        for (int i=1; i<= Dungeon.dungeonsCount; i++) {
            dungeons.get(i-1).setDungeonNumber(i);
        }

    }

    private static void printHighScores(ArrayList<File> highScoreFiles) {

        ArrayList<String> highScoresDetails = getHighScoresDetails(highScoreFiles);

        ArrayList<Integer> scores = new ArrayList<>();

        for (String scoreDetail : highScoresDetails) {

            int score = getScoreValue(scoreDetail);

            scores.add(score);
        }

        if (highScoresDetails.size() != 0) {

            //Order ScoreDetails by their Score values
            for (int i=0; i < highScoresDetails.size(); i++) {
                for (int j=0; j< highScoresDetails.size(); j++) {
                    if(scores.get(i) > scores.get(j)) {

                        String detail1 = highScoresDetails.get(i);
                        String detail2 = highScoresDetails.get(j);

                        int score1 = scores.get(i);
                        int score2 = scores.get(j);

                        scores.set(i, score2);
                        scores.set(j, score1);

                        highScoresDetails.set(i, detail2);
                        highScoresDetails.set(j, detail1);
                    }
                }
            }


            System.out.println();
            System.out.println("------------- HIGH SCORES -------------");

            int counter = 1;

            for (String score : highScoresDetails) {
                System.out.print("NUMBER " + counter + ":");
                System.out.println(score);
                System.out.println();

                counter++;
            }

            System.out.println("----------------------------------------");
            System.out.println();
        }

    }

    private static ArrayList<String> getHighScoresDetails(ArrayList<File> highScoreFiles) {

        ArrayList<String> scoreDetails = new ArrayList<>();

        ArrayList<Scanner> readers = new ArrayList<>();

        for (File file : highScoreFiles) {
            try {
                readers.add(new Scanner(file));
            } catch (FileNotFoundException e) {
                //File not found
            }
        }

        for (Scanner reader : readers) {
            String data = "";

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                data += "\n" + line;
            }
            scoreDetails.add(data);
            reader.close();
        }

        return scoreDetails;
    }

    private static void saveScore(Hero hero, ArrayList<File> highScoreFiles) {

        ArrayList<String> highScoresDetails = getHighScoresDetails(highScoreFiles);


        int currentScore = hero.getScore();

        File file = null;

        if (highScoresDetails.size() > 0) {

            int minScore = getMinScore(highScoresDetails);

            for (int i = 0; i < highScoresDetails.size(); i++) {

                if (highScoresDetails.size() == 5) {


                    int score = getScoreValue(highScoresDetails.get(i));

                    if (currentScore > score && score == minScore) {
                        file = highScoreFiles.get(i);
                    }

                } else {
                    file = highScoreFiles.get(i + 1);
                }
            }
        } else {
            file = highScoreFiles.get(0);
        }

        if (file == null) {
            System.out.println("Your score is not greater than top 5 high scores.");
            System.out.println("Your score details were not saved in the high scores list..");

            return;
        }

        try {
            if (file.createNewFile()) {
                System.out.println("Your score will be saved in a new file..");
            } else {
                System.out.println("Your score will be saved..");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }

        try {
            FileWriter writer = new FileWriter(file.getName());
            writer.write("<-----------------------------> \n"
                    + hero.getName().toUpperCase() + " \n"
                    + "Gender: " + hero.getGender() + " \n"
                    + "Score: " + hero.getScore() + " \n"
                    + "Cleared Dungeons: " + hero.getFinishedLevels().size() + " \n"
                    + "Killed Monsters: " + hero.getKilledMonsters().size() + " \n"
                    + "Saved Townspeople: " + hero.getSavedTownspeople().size() + " \n"
                    + "<-----------------------------> \n"
            );
            writer.close();
            System.out.println("Saved Successfully..");
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static int getScoreValue(String scoreDetail) {

        String[] words = scoreDetail.split(" ");

        return Integer.parseInt(words[5]);
    }

    private static int getMinScore(ArrayList<String> highScoresDetails) {
        int minScore = 100;

        for (String scoreDetail : highScoresDetails) {
            String[] words = scoreDetail.split(" ");

            int score = Integer.parseInt(words[5]);

            if (score < minScore) {
                minScore = score;
            }
        }
        return minScore;
    }

}

