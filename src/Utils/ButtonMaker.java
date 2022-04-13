
package Utils;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;


/*
* A class that contains static utility functions related to the creation of JButton objects.
 
* Composition:
    (a). Public Methods (5)
        (i). Statics (5)
*/
public class ButtonMaker 
{
    
// (a). Public Methods (5) =============================================================================================
    
    
// (a)(i). Statics (5) =================================================================================================
    
    /*
     
    * Parameters:
        > a_title: the button's text.
        > a_listener: the event listener connecting the click event to an event-handler.
        > a_font: the button's font.
        
    * Return Value:
        > A JButton whose properties match the ones specified in the parameter list.
       
    */
    public static JButton CreateButton(String a_title, ActionListener a_listener, Font a_font)
    {
        return CreateButton(a_title, a_listener, null, a_font);
    }

    /*
    
    * Parameters:
        > a_title: the button's text.
        > a_listener: the event listener connecting the click event to an event-handler.
        > a_dimension: the button's dimension.
        > a_font: the button's font.
        
    * Return Value:
        > A JButton whose properties match the ones specified in the parameter list.
       
    */
    public static JButton CreateButton(String a_title, ActionListener a_listener, Dimension a_dimension, Font a_font)
    {
        JButton Result = new JButton(a_title);

        if (a_dimension != null)
        {
            Result.setPreferredSize(a_dimension);
        }

        if (a_font != null)
        {
            Result.setFont(a_font);  
        }
        
        Result.addActionListener(a_listener);
        
        return Result;
    }
    
    /*
    
    * Parameters:
        > a_title: the button's text.
        > a_listener: the event listener connecting the click event to an event-handler.
        > a_dimension: the button's dimension.
       
    * Return Value:
        > A JButton whose properties match the ones specified in the parameter list.
       
    */
    public static JButton CreateButton(String a_title, ActionListener a_listener, Dimension a_dimension)
    {
        return CreateButton(a_title, a_listener, a_dimension, null);
    }

    /*
    
    * Parameters:
        > a_title: the button's text.
        > a_listener: the event listener connecting the click event to an event-handler.
        > a_height: the button's height.
        
    * Return Value:
        > A JButton whose properties match the ones specified in the parameter list.
       
    */
    public static JButton CreateButton(String a_title, ActionListener a_listener, int a_height)
    {
        JButton Result = new JButton(a_title);
        Dimension lSize = Result.getPreferredSize();
        
        lSize.height = a_height;
        
        Result.setPreferredSize(lSize);
        Result.setFont(new Font("Arial", Font.BOLD, 24));
        Result.addActionListener(a_listener);
        
        return Result;
    }

    /*
    
    * Parameters:
        > a_title: the button's text.
        > a_listener: the event listener connecting the click event to an event-handler.
        
    * Return Value:
        > A JButton whose properties match the ones specified in the parameter list.
       
    */
    public static JButton CreateButton(String a_title, ActionListener a_listener)
    {
        return CreateButton(a_title, a_listener, null, null);
    }
    
    
}
