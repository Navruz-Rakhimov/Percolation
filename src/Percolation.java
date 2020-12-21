import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] sites;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uff;

    private int size;
    private int count;

    private int virtualTop;
    private int virtualBottom;

    //done
    public Percolation(int n) {
        if(n <= 0){
            throw new IllegalArgumentException("Illegal Argument");
        }

        sites = new boolean[n][n];
        size = n;

        uf = new WeightedQuickUnionUF(n*n + 2);
        uff = new WeightedQuickUnionUF(n*n + 1);

        virtualTop = n*n;
        virtualBottom = n*n + 1;
        count = 0;

        /*for(int i = 0; i < n; i++){
            uf.union(i, size);
            uf.union(size-1-i,size+1);
        }*/
    }

    public void open(int row, int col) {
        validate(row, col);

        int location = getLocation(row, col);

        if(isOpen(row, col)){
            return;
        }

        sites[row-1][col-1] = true;
        count++;

        if(row == 1){
            uf.union(virtualTop, location);
            uff.union(virtualTop, location);
        }

        if(row == size){
            uf.union(virtualBottom, location);
        }

        // Left
        if(isOnGrid(row, col - 1) && isOpen(row, col - 1)){
            uf.union(location, getLocation(row, col - 1));
            uff.union(location, getLocation(row, col - 1));
        }

        // Right
        if(isOnGrid(row, col + 1) && isOpen(row, col+1)){
            uf.union(location, getLocation(row, col + 1));
            uff.union(location, getLocation(row, col + 1));
        }

        // Top
        if(isOnGrid(row - 1, col) && isOpen(row - 1, col)){
            uf.union(location, getLocation(row - 1, col));
            uff.union(location, getLocation(row - 1, col));
        }

        // Bottom
        if(isOnGrid(row + 1, col) && isOpen(row + 1, col)){
            uf.union(location, getLocation(row + 1, col));
            uff.union(location, getLocation(row + 1, col));
        }

    }

    ////done
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return sites[row - 1][col-1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return  uff.find(virtualTop) == uff.find(getLocation(row, col));
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return uf.find(virtualBottom) == uf.find(virtualTop);
    }

    private void validate(int row, int col) {
        if(!isOnGrid(row, col)){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private boolean isOnGrid(int row, int col){
        int r = row - 1;
        int c = col - 1;
        return (r >= 0 && r < size && c >= 0 && c < size);
    }

    private int getLocation(int row, int col){
        return (row-1)*size + col-1;
    }

    public static void main(String[] args){
        In in = new In(args[0]);
        int n = in.readInt();
 
        Percolation percolation = new Percolation(n);

        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            percolation.open(i, j);
        }

        /*int size = Integer.parseInt(args[0]);

        Percolation percolation = new Percolation(size);
        int argSize = args.length;

        for(int i = 1; argSize >= 2; i += 2){
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i+1]);

            StdOut.printf("Adding row: %d col: %d %n", row, col);
            percolation.open(row, col);
            if(percolation.percolates()){
                StdOut.printf("%nThe System percolates %n");
            }
            argSize -= 2;
        }

        if (!percolation.percolates()) {
            StdOut.print("Does not percolate %n");
        }*/

    }

}
