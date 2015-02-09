
public class Percolation {
  private WeightedQuickUnionUF uf;
  private int gridSize;
  private int area;
  private boolean[] isOpenArray;

  public Percolation(int N) {
    if (N <= 0) throw new IllegalArgumentException("N must be greater than 0");
    gridSize = N;
    area = gridSize * gridSize +2;
    isOpenArray = new boolean[area];
    for (int i = 0; i < area; i++) isOpenArray[i] = false;
    isOpenArray[0] = true;
    isOpenArray[area - 1] = true;
    uf = new WeightedQuickUnionUF(area);
  }

  public void open(int x, int y) {
    checkWithinRange(x ,y);
    int arrayIndex = xyTo1D(x, y);
    isOpenArray[arrayIndex] = true;

    connectUF(x, y, x-1, y); // Above
    connectUF(x, y, x+1, y); // Below
    connectUF(x, y, x, y-1); // Left
    connectUF(x, y, x, y+1); // Right
  }

  public boolean isOpen(int x, int y) {
    checkWithinRange(x ,y);
    int arrayIndex = xyTo1D(x,y);
    return isOpenArray[arrayIndex];
  }

  public boolean isFull(int x, int y) {
    checkWithinRange(x ,y);
    return uf.connected(0, xyTo1D(x, y));
  }

  public boolean percolates() {
    return uf.connected(0, area-1);
  }

  private void checkWithinRange(int x, int y) {
    if (x < 1 || x > gridSize) throw new IndexOutOfBoundsException("Row index out of bounds");
    if (y < 1 || y > gridSize) throw new IndexOutOfBoundsException("Column index out of bounds");
  }

  private void connectUF(int xA, int yA, int xB, int yB) {
    if (xB < 1) {
      uf.union(0, xyTo1D(xA, yA)); // Connect to top virtual site
    } else if (xB > gridSize) {
      uf.union(area-1, xyTo1D(xA, yA)); // Connect to bottom virtual site
    } else if (yB > 0 && yB < gridSize+1) {
      if (isOpen(xB, yB)) {
        uf.union(xyTo1D(xA, yA), xyTo1D(xB, yB));
      }
    }
  }

  private int xyTo1D (int x, int y) {
    return (gridSize * (x - 1) + (y - 1)) + 1;
  }

  public static void main(String[] args) {
    int N = StdIn.readInt();
    Percolation perc = new Percolation(N);
    while (!StdIn.isEmpty()) {
      int x = StdIn.readInt();
      int y = StdIn.readInt();
      perc.open(x, y);
    }
  }

}
