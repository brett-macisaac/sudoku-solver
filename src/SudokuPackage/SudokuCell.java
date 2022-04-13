
package SudokuPackage;


import java.awt.Color;

import Utils.Label;


/*
 * Each object of this class is a cell in a SudokuBoard object.
 * Each cell is either a 'guess cell' or a 'clue cell'. Clue cells are those whose numbers are given to the user, while
   guess cells are those that the user must fill in.
 
 * Class Composition:
     (a). Fields (2)
     (b). Constructors (1)
     (c). Publics (4)
         (i). Accessors (2)
         (ii). Mutators (2)
*/
public class SudokuCell 
    extends Label
{
    
// (a). Fields (2) =====================================================================================================
    
    /*
     * The cell's value. 
    */
    private int f_value;
    
    /*
     * A flag that, when true, indicates that the cell is a 'clue cell'; otherwise, it's a 'guess cell'.
    */
    private boolean f_is_clue;
    
    
    
// (b). Constructors (1) ===============================================================================================
    
    /*
     * Parameters:
         > a_value: the cell's initial value.
         > a_is_clue: whether the cell is a clue. 
    */
    public SudokuCell(int a_value, boolean a_is_clue, int a_width, int a_height, Color a_color_back, Color a_color_text)
    {   
        super(String.valueOf(a_value), Label.Alignment.Centre, a_width, a_height, a_color_back, a_color_text);
        
        f_value = a_value;
        f_is_clue = a_is_clue;
    }
    
    
    
// (c). Publics (1) ====================================================================================================
    
    
// (c)(i). Accessors (2) -----------------------------------------------------------------------------------------------
    
    /* Accessor of f_value
    */
    public int GetValue()
    {
        return f_value;
    }
    
    /* Accessor of f_is_clue
    */
    public boolean IsClue()
    {
        return f_is_clue;
    }
    
    
// (c)(ii). Mutators (2) -----------------------------------------------------------------------------------------------
    
    /* Mutator of f_value
    */
    public void SetValue(int a_value)
    {
        // Set  the value.
        f_value = a_value;
        
        if (f_value == 0)
        {
            super.SetText("");
        }
        else
        {
            super.SetText(String.valueOf(f_value));
        }
        
    }
    
    /* Mutator of f_is_clue
    */
    public void SetIsClue(boolean a_is_clue)
    {
        f_is_clue = a_is_clue;
    }
    
    
}
