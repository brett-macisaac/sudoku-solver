
package SudokuPackage;


/*
 * Description:
     > One of the algorithms used to solve sudoku puzzles operates via the concept of recursive-backtracking. When 
       teaching this programming concept, its application in solving sudokus is often used. 
     > This program is primarily designed to help visualise the recursive-backtracking process, allowing users to go 
       through the process step-by-step; however, a user can also solve sudokus manually via the UI.
  
  * Controls:
      > As stated in the description, the program can be used to solve sudokus manually; below is the associated control
        scheme:
          * Move Selection Up -------> w
          * Move Selection Left -----> a
          * Move Selection Right ----> s
          * Move Selection Down -----> d
          * Increment Cell Value ----> [up arrow]
          * Decrement Cell Value ----> [down arrow]
          * Set Cell Value ----------> [1-9]
          * Rotate Anticlockwise ----> a
          * Rotate Clockwise --------> d
          * Pause/Resume ------------> p (or the GUI button)
          * Play/Restart ------------> GUI button
        
 * Credits: 
     > https://www.geeksforgeeks.org/sudoku-backtracking-7/
         * This C++ implementation was instrumental in the design of the algorithm found in this Java implementation.
     
 * Author:
     > Brett J Macisaac.
 
 * Coding Conventions:
     > All variable names are composed of lower-case alphanumerical characters and underscores.
     > All variable names are prefixed with 'x_' to indicate its general 'type':
         - 'l_': local.
         - 'f_': field.
         - 's_': static field.
         - 'g_': global.
 
*/


/* Top-level Class
 * The entry-point class of the program.
 
 * Class Composition:
     (a). Publics (1)
     (b). Auxiliaries (1)
 
*/
public class MainSudokuSolver 
{
    
// (a). Publics (1) ====================================================================================================
    
    /* Entry-point Method
     * This method is the program's entry-point function.
     
     * Notes:
         (a). The main purpose of the welcome message is to open the console before the frame opens; if the console
              opens after the frame is opened, the frame will be force-minimised by the console.
 
    */
    public static void main(String[] args) 
    {
        // (a). Display a welcome message in the console.
        System.out.println("Welcome to SudokuSolver. Use this program to solve a sudoku, either manually or via the solving algorithm.");
        
        // This code schedules a job on the event-dispatching thread, which creates and shows the application's GUI.
        javax.swing.SwingUtilities.invokeLater(
            new Runnable() 
            {
                public void run() 
                {
                    CreateAndShowGUI();
                }
            }
        );
        
    }
    
    
// (b). Auxiliaries (1) ================================================================================================
    
    /* Auxiliary of main
     * main uses this method to create the frame.
    */
    private static void CreateAndShowGUI()
    {
        (new SudokuFrame("Sudoku Solver", "sudokus/sudoku_33_1.txt", 3, 3, 50)).setVisible(true);
    }
    
    
}
