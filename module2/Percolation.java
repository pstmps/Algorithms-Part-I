import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private int n;
    private byte[][] grid;

    private int numberOfOpenSites = 0;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF fillOnly;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        this.size = n;

        // create n-by-n grid, with all sites initially blocked(set to 0)
        grid = new byte[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                grid[i][j] = 0;
                //  grid[i][j] = i * size + j + 1;
            }
        }

        // create a WeightedQuickUnionUF object
        uf = new WeightedQuickUnionUF(size * size + 2);
        fillOnly = new WeightedQuickUnionUF(size * size + 2);
    }

    private int[] adjustIndices(int row, int col) {
        return new int[]{row - 1, col - 1};
    }

    private int getGridNumber(int row, int col)
    {
        int[] adjusted = adjustIndices(row, col);
        int adjustedRow = adjusted[0];
        int adjustedCol = adjusted[1];
        // returns an integer value of a grid index, all cells geting number from 1 to end of grid
        int index = adjustedRow * size + adjustedCol + 1;
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
        int adjustedRow = adjusted[0];
        int adjustedCol = adjusted[1];

        if (!isOpen(row, col))
        {
            grid[adjustedRow][adjustedCol] = 1;
            numberOfOpenSites++;
            connect(row, col);
        }
    }

    // // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (!isValid(row, col))
        {
            throw new IllegalArgumentException("row and col must be between 1 and " + (size));
        }

        int[] adjusted = adjustIndices(row, col);
        int adjustedRow = adjusted[0];
        int adjustedCol = adjusted[1];

        return (grid[adjustedRow][adjustedCol] == 1);

    }

    private boolean isValid(int row, int col)
    {
        return (row >= 1 && row <= size && col >= 1 && col <= size);
    }

    private void connect(int row, int col) {

        int[] adjusted = adjustIndices(row, col);
        int adjustedRow = adjusted[0];
        int adjustedCol = adjusted[1];

        int index = 0;
        int root;
        if (row == 1) {
            uf.union(0, getGridNumber(row, col));
            fillOnly.union(0, getGridNumber(row,col));
            root = 0;
        } else {
            root = uf.find(getGridNumber(row, col));
        }

        if (row == size) {
            uf.union(size * size + 1, getGridNumber(row, col));
        }

        // int root = uf.find(getGridNumber(row, col));

        int[][] directions = {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
        };

        while (index < directions.length) {
            int newRow = adjustedRow + directions[index][0];
            int newCol = adjustedCol + directions[index][1];
            if (isValid(newRow + 1, newCol + 1) && isOpen(newRow + 1, newCol + 1)) {
                if (getGridNumber(newRow + 1, newCol + 1) > root) {
                    uf.union(root, getGridNumber(newRow + 1, newCol + 1));
                    fillOnly.union(root, getGridNumber(newRow + 1, newCol + 1));
                }
                else {
                    uf.union(getGridNumber(newRow + 1, newCol + 1), root);
                    fillOnly.union(root, getGridNumber(newRow + 1, newCol + 1));
                }
            }
            index++;
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

        return fillOnly.find(0) == fillOnly.find(getGridNumber(row, col));

        // return uf.find(getGridNumber(row, col)) <= size;

        // System.out.println("Root: " + uf.find(getGridNumber(row, col))+ " " + uf.count());

        // return true;

    }

    public boolean percolates()
    {
        return uf.find(0) == uf.find(size * size + 1);
        // for (int i = 1; i <= size; i++)
        // {
        //     // System.out.println("checking"+ size + " " + i);
        //     if (isFull(size, i))
        //     {
        //         return true;
        //     }
        // }
        // return false;
    }

    // // returns the number of open sites
    public int numberOfOpenSites()
    {
        return numberOfOpenSites;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(10);

        int[][] cellsToOpen = {
            {1, 1},
            {1, 10},
            {2, 10},
            {2, 1},  // down
            {3, 1}, // left
            {3, 2},   // right
            {3, 3},
            {3, 2},
            {4, 3},
            {5, 3},
            {5, 4},
            {5, 5},
            {5, 6},
            {6, 6},
            {7, 6},
            {8, 6},
            // {9, 6},
            {10, 7}
        };

        for (int[] cell : cellsToOpen) {
            percolation.open(cell[0], cell[1]);
        }

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                percolation.open(i,j);
                System.out.print(percolation.isOpen(i, j) + " ");
            }
            System.out.println();
        }


        //    Percolation percolation = new Percolation(3);
        // percolation.open(1, 1);
        System.out.println(percolation.isFull(2, 9));
        System.out.println(percolation.percolates());

    }
}
