/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs2365.project.pkg2;

import java.util.*;

/**
 * Game class requires Deck and Card classes to function Has 3 methods: - start
 * Asks user for # of decks, shuffle preference, and whether to remove Special
 * cards or not - formatCard Counts amount of reps per workout, takes into
 * account special cards and performs functions as listed in Project 2 PDF -
 * getMax For statistics collection
 *
 * start draws 7 cards sends to formatCard output displayed per exercise on
 * console and in HTML repeat until deck is depleted
 *
 * @author chris
 */
public class Game {
    //FOR STAT COLLECTION--> 

    int totalcounter = 0;
    int[] StatsWorkout = new int[3];
    int[] WorkoutReps = new int[5];
    int[] skippedR = new int[4];
    String winner = "";
    String fwin = "";

    Output html = new Output();

    /**
     * Main function that Starts and runs through the game. Goes through all the
     * decks and changes decks when no more cards can be drawn from it
     *
     */
    public void StartGame(int inp1, int inp2, int inp3) {

        if (inp1 > 3) {
            inp1 = 3;
        } else if (inp1 <= 0) {
            inp1 = 1;
        }

        //filling the Decks
        Deck[] Decks = new Deck[inp1];

        for (int a = 0; a < Decks.length; a++) {
            Decks[a] = new Deck();
            Decks[a].create(inp3);
        }

        //shuffling all the decks together
        if (inp2 == 1 && Decks.length > 1) {

            ArrayList<Card> All = new ArrayList<Card>();
            for (int a = 0; a < Decks.length; a++) {
                ArrayList<Card> DeckInd = Decks[a].getDeck(inp3);

                for (int b = 0; b < DeckInd.size(); b++) {
                    All.add(DeckInd.get(b));
                }

            }

            Collections.shuffle(All);

            //redistrbuting the cards into the decks
            int index = 0;
            for (int a = 0; a < Decks.length; a++) {
                ArrayList<Card> NewD = new ArrayList<Card>();
                for (int b = 0; b < 108; b++) {
                    if (All.get(index) != null) {
                        NewD.add(All.get(index));
                        index++;
                    }
                }

                Decks[a].refill(NewD);
            }

        }

        //The Player can then choose to remove special cards!!!
        //Let the game begin!
        int count = 1;
        ArrayList<Card> Hand = new ArrayList<Card>();
        for (int a = 0; a < Decks.length; a++) {
            Hand.clear();
            while (!Decks[a].isEmpty()) {
                if (Hand.size() == 0) {
                    ArrayList<Card> Temp = Decks[a].drawCards(7);

                    //function to sort the hand and display the data
                    html.setHandHeading(count);
                    count++;
                    SortingHand(Temp);

                    for (int b = 0; b < Temp.size(); b++) {
                        Hand.add(Temp.get(b));
                    }

                    //Hand has been filled
                    format(Hand, Decks[a]);

                    //Will draw a new hand
                    Temp.clear();
                    Hand.clear();

                }

                //Number of Cards Left3
                int RemainingCards = Decks[a].onDeck() + (108 * (Decks.length - (a + 1)));
                html.setCardsLeft(RemainingCards);
                System.out.println("Cards left to draw from - " + RemainingCards + "\n");

            }
        }

        //Display Stat Data
        DisplayStatData(StatsWorkout, WorkoutReps, skippedR);
        html.writeToFile();
        System.out.println("Game over!!! Hopefully you had a good workout!");
    }

    /**
     * Function takes in each hand and the Deck that you are currently on and
     * formats the series of cards into a workout routine via card color, value,
     * and special type.
     *
     * @param H
     * @param D
     */
    void format(ArrayList<Card> H, Deck D) {
        int repSitups = 0;
        int repPushups = 0;
        int repSquats = 0;
        int repLunges = 0;
        int Burpees = 0;

        for (int a = 0; a < H.size(); a++) {
            boolean isS = H.get(a).isSpec;
            int V = H.get(a).getVal();
            String Col = H.get(a).getType(true);
            String Spe = H.get(a).getType(false);

            if (!isS) {
                if (Col.equals(("Red"))) {
                    repSitups += V;
                } else if (Col.equals(("Blue"))) {
                    repPushups += V;
                } else if (Col.equals(("Yellow"))) {
                    repSquats += V;
                } else if (Col.equals(("Green"))) {
                    repLunges += V;
                }
            }
        }

        for (int a = 0; a < H.size(); a++) {
            boolean isS = H.get(a).isSpec;
            int V = H.get(a).getVal();
            String Col = H.get(a).getType(true);
            String Spe = H.get(a).getType(false);

            if (isS) {
                if (Spe.equals("Skip")) {
                    if (Col.equals(("Red"))) {
                        skippedR[0] += repSitups;
                        repSitups = 0;
                    } else if (Col.equals(("Blue"))) {
                        skippedR[1] += repPushups;
                        repPushups = 0;
                    } else if (Col.equals(("Yellow"))) {
                        skippedR[2] += repSquats;
                        repSquats = 0;
                    } else if (Col.equals(("Green"))) {
                        skippedR[3] += repLunges;
                        repLunges = 0;
                    }
                } else if (Spe.equals("Draw Two")) {
                    if (Col.equals(("Red"))) {
                        repSitups *= 2;
                    } else if (Col.equals(("Blue"))) {
                        repPushups *= 2;
                    } else if (Col.equals(("Yellow"))) {
                        repSquats *= 2;
                    } else if (Col.equals(("Green"))) {
                        repLunges *= 2;
                    }
                } else if (Spe.equals(("Wild"))) {
                    Burpees += 4;

                } else if (Spe.equals(("Wild Draw 4"))) {
                    Burpees += 4;
                    repSitups *= 4;
                    repPushups *= 4;
                    repSquats *= 4;
                    repLunges *= 4;
                }
            }
        }

        if (repSitups > 0) {
            System.out.println("Do " + repSitups + " reps of Sit Ups!");
        }

        if (repPushups > 0) {
            System.out.println("Do " + repPushups + " reps of Push Ups!");
        }

        if (repSquats > 0) {
            System.out.println("Do " + repSquats + " reps of Squats!");
        }

        if (repLunges > 0) {
            System.out.println("Do " + repLunges + " reps of Lunges!");
        }

        if (Burpees > 0) {
            System.out.println("Do " + Burpees + " reps of Burpees!");
        }

        System.out.println();

        //Stat collection function gets called
        WorkoutReps[0] += repSitups;
        WorkoutReps[1] += repPushups;
        WorkoutReps[2] += repSquats;
        WorkoutReps[3] += repLunges;
        WorkoutReps[4] += Burpees;
        int TotalR = repSitups + repPushups + repSquats + repLunges + Burpees;
        int MaxR = GetMaxReps(repSitups, repPushups, repSquats, repLunges, Burpees);
        

        totalcounter += TotalR;
        fwin = D.stats(TotalR, 0, MaxR, winner);

        updateStats(D.statData());

    }

    /**
     * Function globally keeps track of every Rep performed, every skipped rep,
     * and the max reps performed in a single set throughout the entire game
     *
     * @param Stats
     */
    void updateStats(int[] Stats) {
        StatsWorkout[0] += Stats[0];
        StatsWorkout[1] += Stats[1];

        if (StatsWorkout[2] < Stats[2]) {
            StatsWorkout[2] = Stats[2];
        }

    }

    /**
     * Function displays the stats of game, called at the end of the game. Takes
     * in two Arrays that both have data pertaining to total stats
     *
     * @param TotalStats
     * @param IndStats
     */
    void DisplayStatData(int[] TotalStats, int[] IndStats, int[] skipped) {
        html.setExerciseHeading();
        System.out.println("---Workout Stats---");

        int placehtkr = skippedR[0] + skippedR[1] + skippedR[2] + skippedR[3];

        System.out.println("Total Reps - " + totalcounter);
        System.out.println("Total Skipped Reps - " + placehtkr);
        System.out.println("Most Reps performed at once: " + TotalStats[2] + " " + fwin + "\n");

        System.out.println("Total Situps - " + IndStats[0]);
        html.setExerciseTotal(IndStats[0], skippedR[0], "Situps");
        System.out.println("Total Pushups - " + IndStats[1]);
        html.setExerciseTotal(IndStats[1], skippedR[1], "Pushups");
        System.out.println("Total Squats - " + IndStats[2]);
        html.setExerciseTotal(IndStats[2], skippedR[2], "Squats");
        System.out.println("Total Lunges - " + IndStats[3]);
        html.setExerciseTotal(IndStats[3], skippedR[3], "Lunges");
        System.out.println("Total Burpees - " + IndStats[4] + "\n");
        html.setExerciseTotal(IndStats[4], 0, "Burpees");
    }

    /**
     * Sorting method that groups a set of cards from hand H, into separate
     * arrayList via color
     *
     * @param H
     */
    void SortingHand(ArrayList<Card> H) {
        ArrayList<Card> Wild = new ArrayList<Card>();
        ArrayList<Card> Red = new ArrayList<Card>();
        ArrayList<Card> Blue = new ArrayList<Card>();
        ArrayList<Card> Yellow = new ArrayList<Card>();
        ArrayList<Card> Green = new ArrayList<Card>();

        for (int a = 0; a < H.size(); a++) {
            String C = H.get(a).getType(true);

            if (C.equals("Red")) {
                Red.add(H.get(a));
            } else if (C.equals("Blue")) {
                Blue.add(H.get(a));
            } else if (C.equals("Yellow")) {
                Yellow.add(H.get(a));
            } else if (C.equals("Green")) {
                Green.add(H.get(a));
            } else {
                Wild.add(H.get(a));
            }

        }

        //sort them
        Red = SortByRank(Red);
        Blue = SortByRank(Blue);
        Yellow = SortByRank(Yellow);
        Green = SortByRank(Green);

        System.out.println("-----New Hand-----");

        for (Card red1 : Red) {
            html.setCardDescription(red1);
            System.out.print(red1.cardOut() + ", ");
        }
        for (Card blue1 : Blue) {
            html.setCardDescription(blue1);
            System.out.print(blue1.cardOut() + ", ");
        }
        for (Card yellow1 : Yellow) {
            html.setCardDescription(yellow1);
            System.out.print(yellow1.cardOut() + ", ");
        }
        for (Card green1 : Green) {
            html.setCardDescription(green1);
            System.out.print(green1.cardOut() + ", ");
        }
        for (Card wild1 : Wild) {
            html.setCardDescription(wild1);
            System.out.print(wild1.cardOut() + ", ");
        }

        System.out.println();

    }

    /**
     * Sorting method that takes in a set of cards, already sorted by color, and
     * sorts them via value that they hold and returns the sorted list from
     * smallest to largest.
     *
     * @param HandCol
     * @return
     */
    ArrayList<Card> SortByRank(ArrayList<Card> HandCol) {
        for (int j = 0; j < HandCol.size() - 1; j++) {
            for (int i = 0; i < HandCol.size() - 1; i++) {
                if (HandCol.get(i).getVal() > HandCol.get(i + 1).getVal()) {
                    Card PlaceHolder = HandCol.get(i + 1);
                    HandCol.set(i + 1, HandCol.get(i));
                    HandCol.set(i, PlaceHolder);
                }
            }
        }

        return HandCol;
    }

    /**
     * Function takes in all the reps done per exercise in a given hand, and
     * finds which one was done the most. Essentially, finds the largest value
     * passed in.
     *
     * @param Push
     * @param Sit
     * @param L
     * @param Sq
     * @param B
     * @return
     */
    int GetMaxReps(int Sit, int Push, int Sq, int L, int B) {

        if (Push > Sit && Push > L && Push > Sq && Push > B) {
            html.setMaxHandReps(Push, "PushUps");
            winner = "Pushups";
            System.out.println(winner);
            return Push;
        } else if (Sit > Push && Sit > L && Sit > Sq && Sit > B) {
            html.setMaxHandReps(Sit, "Situps");
            winner = "Situps";
            System.out.println(winner);
            return Sit;
        } else if (L > Push && L > Sit && L > Sq && L > B) {
            html.setMaxHandReps(L, "Lunges");
            winner = "Lunges";
            System.out.println(winner);
            return L;
        } else if (Sq > Push && Sq > Sit && Sq > L && Sq > B) {
            html.setMaxHandReps(Sq, "Squats");
            winner = "Squats";
            System.out.println(winner);
            return Sq;
        } else if (B > Push && B > Sit && B > L && B > Sq) {
            html.setMaxHandReps(B, "Burpees");
            winner = "Burpees";
            System.out.println(winner);
            return B;
        } else{
            return 0;
        }
    }
}
