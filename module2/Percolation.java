

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private int n;
    private int[][] grid;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        this.size = n;

        // create n-by-n grid, with all sites initially blocked(set to 0)
        grid = new int[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                grid[i][j] = 0;
                //  grid[i][j] = i * size + j + 1;
            }
        }

        // create a WeightedQuickUnionUF object
        uf = new WeightedQuickUnionUF(size * size);
    }

    private int[] adjustIndices(int row, int col) {
        return new int[]{row - 1, col - 1};
    }

    private int getGridNumber(int row, int col)
    {
        int[] adjusted = adjustIndices(row, col);
        int adjusted_row = adjusted[0];
        int adjusted_col = adjusted[1];
        // returns an integer value of a grid index, all cells geting number from 1 to end of grid
        int index = adjusted_row * size + adjusted_col + 1;
        return index;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (!isValid(row, col))
        {
            throw new IllegalArgumentException("row and col must be between 1 and " + (size));
        }

        int[] adjusted = adjustIndices(row, col);
        int adjusted_row = adjusted[0];
        int adjusted_col = adjusted[1];

        if (!isOpen(row, col))
        {
            grid[adjusted_row][adjusted_col] = 1;
            numberOfOpenSites++;
        }
    }

    // // is the site (row, col) open?
    public boolean isOpen(int row, int col){

        if (!isValid(row, col))
        {
            throw new IllegalArgumentException("row and col must be between 1 and " + (size));
        }

        int[] adjusted = adjustIndices(row, col);
        int adjusted_row = adjusted[0];
        int adjusted_col = adjusted[1];

        if (grid[adjusted_row][adjusted_col] == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // private boolean isValid(int row, int col) {
    //     return row >= 1 && row <= size && col >= 1 && col <= size;
    // }
    private boolean isValid(int row, int col)
    {
        if (row < 1 || row > (size) || col < 1 || col > (size))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {

        if (!isValid(row, col))
        {
            throw new IllegalArgumentException("row and col must be between 1 and " + (size));
        }

        if (!isOpen(row, col))
        {
            return false;
        }

        int[] adjusted = adjustIndices(row, col);
        int adjusted_row = adjusted[0];
        int adjusted_col = adjusted[1];

        int index = 0;
        int root = uf.find(getGridNumber(row, col));

        int[][] directions = {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
        };

        while (index < directions.length) {
            int newRow = adjusted_row + directions[index][0];
            int newCol = adjusted_col + directions[index][1];
            if (isValid(newRow + 1, newCol + 1) && isOpen(newRow + 1, newCol + 1)) {
                if (getGridNumber(newRow, newCol) > root) {
                    uf.union(root, getGridNumber(newRow + 1, newCol + 1));
                }
                else {
                    uf.union(getGridNumber(newRow + 1, newCol + 1), root);
                }
            }
            index++;
        }

        if (uf.find(getGridNumber(row, col)) <= size) {
            return true;
        }
        else {
            return false;
        }


        // System.out.println("Root: " + uf.find(getGridNumber(row, col))+ " " + uf.count());

        // return true;

    }

    public boolean percolates()
    {
        for (int i = 0; i < size; i++)
        {
            if (isFull(size, i + 1))
            {
                return true;
            }
        }
        return false;
    }

    // get an array of open neighbors of a cell

    // public int[] getNeighbors(int row, int col)
    // {
    //     if (!isValid(row, col))
    //     {
    //         throw new IllegalArgumentException("row and col must be between 1 and " + (size));
    //     }
    //     int[] adjusted = adjustIndices(row, col);
    //     int adjusted_row = adjusted[0];
    //     int adjusted_col = adjusted[1];

    //     //get neighbors of the grid by checking if the cell is open
    //     int[] neighbors = new int[4];
    //     int index = 0;




    //     return neighbors;
    // }

    // // returns the number of open sites
    public int numberOfOpenSites()
    {
        return numberOfOpenSites;
    }

    // // does the system percolate?
    // public boolean percolates()

    private void print_grid()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                System.out.println("Index " + i + " and " + j + " value: " + grid[i][j]);
            }
        }
    }

    // test client (optional)
    public static void main(String[] args){
        int n = 3;
        Percolation p = new Percolation(n);
        System.out.println("Percolation created with n = " + n);

        System.out.println("Grid:" + p.grid[0][0]);

        int size = n;
        // p.print_grid();
        p.open(1,2);
        p.open(2,2);
        // p.open(3,2);
        // p.print_grid();

        System.out.println("Is open 1: " + p.isOpen(1,1));
        System.out.println("Is open 2: " + p.isOpen(2,2));

        for (int i = 1; i <= size; i++)
        {
            for (int j = 1; j <= size; j++)
            {
                System.out.println("Index " + i + " " + j + " " + p.getGridNumber(i, j));
            }
        }
        System.out.println("Is open 8: "  + p.uf.count());

        System.out.println(p.isFull(2,2));

        for (int i = 1; i <= size; i++)
        {
            for (int j = 1; j <= size; j++)
            {
                System.out.println("Is full " + i + " " + j + " " + p.isFull(i, j));
            }
        }

        System.out.println("Percolates: " + p.percolates());


        //System.out.println("Neighbors" + p.getNeighbors(1,1)[0]);

        // print all neighbors of 0,1
        // int[] neighbors = p.getNeighbors(0,1);

        // for (int i = 0; i < neighbors.length; i++)
        // {
        //     System.out.println("Neighbors: " + neighbors[i]);
        // }



    }
}
