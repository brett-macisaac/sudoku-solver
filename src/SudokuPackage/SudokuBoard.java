
package SudokuPackage;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import java.io.*;

import java.util.Scanner;

import javax.swing.JPanel;

import Utils.Rounder;


/* Sudoku Board (Composition of SudokuCell)
 * An instance of this class is a GUI of a sudoku board/grid.
 * A sudoku board is a square grid composed of n*n cells. Each cell is either empty or contains a number from 1 to n.
 * Each cell is either a 'guess cell' or a 'clue cell'. Clue cells are those whose numbers are given to the user, while
   guess cells are those that the user must fill in.
 * The cells are divided into a number of boxes.
 * A sudoku is solved when all of the numbers from 1 to n occur exactly once in each row, column, and box.
 
 * Class Composition:
     (a). Enums (2)
     (b). Fields (23)
         (i). Static Fields (11)
     (c). Constructors (1)
     (d). Publics (10)
     (e). Auxiliaries (16)
*/
public class SudokuBoard
    extends JPanel
{
    
// (a). Enums (2) ======================================================================================================
    
    /*
     * An enum to represent the different background colours of the cells. 
    */
    public enum CellColourEnum
    {
        Normal,
        Selected,
        Valid,
        Invalid
    }
    
    /*
     * An enum to represent the different directions that a user can move their selection.
    */
    public enum Direction
    {
        Up,
        Down,
        Left,
        Right
    }
    
    
    
// (b). Fields (23) ====================================================================================================
    
    /*
     * The sudoku grid.
    */
    private SudokuCell f_grid[][];
    
    /*
     * The width of each box in the grid (no. of cells).
     * The number of boxes per column.
    */
    private int f_width_box;

    /*
     * The height of each box in the grid (no. of cells).
     * The number of boxes per row.
    */
    private int f_height_box;

    /*
     * The dimension (width/height) of the grid.
     * Is equal to f_width_box * f_height_box: by having this value as a field removes the need to constantly calculate it.
    */
    private int f_size_grid;
    
    /*
     * The coordinate of the cell that the user has currently selected.
    */
    private SudokuCoordinate f_coord_selected;
    
    /*
     * A cell's 'normal' background colour.
    */
    private Color f_colour_cell_fill_normal;
    
    /*
     * A cell's background colour when it is selected.
    */
    private Color f_colour_cell_fill_selected;
    
    /*
     * A cell's background colour when it's number is determined to be a (seemingly) valid placement.
    */
    private Color f_colour_cell_fill_valid;
    
    /*
     * A cell's background colour when it's number is determined to be a (seemingly) invalid placement.
    */
    private Color f_colour_cell_fill_invalid;
    
    /*
     * The text colour guess cells.
    */
    private Color f_colour_text_guess_cell;
    
    /*
     * The text colour clue cells.
    */
    private Color f_colour_text_clue_cell;
    
    /*
     * The frame on which this SudokuBoard instance is displayed and controlled by.
    */
    private SudokuFrame f_parent;
    
    
// (b)(i). Static Fields (11) ------------------------------------------------------------------------------------------
    
    // The default width of each box (no. of cells).
    private static final int S_WIDTH_BOX_DEFAULT = 3;
    
    // The default height of each box (no. of cells).
    private static final int S_HEIGHT_BOX_DEFAULT = 3;

    // The max width of a box.
    private static final int S_WIDTH_BOX_MAX = 7;

    // The max height of each box.
    private static final int S_HEIGHT_BOX_MAX = 7;
    
    // The min width of each box. 
    private static final int S_WIDTH_BOX_MIN = 2;

    // The min height of each box.
    private static final int S_HEIGHT_BOX_MIN = 2;
    
    // The maximum height the panel can be (pixels).
    private static final int S_HEIGHT_MAX = (int)( Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.5 );
    
    // The maximum width a panel can be (pixels).
    private static final int S_WIDTH_MAX = (int)( Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5 );
    
    // The width of the boxes' inner borders (pixels).
    private static final int S_WIDTH_BORDER_INNER = 2;
    
    // The width of the boxes' outer borders (pixels).
    private static final int S_WIDTH_BORDER_OUTER = 4;
    
    // The value associated with an empty cell (no value is displayed when a cell has this value).
    private static final int S_VALUE_EMPTY_CELL = 0;
    
    private static final long serialVersionUID = 1L;
    
    
    
// (c). Constructors (1) ===============================================================================================
    
    /*
         * Notes:
             (a). First term is the width from the cells themselves. The second term is the width from the 'inner' borders,
                  which divide the cells within each box. The third term is the width from the 'outer' borders, which
                  divide the boxes and surround the sudoku grid (note that f_height_box is the no. of boxes in each row).
    */
    public SudokuBoard(int a_width_box, int a_height_box, int a_size_cell, Color a_color_back, Color a_colour_cell_fill_normal, 
                       Color a_colour_cell_fill_selected, Color a_colour_cell_fill_valid, Color a_colour_cell_fill_invalid,
                       Color a_colour_text_guess_cell, Color a_colour_text_clue_cell, SudokuFrame a_parent)
    {
        super(new GridBagLayout());
        
        f_parent = a_parent;
        
        // Assign values to the box dimension fields.
        if ( (a_width_box < S_WIDTH_BOX_MIN || a_width_box > S_WIDTH_BOX_MAX) ||
             (a_height_box < S_HEIGHT_BOX_MIN || a_height_box > S_HEIGHT_BOX_MAX) ) // If either dimension is invalid.
        {
            f_width_box = S_WIDTH_BOX_DEFAULT;
            f_height_box = S_HEIGHT_BOX_DEFAULT;
            
        }
        else
        {
            f_width_box = a_width_box;
            f_height_box = a_height_box;
        }
        
        // The size of the grid (n*n) is equal to the product of a box's dimensions.
        f_size_grid = f_width_box * f_height_box;
        
        // Set the colours.
        super.setBackground(a_color_back);
        f_colour_cell_fill_normal = a_colour_cell_fill_normal;
        f_colour_cell_fill_selected = a_colour_cell_fill_selected;
        f_colour_cell_fill_valid = a_colour_cell_fill_valid;
        f_colour_cell_fill_invalid = a_colour_cell_fill_invalid;
        f_colour_text_guess_cell = a_colour_text_guess_cell;
        f_colour_text_clue_cell = a_colour_text_clue_cell;
        
        // Create the grid.
        f_grid = new SudokuCell[f_size_grid][f_size_grid];
        
        // Get the maximum cell size.
        int l_cell_size_max = GetMaxCellSize();
        
        // Determine the cell size (ensure it isn't above the max value).
        int l_cell_size = a_size_cell > l_cell_size_max ? l_cell_size_max : a_size_cell;
        
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        // Populate the grid.
        for (int row = 0; row < f_size_grid; ++row)
        {
            // Set the y-coordinate of the row.
            l_constraints.gridy = row;
            
            for (int col = 0; col < f_size_grid; ++col)
            {
                // Set the x-coordinate of the cell.
                l_constraints.gridx = col;
                
                // Reset the padding for each cell.
                l_constraints.insets.set(0, 0, 0, 0);
                
                // A flag that, when true, indicates that the cell at the current (col,row) coordinate is along the left-edge of a box.
                boolean l_is_along_left_edge_of_box = col == 0 || col % f_width_box == 0;
                
                // A flag that, when true, indicates that the cell at the current (col,row) coordinate is along the left-edge of a box.
                boolean l_is_along_right_edge_of_box = Rounder.NearestMultiple(col, f_width_box) - 1 == col;
                
                // A flag that, when true, indicates that the cell at the current (col,row) coordinate is along the top-edge of a box.
                boolean l_is_along_top_edge_of_box = row == 0 || row % f_height_box == 0;
                
                // A flag that, when true, indicates that the cell at the current (col,row) coordinate is along the left-edge of a box.
                boolean l_is_along_bottom_edge_of_box = Rounder.NearestMultiple(row, f_height_box) - 1 == row;
                
                // Set top border.
                if (l_is_along_top_edge_of_box)
                { l_constraints.insets.top = S_WIDTH_BORDER_OUTER; }
                
                // Set bottom border
                if (row == f_size_grid - 1)
                { l_constraints.insets.bottom = S_WIDTH_BORDER_OUTER; }
                if (!l_is_along_bottom_edge_of_box)
                { l_constraints.insets.bottom = S_WIDTH_BORDER_INNER; }
                
                // Set left border.
                if (l_is_along_left_edge_of_box)
                { l_constraints.insets.left = S_WIDTH_BORDER_OUTER; }
                
                // Set right border
                if (col == f_size_grid - 1)
                { l_constraints.insets.right = S_WIDTH_BORDER_OUTER; }
                else if (!l_is_along_right_edge_of_box)
                { l_constraints.insets.right = S_WIDTH_BORDER_INNER; }
                
                // Create the cell.
                f_grid[row][col] = new SudokuCell(S_VALUE_EMPTY_CELL, false, l_cell_size, l_cell_size, 
                                                  f_colour_cell_fill_normal, f_colour_text_guess_cell);
                
                // Add the cell to the panel with the set constraints.
                super.add(f_grid[row][col], l_constraints);
            }
            
        }
        
        f_coord_selected = new SudokuCoordinate(f_size_grid);
    }
    
    
    
// (d). Publics (10) ===================================================================================================
    
    /*
     * This method attempts to solve the sudoku.
      
     * Return Value:
         > A boolean corresponding to whether the sudoku was solved (or was already solved).
    */
    public boolean Solve()
    {
        // Hide the selected cell.
        f_grid[f_coord_selected.GetRow()][f_coord_selected.GetCol()].setBackground(GetColour(CellColourEnum.Normal));
        
        // A flag that, when true, indicates that the sudoku has been solved.
        boolean l_solved = false;
        
        if (!IsValid())
        { 
            System.out.println("The sudoku doesn't adhere to the rules of sudoku.");
        }
        else if (IsSolved())
        {
            System.out.println("The sudoku is already solved.");
            l_solved = true;
        }
        else if (Solve_BackTracking())
        {
            System.out.println("The sudoku has been solved!");
            l_solved = true;
        }
        else
        {
            System.out.println("The sudoku has no solution.");
        }
        
        // Show the selected cell.
        f_grid[f_coord_selected.GetRow()][f_coord_selected.GetCol()].setBackground(GetColour(CellColourEnum.Selected));
        
        return l_solved;
    }
    
    /*
     * This method removes any guesses.
     
    */
    public void RemoveGuesses()
    {
        for (int row = 0; row < f_size_grid; ++row)
        {
            for (int col = 0; col < f_size_grid; ++col)
            {
                if (!f_grid[row][col].IsClue())
                {
                    f_grid[row][col].SetValue(S_VALUE_EMPTY_CELL);
                }

            }
            
        }
        
    }
    
    /*
     * This method removes all values from the sudoku grid.
     
    */
    public void Clear()
    {
        SudokuCoordinate l_coord = new SudokuCoordinate(f_size_grid);
        
        for (int row = 0; row < f_size_grid; ++row)
        {
            for (int col = 0; col < f_size_grid; ++col)
            {
                l_coord.Set(row, col);
                
                ClearCell(l_coord);
            }
            
        }
        
    }
    
    /*
     * This method removes the value of the cell at the given coordinate.
     
     * Parameters:
         > a_coord: the coordinate corresponding to the cell whose value is to be removed.
     
    */
    public void ClearCell(SudokuCoordinate a_coord)
    {
        f_grid[a_coord.GetRow()][a_coord.GetCol()].SetValue(S_VALUE_EMPTY_CELL);
        
        f_grid[a_coord.GetRow()][a_coord.GetCol()].setBackground(GetColour(CellColourEnum.Normal));
        f_grid[a_coord.GetRow()][a_coord.GetCol()].setForeground(f_colour_text_guess_cell);
    }
    
    /*
     * This method changes (increases/decreases) the value of the cell at coordinate f_coord_selected by 1.
     
     * Parameters:
         > a_increase: a flag that, when true, indicates that the cell's value should be increased; otherwise, it's 
                       decreased.
     
    */
    public void ChangeSelectedValue(boolean a_increase)
    {
        int l_value_current = f_grid[f_coord_selected.GetRow()][f_coord_selected.GetCol()].GetValue();
        
        int l_value_next;
        
        if (a_increase)
        {
            l_value_next = l_value_current + 1;
            
            if (l_value_next > f_size_grid)
            { l_value_next = 1; }
        }
        else
        {
            if (l_value_current == 1 || l_value_current == 0)
            {
                l_value_next = f_size_grid;
            }
            else
            {
                l_value_next = l_value_current - 1;
            }
            
        }
        
        PlaceValue(l_value_next, f_coord_selected);
    }
    
    /*
     * This method changes the value of the cell at coordinate f_coord_selected to the given value.
     
     * Parameters:
         > a_value: the value which is to be placed at the selected cell.
     
    */
    public void ChangeSelectedValue(int a_value)
    {
        PlaceValue(a_value, f_coord_selected);
    }
    
    /*
     * This method is used to indicate whether the board adheres to the rules of sudoku.
     
     * Return Value:
         > a boolean that, if true, indicates that the sudoku board is valid; otherwise, it's invalid.
    */
    public boolean IsValid()
    {
        SudokuCoordinate l_coord = new SudokuCoordinate(f_size_grid);
        
        for (int row = 0; row < f_size_grid; ++row)
        {
            for (int col = 0; col < f_size_grid; ++col)
            {
                l_coord.Set(row, col);
                
                if (!IsCellValid(l_coord))
                { return false; }
            }

        }

        return true;
    }
    
    /*
     * This method is used to indicate whether the sudoku is solved.
     
     * Return Value:
         > a boolean that, if true, indicates that the sudoku is solved; otherwise, it's not solved.
    */
    public boolean IsSolved()
    {
        for (int row = 0; row < f_size_grid; ++row)
        {
            for (int col = 0; col < f_size_grid; ++col)
            {
                if (f_grid[row][col].GetValue() == S_VALUE_EMPTY_CELL || !IsCellValid(new SudokuCoordinate(f_size_grid, row, col)))
                { return false; }
            }

        }

        return true;
    }
    
    /*
     * This method places the value a_value at the cell located at the given coordinate.
     
     * Parameters:
         > a_value: the value to place.
         > a_coord: the coordinate at a_value is to be placed.
    */
    public void SetFromFile(String a_file_sudoku)
    {
        File l_file = new File(a_file_sudoku);
        
        Scanner l_scanner = null;
        
        // Clear the board. 
        Clear();
        
        try 
        {
            l_scanner = new Scanner(l_file);
            
            int l_width_box = l_scanner.nextInt();
            int l_height_box = l_scanner.nextInt();
            
            if (l_width_box != f_width_box || l_height_box != f_height_box)
            { return; }
            
            for (int row  = 0; row < f_size_grid; ++row)
            {
                for (int col  = 0; col < f_size_grid; ++col)
                {
                    f_grid[row][col].SetValue(l_scanner.nextInt());
                    
                    // If the cell isn't empty, it's considered a clue.
                    if (f_grid[row][col].GetValue() != S_VALUE_EMPTY_CELL)
                    {
                        f_grid[row][col].setForeground(f_colour_text_clue_cell);
                        f_grid[row][col].SetIsClue(true);
                    }
                    
                }
                
            }
            
        } 
        catch (Exception e)   
        {
            e.printStackTrace();
        }
        finally
        {
            l_scanner.close();
        }
        
        // Reset the selected cell.
        ResetSelectedCell();
    }
    
    public void MoveSelection(Direction a_direction, boolean a_thread_control)
    {
        // Unselect the currently selected cell.
        SetCellColour(f_coord_selected, CellColourEnum.Normal, false);
        
        // Move the selected coordinate.
        f_coord_selected.Move(a_direction);
        
        // Select the cell at f_coord_selected.
        SetCellColour(f_coord_selected, CellColourEnum.Selected, a_thread_control);
    }
    
    
    
// (e). Auxiliaries (16) ===============================================================================================
    
    /* Auxiliary of Solve
     * This method tries to solve the sudoku using the backtracking technique.
     * An assumption is that the sudoku is valid to begin with: i.e. the method assumes that none of the pre-filled 
       cells violate the rules of sudoku.
   
     * Notes:
        (a). All possible values may not need to be iterated over; it could even be the case that only value is
             considered for this cell. In other words, iterate over at most all values.
        (b). Note that it's not necessarily the case that the sudoku cannot be solved with the current value at the cell
             (l_row, l_col), rather that this cannot occur on the current 'branch' of the recursive program.
        (c). If the current Solve_BackTracking call is the top/first one, this means that the sudoku cannot be
             solved: i.e. it's an impossible puzzle.
    */
    private boolean Solve_BackTracking()
    {
        // Variable to store the (empty) cell being considered by this Solve_BackTracking call.
        SudokuCoordinate l_coord = new SudokuCoordinate(f_size_grid);
        
        // Get the next empty cell; if a cell cannot be assigned, this means all cells are filled, which would imply that the sudoku is solved.
        if (!AssignNextEmptyCell(l_coord))
        { 
            return true; 
        }
        
        // Show which cell is currently under consideration.
        SetCellColour(l_coord, CellColourEnum.Selected, false);
    
        // (a). Iterate over (at most) all possible values.
        for (int value = 1; value <= f_size_grid; ++value)
        {
            // Try to place the value; if it cannot be placed, continue to the next value.
            if (!PlaceValue(value, l_coord))
            { 
                HighlightCell(l_coord, CellColourEnum.Invalid);
                continue; 
            }
            HighlightCell(l_coord, CellColourEnum.Valid);
            
            SetCellColour(l_coord, CellColourEnum.Normal, false);
    
            // Execute a recursive call; if this call returns true, then the sudoku must be solved; therefore, return true.
            if (Solve_BackTracking())
            { return true; }
    
            // (b). If this line is reached, this means the sudoku cannot be solved with the current value. 
            SetCellColour(l_coord, CellColourEnum.Selected, true);
        }
        
        // Clear the cell.
        f_grid[l_coord.GetRow()][l_coord.GetCol()].SetValue(S_VALUE_EMPTY_CELL);
        SetCellColour(l_coord, CellColourEnum.Normal, false);
    
        // (c). Return false so that the previous Solve_BackTracking call knows to try a different value.
        return false;
    }
    
    /* Auxiliary of ChangeSelectedValue, Solve_BackTracking
     * This method places the value a_value at the cell located at the given coordinate.
     
     * Parameters:
         > a_value: the value to place.
         > a_coord: the coordinate at which a_value is to be placed.
         
     * Return Value:
         > a boolean that indicates whether the placement is valid
    */
    private boolean PlaceValue(int a_value, SudokuCoordinate a_coord)
    {
        
        try 
        {
            if (a_value > f_size_grid || a_value < 1)
            {
                System.out.println("This value is invalid.");
                return false;
            }
            if (f_grid[a_coord.GetRow()][a_coord.GetCol()].IsClue())
            {
                System.out.println("You cannot place a value in a clue cell.");
                return false;
            }
            
            f_grid[a_coord.GetRow()][a_coord.GetCol()].SetValue(a_value); 
        }
        catch (IndexOutOfBoundsException e)
        { 
            e.printStackTrace();
            return false;
        }
        
        return IsCellValid(a_coord);
    }
    
    /* Auxiliary of SetFromFile
     * This method sets the colour of the selected cell to the origin.
    */
    private void ResetSelectedCell()
    {
        // Unselect the currently selected cell.
        SetCellColour(f_coord_selected, CellColourEnum.Normal, false);
        
        // Move selection to the origin.
        f_coord_selected.Set(0, 0);
        
        // Display the selection.
        SetCellColour(f_coord_selected, CellColourEnum.Selected, false);
    }
    
    /* Auxiliary of Solve_BackTracking
     * This method sets the colour of the given cell and then returns it to its previous colour once the thread has been
       paused/halted for a period of time.
     
     * Parameters:
         > a_coord: the coordinate of the cell whose colour is to be highlighted.
         > a_colour: an enum value which represents the colour to highlight.
    */
    private void HighlightCell(SudokuCoordinate a_coord, CellColourEnum a_colour)
    {
        HighlightCell(a_coord, GetColour(a_colour));
    }
    
    /* Auxiliary of HighlightCell
     * This method sets the colour of the given cell and then returns it to its previous colour once the thread has been
       paused/halted for a period of time.
     
     * Parameters:
         > a_coord: the coordinate of the cell whose colour is to be highlighted.
         > a_colour: the colour to highlight.
    */
    private void HighlightCell(SudokuCoordinate a_coord, Color a_colour)
    {
        // The bar's current colour.
        Color l_colour_current = f_grid[a_coord.GetRow()][a_coord.GetCol()].getBackground();
        
        // Highlight the bar.
        SetCellColour(a_coord, a_colour, true);
        
        // Return the bar's colour to the one prior to the highlight.
        SetCellColour(a_coord, l_colour_current, false);
    }
    
    /* Auxiliary of 
     * This method sets the colour of the cells within the given range and then returns them to their previous colour 
       once the thread has been paused/halted for a period of time.
     
     * Parameters:
         > a_coord_start: the coordinate of the first cell whose colour is to be highlighted.
         > a_coord_end: the coordinate of the last cell whose colour is to be highlighted.
         > a_colour: an enum value which represents the colour to highlight.
    */
    private void HighlightCellRange(SudokuCoordinate a_coord_start, SudokuCoordinate a_coord_end, CellColourEnum a_colour)
    {
        HighlightCellRange(a_coord_start, a_coord_end, GetColour(a_colour));
    }
    
    /* Auxiliary of HighlightCellRange
     * This method sets the colour of the cells within the given range and then returns them to their previous colour 
       once the thread has been paused/halted for a period of time.
     
     * Parameters:
         > a_coord_start: the coordinate of the first cell whose colour is to be highlighted.
         > a_coord_end: the coordinate of the last cell whose colour is to be highlighted.
         > a_colour: the colour to highlight.
    */
    private void HighlightCellRange(SudokuCoordinate a_coord_start, SudokuCoordinate a_coord_end, Color a_colour)
    {
        // The bars' current colour.
        // The assumption here is that all of the bars in the range have the colour of the bar at index a_index_start.
        Color l_colour_current = Color.BLACK;
        try
        { l_colour_current = f_grid[a_coord_start.GetRow()][a_coord_start.GetCol()].getBackground(); }
        catch (IndexOutOfBoundsException e)
        { e.printStackTrace(); }
        
        // Highlight the bar.
        SetCellRangeColour(a_coord_start, a_coord_end, a_colour, true);
        
        // Return the bar's colour to the one prior to the highlight.
        SetCellRangeColour(a_coord_start, a_coord_end, l_colour_current, false);
    }
    
    /* Auxiliary of ResetSelectedCell, Solve_BackTracking, MoveSelection
     * This method sets the cell at the given coordinate to the given colour.
     
     * Parameters:
         > a_coord_start: the coordinate of the first cell whose colour is to be set.
         > a_colour: an enum value which corresponds to the colour to set.
         > a_thread_control: a flag that, when true, indicates that the thread should be paused/halted before execution
                             continues.
    */
    private void SetCellColour(SudokuCoordinate a_coord, CellColourEnum a_colour, boolean a_thread_control)
    {   
        SetCellColour(a_coord, GetColour(a_colour), a_thread_control);
    }
    
    /* Auxiliary of HighlightCell, SetCellColour
     * This method sets the cell at the given coordinate to the given colour.
     
     * Parameters:
         > a_coord: the coordinate of the cell whose colour is to be set.
         > a_colour: the colour to set.
         > a_thread_control: a flag that, when true, indicates that the thread should be paused/halted before execution
                             continues.
    */
    private void SetCellColour(SudokuCoordinate a_coord, Color a_colour, boolean a_thread_control)
    {
        try
        { f_grid[a_coord.GetRow()][a_coord.GetCol()].SetColourBack(a_colour); }
        catch (IndexOutOfBoundsException e)
        { e.printStackTrace(); }
        
        if (a_thread_control)
        { ThreadControl(); }
    }
    
    /* Auxiliary of 
     * This method sets the cells within the given range to the given colour.
     
     * Parameters:
         > a_coord_start: the coordinate of the first cell whose colour is to be set.
         > a_coord_end: the coordinate of the last cell whose colour is to be set.
         > a_colour: an enum value which corresponds to the colour to set.
         > a_thread_control: a flag that, when true, indicates that the thread should be paused/halted before execution
                             continues.
    */
    private void SetCellRangeColour(SudokuCoordinate a_coord_start, SudokuCoordinate a_coord_end, 
                                    CellColourEnum a_colour, boolean a_thread_control)
    {   
        SetCellRangeColour(a_coord_start, a_coord_end, GetColour(a_colour), a_thread_control);
    }
    
    /* Auxiliary of SetCellRangeColour
     * This method sets the cells within the given range to the given colour.
     
     * Parameters:
         > a_coord_start: the coordinate of the first cell whose colour is to be set.
         > a_coord_end: the coordinate of the last cell whose colour is to be set.
         > a_colour: the colour to set.
         > a_thread_control: a flag that, when true, indicates that the thread should be paused/halted before execution
                             continues.
    */
    private void SetCellRangeColour(SudokuCoordinate a_coord_start, SudokuCoordinate a_coord_end, Color a_colour, 
                                    boolean a_thread_control)
    {    
        SudokuCoordinate l_coord_curr = new SudokuCoordinate(f_size_grid, a_coord_start.GetRow(), a_coord_start.GetCol());
        
        do
        {
            SetCellColour(l_coord_curr, a_colour, false);
            
            l_coord_curr.Increment();
            
        } while (l_coord_curr.LessThan(a_coord_end));
        
        if (a_thread_control)
        { ThreadControl(); }
    }
    
    /* Auxiliary of SudokuBoard
     * Returns the maximum size of each cell for the board to fit within the specified maximums (S_WIDTH_MAX and
       S_HEIGHT_MAX).
    */
    private int GetMaxCellSize() 
    {
        // The total width from the borders in a row.
        int l_width_borders = ( S_WIDTH_BORDER_INNER * (f_width_box - 1) ) 
                                + ( S_WIDTH_BORDER_OUTER * (2 + (f_height_box - 1)) );
        
        // The total height from the borders in a column.
        int l_height_borders = ( S_WIDTH_BORDER_INNER * (f_height_box - 1) ) 
                                 + ( S_WIDTH_BORDER_OUTER * (2 + (f_width_box - 1)) );
        
        // The total space available for the cells in a row.
        int l_width_cells = S_WIDTH_MAX - l_width_borders;
        
        // The total space available for the cells in a column.
        int l_height_cells = S_HEIGHT_MAX - l_height_borders;
        
        // The maximum width per cell.
        int l_width_cell = l_width_cells / f_size_grid;
        
        // The maximum height per cell.
        int l_height_cell = l_height_cells / f_size_grid;
        
        return l_width_cell > l_height_cell ? l_height_cell : l_width_cell;
    }
    
    /* Auxiliary of SetCellColour, HighlightCell
     * This method returns the colour which corresponds to the given enum value.
     
     * Parameters:
         > a_colour: an enum value which corresponds to the colour to return.
    */
    private Color GetColour(CellColourEnum a_colour)
    {
        if (a_colour == CellColourEnum.Normal)
        {
            return f_colour_cell_fill_normal;
        }
        else if (a_colour == CellColourEnum.Selected)
        {
            return f_colour_cell_fill_selected;
        }
        else if (a_colour == CellColourEnum.Valid)
        {
            return f_colour_cell_fill_valid;
        }
        else if (a_colour == CellColourEnum.Invalid)
        {
            return f_colour_cell_fill_invalid;
        }
        
        return Color.WHITE;
    }
    
    /* Auxiliary of IsValid, IsSolved, PlaceValue
     * This method returns whether or not the cell adheres to the rules of sudoku.
    */
    private boolean IsCellValid(SudokuCoordinate a_coord)
    {
        int l_row = a_coord.GetRow();
        int l_col = a_coord.GetCol();
        
        int l_value = f_grid[l_row][l_col].GetValue();

        // A cell can always be empty.
        if (l_value == S_VALUE_EMPTY_CELL)
        { return true; }

        // Check if the value is equal to any within its row.
        for (int col = 0; col < f_size_grid; ++col)
        {
            if (col == l_col)
            { continue; }

            if (l_value == f_grid[l_row][col].GetValue())
            { return false; }
        }

        // Check if the value is equal to any within its column.
        for (int row = 0; row < f_size_grid; ++row)
        {
            if (row == l_row)
            { continue; }

            if (l_value == f_grid[row][l_col].GetValue())
            { return false; }
        }

        
        // Check if the value is equal to any other within its box.
        // Each box can be define by four values: a pair of min/max values for the rows and columns.

        // Get the min and max row for the box at (a_col, a_row).
        int l_index_row_min = l_row - (l_row % f_height_box);
        int l_index_row_max = l_index_row_min + (f_height_box - 1);

        // Get the min and max column for the box at (a_col, a_row).
        int l_index_col_min = l_col - (l_col % f_width_box);
        int l_index_col_max = l_index_col_min + (f_width_box - 1);

        // Check if the value is equal to any within its box.
        for (int row = l_index_row_min; row < l_index_row_max; ++row)
        {
            for (int col = l_index_col_min; col < l_index_col_max; ++col)
            {
                if (row == l_row && col == l_col)
                { continue; }

                if (l_value == f_grid[row][col].GetValue())
                { return false; }
            }

        }

        // If this point is reached, all of the conditions/rules have been satisfied; thus, return true.
        return true;
    }
    
    /* Auxiliary of Solve_BackTracking
         * Assigns to a_row and a_col the values which correspond to the location of the 'earliest' empty cell of f_grid.
           In this context, the 'earliest' empty cell is the one at the lowest column/vertical position of the lowest row.
    */
    private boolean AssignNextEmptyCell(SudokuCoordinate a_coord)
    {
        for (int row = 0; row < f_size_grid; ++row)
        {
            for (int col = 0; col < f_size_grid; ++col)
            {
                if (f_grid[row][col].GetValue() == S_VALUE_EMPTY_CELL)
                {
                    a_coord.Set(row, col);

                    return true;
                }

            }

        }

        return false;
    }
    
    /* Auxiliary of SetCellColour, SetCellRangeColour
     * This method either (a) pauses the current thread for a given period of time, or (b) waits until it's notified, 
       depending on the state of f_parent.
    */
    private void ThreadControl()
    {
        try 
        {
            if (f_parent.IsStepping())
            {
                // Wait until the 'step' button is pressed.
                //Thread.currentThread().wait();
                synchronized (f_parent) 
                {
                    f_parent.wait();
                }

            }
            else
            {
                Thread.currentThread().sleep(f_parent.GetSortRate());
            }
            
        } catch (InterruptedException e) 
        { }
        
    }
    
    
}
