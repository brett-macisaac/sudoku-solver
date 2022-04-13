package SudokuPackage;



public class SudokuCoordinate 
{
    // The dimension (width/height) of the sudoku.
    private int f_size_grid;
    
    private int f_index_row;
    
    private int f_index_col;
    
    
    
    public SudokuCoordinate(int a_size_grid)
    {
        f_size_grid = a_size_grid;
        
        f_index_row = 0;
        f_index_col = 0;
    }
    
    public SudokuCoordinate(int a_size_grid, int a_index_row, int a_index_col)
    {
        f_size_grid = a_size_grid;
        
        Set(a_index_row, a_index_col);
    }
    
    
    
    public int GetRow()
    {
        return f_index_row;
    }
    
    public int GetCol()
    {
        return f_index_col;
    }
    
    public void Set(int a_index_row, int a_index_col)
    {
        if (a_index_row < 0 || a_index_row >= f_size_grid)
        {
            f_index_row = 0;
        }
        else
        {
            f_index_row = a_index_row;
        }
        
        if (a_index_col < 0 || a_index_col >= f_size_grid)
        {
            f_index_col = 0;
        }
        else
        {
            f_index_col = a_index_col;
        }
        
    }
    
    public void Move(SudokuBoard.Direction a_direction)
    {
        if (a_direction == SudokuBoard.Direction.Up)
        {
            DecrementRow();
        }
        else if (a_direction == SudokuBoard.Direction.Down)
        {
            IncrementRow();
        }
        else if (a_direction == SudokuBoard.Direction.Left)
        {
            DecrementColumn();
        }
        else if (a_direction == SudokuBoard.Direction.Right)
        {
            IncrementColumn();
        }
        
    }
    
    public void Increment()
    {
        IncrementRow();
    }
    
    public void IncrementRow()
    {
        f_index_row = (f_index_row + 1) % f_size_grid;
    }
    
    public void IncrementColumn()
    {
        f_index_col = (f_index_col + 1) % f_size_grid;
        
        if (f_index_col == 0)
        {
            IncrementRow();
        }
    }
    
    public void DecrementRow()
    {
        if (f_index_row == 0)
        {
            f_index_row = f_size_grid - 1;
        }
        else
        {
            --f_index_row;
        }
        
    }
    
    public void DecrementColumn()
    {
        if (f_index_col == 0)
        {
            f_index_col = f_size_grid - 1;
            
            DecrementRow();
        }
        else
        {
            --f_index_col;
        }    
        
    }
    
    public boolean Equal(SudokuCoordinate a_coord)
    {
        return f_index_row == a_coord.f_index_row && f_index_col == a_coord.f_index_col;
    }
    
    public boolean NotEqual(SudokuCoordinate a_coord)
    {
        return !Equal(a_coord);
    }
    
    public boolean GreaterThan(SudokuCoordinate a_coord)
    {
        if (f_index_row > a_coord.f_index_row)
        {
            return true;
        }
        else if (f_index_row < a_coord.f_index_row)
        {
            return false;
        }
        else
        {
            return f_index_col > a_coord.f_index_col;
        }
        
    }
    
    public boolean LessThan(SudokuCoordinate a_coord)
    {
        if (f_index_row < a_coord.f_index_row)
        {
            return true;
        }
        else if (f_index_row > a_coord.f_index_row)
        {
            return false;
        }
        else
        {
            return f_index_col < a_coord.f_index_col;
        }
        
    }
    
    public boolean LessThanOrEqual(SudokuCoordinate a_coord)
    {
        return Equal(a_coord) || LessThan(a_coord);
    }
    
    public boolean GreaterThanOrEqual(SudokuCoordinate a_coord)
    {
        return Equal(a_coord) || GreaterThan(a_coord);
    }
    
}
