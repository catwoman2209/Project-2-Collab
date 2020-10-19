/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs2365.project.pkg2;

import java.util.*;


/**
 * Deck class creates 108 UNO cards and stores them in an array list
 * Functions:
 *  -Shuffle
 *  -Check if empty
 *  -Draws hands for user
 * Statistics collection functions will store data for each deck
 *
 * @author chris
 */
class Deck {
    
    private ArrayList<Card> D = new ArrayList<Card>();
    
    private int repsComp = 0;
    private int repsSkip = 0;
    private int maxReps = 0;
    
    /**
     * Method creates new deck of uno cards with color, value and/or special
     * Stores in ArrayList D
     */
    public void create() {
        
        for( int i=0; i<4; i++ ) {
            
            String col = "";
            
            switch(i) {
                case '0': col = "Red";
                case '1': col = "Blue";
                case '2': col = "Yellow";
                case '3': col = "Green";
                default: break;
            }
            
            for(int j=0; j<25; j++) {
                
                if( j<10 ) {
                    
                    Card CRD = new Card(j, col, "");
                    D.add(CRD);
                }
                
                else if( j<19 ) {
                    
                    Card CRD = new Card(j-9, col, "");
                    D.add(CRD);
                }
                
                else if( j<21 ) {
                    
                    Card CRD = new Card(0, col, "Skip");
                    D.add(CRD);
                }
                
                else if( j<23 ) {
                    
                    Card CRD = new Card(0, col, "Draw Two");
                    D.add(CRD);
                }
                
                else {
                    
                    Card CRD = new Card(0, col, "Reverse");
                    D.add(CRD);
                }
            }
        }
        
        
        for( int i=0; i<8; i++ ) {
            
            if( i<4 ) {

                Card CRD = new Card(0, "", "Wild");
                D.add(CRD);
            }

            else {

                Card CRD = new Card(0, "", "Wild Draw 4");
                D.add(CRD);
            }
        }
        
        D = shuffleDeck(D);
    }
    
    /**
     * Method returns int of cards left in deck
     * @return 
     */
    public int onDeck() {
        
        return D.size();
    }
    
    /**
     * Method returns randomly shuffled ArrayList using Collections.shuffle
     * @param deckShuff
     * @return 
     */
    ArrayList<Card> shuffleDeck(ArrayList<Card> deckShuff) {
        
        Collections.shuffle(deckShuff);
        return deckShuff;
    }
    
    /**
     * Method takes 7 or less cards from deck List, returns ArrayList as a hand,
     * removes selected cards from deck
     * @param amt
     * @return 
     */
    public ArrayList<Card> DrawCards(int amt) {
        
        ArrayList<Card> dealHand = new ArrayList<Card>();
        if(amt > D.size()) {
            amt = D.size();
        }
        
        int i = 0;
        for(int a = D.size()-1; a>= 0 && i < amt; a--){
            
            if(D.get(a) != null) {
                dealHand.add(D.get(a));
                i++;
            }
        }
        
        i = 0;
        for(int a = D.size()-1; a>= 0 && i < amt; a--){
            
            if(D.get(a) != null) {
                D.remove(D.get(a));
                i++;
            }
        }
        
        return dealHand;
        
    }
    
    /**
     * Method returns ArrayList of deck in the class
     * @return 
     */
    public ArrayList<Card> getDeck() {
        
        return D;
    }
    
    /**
     * Method returns new Deck
     * @param newD 
     */
    public void refill(ArrayList<Card> newD) {
        
        D.clear();
        
        for(int i = 0; i < newD.size(); i++) {
            
            D.add(newD.get(i));
        }
    }
    
    /**
     * Method removes Special cards from deck, if chosen
     */
    public void removeSpec() {
        
        for(int i = 0; i < D.size(); i++) {
            
            boolean isSpec = D.get(i).getSpec();
            if(isSpec) {
                
                D.remove(D.get(i));
                
            }
        }
    }
   
    /**
     * Method returns boolean if deck is empty or not
     * @return 
     */
    public boolean isEmpty() {
    
        if(D.size() <= 0) {
            
            return  true;
        }
        else {
            
            return false;
        }
    }
    
    /**
     * Method keeps track of exercises, passes params to global variables
     * @param R
     * @param SR
     * @param MR 
     */
    public void stats(int R, int SR, int MR) {
        
        repsComp += R;
        repsSkip += SR;
        
        if(MR > maxReps) {
            maxReps = MR;
        }
    }
    
    /**
     * Method returns array of integers with all of statistical data
     * @return 
     */
    public int[] statData() {
        
        int[] table = new int[3];
        
        table[0] = repsComp;
        table[1] = repsSkip;
        table[2] = maxReps;
        
        return table;
    }
    
}
