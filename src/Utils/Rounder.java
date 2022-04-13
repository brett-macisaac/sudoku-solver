
package Utils;


/*
* A class that contains static utility functions related to the rounding of numbers.

* Composition:
    (a). Public Methods (1)
        (i). Statics (1)
*/
public class Rounder 
{
    
// (a). Public Methods (1) =============================================================================================
    
    
// (a)(i). Statics (1) =================================================================================================
    
    /*
    * Returns the mutliple of a_n that's closest to a_value.
     
    * Parameters:
        > a_value: the value to be rounded to the nearest a_n.
        > a_n: the number of which a_value will be rounded to a multiple of.
    */
    public static int NearestMultiple(int a_value, int a_n)
    {
        if (a_value < 0 || a_n < 0)
        { return -1; }
        
        float l_mid = (float) ((float)(a_n) / 2.0);
        
        if (a_value <= a_n)
        {
            if (a_value >= l_mid)
            {
                return a_n;
            }
            else
            {
                return 0;
            }
            
        }
        
        int l_remainder = a_value % a_n;
        
        if (l_remainder > l_mid)
        {
            return a_value + (a_n - l_remainder);
        }
        else
        {
            return a_value - l_remainder;
        }
        
    }
    
    
}
