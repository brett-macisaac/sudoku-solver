
package Utils;


import java.awt.Color;


public class TextValueLabel
    extends Label
{
    private int f_length_text;
    
    private int f_value;
    
    
    
    public TextValueLabel(String a_text, int a_value, Label.Alignment a_align, int a_width, int a_height, Color a_color_back, Color a_color_text)
    {   
        super(a_text, a_align, a_width, a_height, a_color_back, a_color_text);
        
        // Add a colon to the text.
        a_text += ": ";
        
        // Record the text's length.
        f_length_text = a_text.length();
        
        // Set the value.
        SetValue(a_value);
    }
    
    public TextValueLabel(String a_text, int a_value, Label.Alignment a_align, int a_width, int a_height)
    {   
        super(a_text, a_align, a_width, a_height);
        
        // Add a colon to the text.
        a_text += ": ";
        
        // Update the text.
        super.SetText(a_text);
        
        // Record the text's length.
        f_length_text = a_text.length();
        
        // Set the value.
        SetValue(a_value);
    }
    
    
    
    public void SetValue(int a_value)
    {
        // Set  the value.
        f_value = a_value;
        
        // Get the 'text' part of the label's string: e.g. if f_text is "Count: 12", the text part will be "Count: "
        String l_text = super.GetText().substring(0, f_length_text);
        
        // Add the value to the text.
        //l_text = l_text.concat(String.valueOf(f_value));
        l_text += String.valueOf(f_value);
        
        // Update the label's text.
        super.SetText(l_text);
    }
    
    public void Increment()
    {
        SetValue(f_value + 1);
    }
    
    public void Decrement()
    {
        SetValue(f_value - 1);
    }
    
    
}
