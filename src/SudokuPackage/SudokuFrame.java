
package SudokuPackage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollBar;

import Utils.ButtonMaker;


/* The Program's Frame
 * This class encapsulates the GUI and execution of the program. 

 * Class Composition:
     (a). Fields (11)
         (i). Static Fields (3)
     (b). Constructors (1)
     (c). Publics (3)
         (i). Accessors (2)
     (d). Event Handlers (4)
     (e). Nested Classes (1)
         (i). Inner Classes (1)

*/
public class SudokuFrame
    extends JFrame
        implements Runnable
{
    
// (a). Fields (11) ====================================================================================================
    
    /*
     * The sudoku grid.
    */
    private SudokuBoard f_sudoku;
    
    /*
     * The button that, when clicked, starts the process of solving the sudoku.  
    */
    private JButton f_btn_solve;
    
    /*
     * The button that, when clicked, resets the sudoku: i.e. removes the 'guesses'.  
    */
    private JButton f_btn_reset;
    
    /*
     * The button that, when clicked, proceeds to the next step of solving the sudoku..  
    */
    private JButton f_btn_step;
    
    /*
     * The value of this scroll-bar is the rate at which the sudoku is solves 
    */
    private JScrollBar f_scr_solve_rate;
    
    /*
     * When this checkbox is checked, the process of solving the sudoku can only progress by the user clicking 
       f_btn_step, allowing them to go through the process step-by-step.
    */
    private JCheckBox f_chk_step;
    
    /*
     * The string of the file in which the sudoku is stored. 
    */
    private String f_file_sudoku;
    
    /*
     * The thread on which the sudoku solving algorithm is run.
    */
    private Thread f_thread_solve;
    
    
// (a)(i). Static Fields (3) -------------------------------------------------------------------------------------------
    
    // The minimum delay between solving steps (ms); the delay corresponding to the fastest solving rate.
    private static final int S_MAX_SOLVE_RATE = 1;
    
    // The maximum delay between solving steps (ms); the delay corresponding to the slowest solving rate.
    private static final int S_MIN_SOLVE_RATE = 1000;
    
    // The increment (ms) between different solving rates (used to set the increment of f_scr_solve_rate).
    private static final int S_INCREMENT_SOLVE_RATE = 1;
    
    private static final long serialVersionUID = 1L;
    
    
    
// (b). Constructors (1) ===============================================================================================
    
    /* Constructor
    
     * Parameters:
         > a_title: the frame's title.
         > a_file_sudoku: the string of the file in which the sudoku is stored.
         > a_width_box: the width (no. of cells) of each box in the sudoku board.
         > a_height_box: the height (no. of cells) of each box in the sudoku board.
         > a_size_cell: the dimension (n*n) of each cell in the sudoku board (in pixels).
    */
    public SudokuFrame(String a_title, String a_file_sudoku, int a_width_box, int a_height_box, int a_size_cell)
    {
        // Call base class' constructor.
        super(a_title);
        
        // Record the name of the file in which the desired sudoku is stored.
        f_file_sudoku = a_file_sudoku;
        
        // Make sure we have nice window decorations. Doesn't seem to do anything.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        // Ensure the frame exits when the user closes the window (i.e. clicks the 'cross' button).
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Ensure that the user can't change the frame's size.
        super.setResizable(false);
        
        // Align the window to the center of the screen (top left corner in centre of screen).
        super.setLocationRelativeTo(null);
        
        // Set the layout manager.
        super.getContentPane().setLayout(new GridBagLayout());
        
        // Create a constraints object, which allows for the various graphical elements to be arranged.
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        // Set general constraints.
        l_constraints.fill = GridBagConstraints.HORIZONTAL; // Objects take up all available horizontal space.
        
        
        // Create the sudoku board.
        
        // The dimensions of the sudoku.
        f_sudoku = new SudokuBoard(a_width_box, a_height_box, a_size_cell, Color.BLACK, Color.WHITE, new Color(255,215,0),
                                   Color.GREEN, Color.RED, Color.GRAY, Color.BLACK, this);
        f_sudoku.SetFromFile(f_file_sudoku);
        
        
        // Create the UI elements.
        
        f_btn_solve = ButtonMaker.CreateButton("Solve", e -> EvtHnd_Solve());
        f_btn_solve.setEnabled(true);
        
        f_btn_reset = ButtonMaker.CreateButton("Reset", e -> EvtHnd_Reset());
        f_btn_reset.setEnabled(true);
        
        f_btn_step = ButtonMaker.CreateButton("Step", e -> EvtHnd_Step());
        f_btn_step.setEnabled(false);
        
        f_scr_solve_rate = new JScrollBar(JScrollBar.HORIZONTAL, 100, S_INCREMENT_SOLVE_RATE, S_MAX_SOLVE_RATE, 
                                          S_MIN_SOLVE_RATE);
        
        f_chk_step = new JCheckBox("Step", false);
        f_chk_step.addActionListener(e -> EvtHnd_StepCheckBox());
        
        
        // Add the GUI elements to the frame.
        
        l_constraints.gridx = 0; l_constraints.gridy = 0; // (0,0)
        super.add(f_sudoku, l_constraints);
        
        l_constraints.gridx = 0; l_constraints.gridy = 1; // (0,1)
        super.add(f_btn_solve, l_constraints);
        
        l_constraints.gridx = 0; l_constraints.gridy = 2; // (0,2)
        super.add(f_btn_reset, l_constraints);
        
        l_constraints.gridx = 0; l_constraints.gridy = 3; // (0,3)
        super.add(f_btn_step, l_constraints);
        
        l_constraints.gridx = 0; l_constraints.gridy = 4; // (0,4)
        super.add(f_scr_solve_rate, l_constraints);
        
        l_constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        l_constraints.gridx = 0; l_constraints.gridy = 5; // (0,5)
        super.add(f_chk_step, l_constraints);
        
        
        // Force layout manager to place GUI elements.
        super.pack();
        
        // Add the controls.
        super.addKeyListener(new SudokuKeyboardControls());
        
        // Get the focus (necessary to recognise keyboard input).
        super.setFocusable(true);
        super.requestFocusInWindow();
    }
    
    
    
// (c). Publics (3) ====================================================================================================
    
    /* Implementation of Runnable
     * This is the code that executes when f_thread_solve is instantiated and run.
    */
    @Override
    public void run()
    {
        // Ensure the sudoku has no guesses: i.e. should only be the clues.
        f_sudoku.RemoveGuesses();
        
        // Solve the sudoku.
        f_sudoku.Solve();
        
        // Return the controls.
        f_btn_solve.setEnabled(true);
        f_btn_reset.setEnabled(true);
        
        // Ensure that the frame has the focus so that the keyboard controls work.
        super.requestFocusInWindow();
        
        // Delete the thread.
        f_thread_solve = null;
    }
    
    
// (c)(i). Accessors (2) -----------------------------------------------------------------------------------------------
    
    /* Accessor of f_scr_solve_rate
     * Returns the value of the scroll-bar f_scr_solve_rate.
    */
    public int GetSortRate()
    {
        return f_scr_solve_rate.getValue();
    }
    
    /* Accessor of f_chk_step
     * Returns the status of the checkbox f_chk_step.
    */
    public boolean IsStepping()
    {
        return f_chk_step.isSelected();
    }
    
    
    
// (d). Event Handlers (4) =============================================================================================
    
    /* Event Handler of f_btn_solve
         *
    */
    private void EvtHnd_Solve()
    {   
        // The sudoku cannot be solved if it's already being sorted.
        f_btn_solve.setEnabled(false);
        
        // Shouldn't be able to reset the array mid-solve.
        f_btn_reset.setEnabled(false);
        
        f_thread_solve = new Thread(this);
        f_thread_solve.start();
    }
    
   /* Event Handler of f_btn_reset
    *
   */
   private void EvtHnd_Reset()
   {
       // Remove all guesses from the sudoku board.
       f_sudoku.RemoveGuesses();
       
       // Ensure that the frame has the focus so that the keyboard controls work.
       super.requestFocusInWindow();
   }
    
    /* Event Handler of f_btn_step
     *
    */
    private void EvtHnd_Step()
    {
        if (f_thread_solve != null)
        {
            synchronized (this) 
            {
                // Awake the thread so that the solving can continue.
                this.notifyAll();
            }
        }
        else
        {
            // Redirect to the event handler of f_btn_solve (i.e. f_btn_step essentially becomes f_btn_solve).
            EvtHnd_Solve();
        }
        
    }
    
    /* Event Handler of f_chk_step
     *
    */
    private void EvtHnd_StepCheckBox()
    {
        // The step button should only be available when in the 'step' mode.
        f_btn_step.setEnabled(f_chk_step.isSelected());
        
        // If the checkbox was just unchecked and the sorting thread is active.
        if (!f_chk_step.isSelected() && f_thread_solve != null)
        {
            synchronized (this)
            {
                // Awake the thread so that the sorting can continue.
                this.notifyAll();
            }

        }
        
    }
    
    
    
// (e). Nested Classes (1) =============================================================================================
    
    
// (e)(i). Inner Classes (1) -------------------------------------------------------------------------------------------
     
     /* Keyboard Controls
      * An instance of this inner class is what handles the keyboard input.
      
      * Composition:
          (a'). Public Methods (1) 
     */
    private class SudokuKeyboardControls
        extends KeyAdapter
    {
        
    // (a'). Public Methods (1) ========================================================================================
        
        /* Implementation of KeyAdapter.keyReleased(...)
         * 
        */
        @Override
        public void keyReleased(KeyEvent e)
        {
            // Don't handle input when the sudoku is being solved.
            if (f_thread_solve != null)
            { return; }
            
            int l_key_code = e.getKeyCode();
            
            if (l_key_code == KeyEvent.VK_W)
            {
                f_sudoku.MoveSelection(SudokuBoard.Direction.Up, false);
            }
            else if (l_key_code == KeyEvent.VK_A)
            {
                f_sudoku.MoveSelection(SudokuBoard.Direction.Left, false);
            }
            else if (l_key_code == KeyEvent.VK_S)
            {
                f_sudoku.MoveSelection(SudokuBoard.Direction.Down, false);
            }
            else if (l_key_code == KeyEvent.VK_D)
            {
                f_sudoku.MoveSelection(SudokuBoard.Direction.Right, false);
            }
            else if (l_key_code == KeyEvent.VK_UP)
            {
                f_sudoku.ChangeSelectedValue(true);
            }
            else if (l_key_code == KeyEvent.VK_DOWN)
            {
                f_sudoku.ChangeSelectedValue(false);
            }
            else if (l_key_code == KeyEvent.VK_1)
            {
                f_sudoku.ChangeSelectedValue(1);
            }
            else if (l_key_code == KeyEvent.VK_2)
            {
                f_sudoku.ChangeSelectedValue(2);
            }
            else if (l_key_code == KeyEvent.VK_3)
            {
                f_sudoku.ChangeSelectedValue(3);
            }
            else if (l_key_code == KeyEvent.VK_4)
            {
                f_sudoku.ChangeSelectedValue(4);
            }
            else if (l_key_code == KeyEvent.VK_5)
            {
                f_sudoku.ChangeSelectedValue(5);
            }
            else if (l_key_code == KeyEvent.VK_6)
            {
                f_sudoku.ChangeSelectedValue(6);
            }
            else if (l_key_code == KeyEvent.VK_7)
            {
                f_sudoku.ChangeSelectedValue(7);
            }
            else if (l_key_code == KeyEvent.VK_8)
            {
                f_sudoku.ChangeSelectedValue(8);
            }
            else if (l_key_code == KeyEvent.VK_9)
            {
                f_sudoku.ChangeSelectedValue(9);
            }
            
        }
        
    } // private class SudokuKeyboardControls
    
    
} // public class SudokuFrame
