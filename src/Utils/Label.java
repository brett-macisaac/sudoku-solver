
package Utils;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


/*
* Objects of this class can be used to display text onto the screen.
* The size of the text is the largest it can be while fitting within the bounds of the label (incl. inner padding).
 
* Class Composition:
    (a). Enums (1)
    (b). Fields (6)
        (i). Statics (4)
    (c). Constructors (2)
    (d). Publics (6)
        (i). Accessors (1)
        (ii). Mutators (3)
*/
public class Label
    extends Canvas 
{
    
// (a). Enums (1) ======================================================================================================
    
    /*
    * An enum to represent the different ways text can be aligned on the label. 
    */
    public enum Alignment
    {
        Left,
        Centre
    }
    
    
    
// (b). Fields (2) =====================================================================================================
    
    /*
    * The text that is displayed. 
    */
    private String f_text;
    
    /*
    * The text's alignment. 
    */
    private Alignment f_align;
    
    
// (b)(i). Statics (4) -------------------------------------------------------------------------------------------------
    
    /*
    * The size of the padding above and below the text/value, as a proportion of the label's width.
    */
    private static final float s_padding_proportion_horizontal = 0.1f;
    
    /*
    * The size of the padding to the left and right of the text, as a proportion of the label's height.
    */
    private static final float s_padding_proportion_vertical = 0.1f;
    
    // The default background colour.
    private static final Color s_colour_back_default = Color.WHITE;
    
    // The default text colour.
    private static final Color s_colour_text_default = Color.BLACK;
    
    private static final long serialVersionUID = 1L;
    
    
    
// (c). Constructors (2) ===============================================================================================
    
    /*
    
    * Parameters:
        > a_text: the label's initial text.
        > a_align: the text's alignment.
        > a_width: the width of the label (pixels).
        > a_height: the height of the label (pixels).         
        > a_colour_back: the label's background colour.
        > a_color_text: the text's colour.
    */
    public Label(String a_text, Alignment a_align, int a_width, int a_height, Color a_color_back, Color a_color_text)
    {
        super();
        
        // Set size.
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Set colours.
        super.setBackground(a_color_back);
        super.setForeground(a_color_text);

        // Set text.
        f_text = a_text;
        f_align = a_align;
    }
    
    /*
    
    * Parameters:
        > a_text: the label's initial text.
        > a_align: the text's alignment.
        > a_width: the width of the label (pixels).
        > a_height: the height of the label (pixels).
    */
    public Label(String a_text, Alignment a_align, int a_width, int a_height)
    {
        super();
        
        // Set size.
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Set colours.
        super.setBackground(s_colour_back_default);
        super.setForeground(s_colour_text_default);

        // Set text.
        f_text = a_text;
        f_align = a_align;
    }
    
    
    
// (d). Publics (6) ====================================================================================================
    
    /*
    * This method is called to update the canvas' graphics.
    */
    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    /*
    * This method updates the canvas' graphics. 
    */
    @Override
    public void update(Graphics g)
    {
        
        // Draw the background.
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        
        if (f_text == "")
        { return; }
        
        // The horizontal and vertical padding.
        int l_padding_horizontal = (int)( super.getPreferredSize().width * s_padding_proportion_horizontal );
        int l_padding_vertical = (int)( super.getPreferredSize().height * s_padding_proportion_vertical );
        
        // The text's maximum width.
        int l_width_max = (int)( super.getPreferredSize().width - 2 * l_padding_horizontal );
        
        // The font's maximum height.
        int l_height_max = (int)( super.getPreferredSize().height - 2 * l_padding_vertical );

        // Print the text upon the canvas at the appropriate font.
        for (int height = l_height_max; height > 1; --height)
        {
            g.setFont(new Font("Arial", Font.PLAIN, height));         
            
            FontMetrics l_font_metrics = g.getFontMetrics();
            
            // The highest height of a character of the current font.
            int l_height_text = l_font_metrics.getAscent();
            
            // Check if the height is too high.
            if (l_height_text > l_height_max)
            { continue; }
            
            // The width of l_text_full under the current font.
            int l_width_text = l_font_metrics.stringWidth(f_text);
            
            // Check if the width is too wide.
            if (l_width_text > l_width_max)
            { continue; }
            
            // X and Y coordinate of the text.
            int l_x = 0;
            int l_y = 0;
            
            // Set the coordinates.
            if (f_align == Alignment.Left)
            {
                l_x = l_padding_horizontal;
                l_y = l_padding_vertical + l_height_text;
            }
            else if (f_align == Alignment.Centre)
            {
                l_x = l_padding_horizontal + (l_width_max - l_width_text) / 2;
                l_y = l_padding_vertical + l_height_text;
            }
            
            // Set the colour for l_text_string.
            g.setColor(getForeground());
            
            // Draw l_text_string.
            g.drawString(f_text, l_x, l_y);
            
            break;
        }

    }
    
    
// (d)(i). Accessors (1) -----------------------------------------------------------------------------------------------
    
    /* Accessor of f_text
    */
    public String GetText()
    {
        return f_text;
    }
    
    
// (d)(ii). Mutators (3) -----------------------------------------------------------------------------------------------
    
    /* Mutator of f_text
    */
    public void SetText(String a_text)
    {
        f_text = a_text;
        
        super.repaint();
    }
    
    /* Mutator of background colour
    */
    public void SetColourBack(Color a_colour)
    {
        super.setBackground(a_colour);

        repaint();
    }
    
    /* Mutator of foreground (text) colour
    */
    public void SetColourFront(Color a_colour)
    {
        super.setForeground(a_colour);

        repaint();
    }
    
   
}
