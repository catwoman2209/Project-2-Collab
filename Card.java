/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs2365.project.pkg2;


/**
 * Card class is called by Deck class to create deck
 * Functions
 *  -Returns color/type of card for use in Game class
 *  -Returns strings for HTML output
 *
 * @author chris
 */
class Card {
    
    private int val;
    private String col;
    private String spec;
    boolean isSpec;
    
    /**
     * Function creates card object with input parameters
     * @param value
     * @param color
     * @param special 
     */
    public Card(int value, String color, String special) {
        
        val = value;
        col = color;
        spec = special;
        
        if(!special.equals("")) {
            
            isSpec = true;
        } 
        else {
            
            isSpec = false;
        }
    }
    
    /**
     * Method returns integer value of card object
     * @return 
     */
    public int getVal() {
        
        return val;
    }
    
    /**
     * Boolean method returns true if card is special
     * @return 
     */
    public boolean getSpec() {
        
        return isSpec;
    }
    
    /**
     * Method returns color of card object or wild
     * @param getCol
     * returns string of color if exists
     * @return 
     */
    public String getStr(boolean getCol) {
        
        if(getCol) {
            
            return col;
        }
        
        else {
            
            if(isSpec) {
                
                return spec;
            }
            else {
                
                return "";
            }
        }
    }
    
    
}
