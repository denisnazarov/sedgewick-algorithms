
public class Percolation {
  private WeightedQuickUnionUF uf;
  private int gridSize;
  private int area;
  private boolean[] isOpenArray;

  public Percolation(int N) {
    gridSize = N;
    area = gridSize * gridSize +2;
    isOpenArray = new boolean[area];
    for (int i = 0; i < area; i++) isOpenArray[i] = false;
    isOpenArray[0] = true;
    isOpenArray[area - 1] = true;
    uf = new WeightedQuickUnionUF(area);
  }

  public void open(int x, int y) {
    int arrayIndex = xyTo1D(x, y);
    isOpenArray[arrayIndex] = true;

    connectUF(x, y, x-1, y); // Above
    connectUF(x, y, x+1, y); // Below
    connectUF(x, y, x, y-1); // Left
    connectUF(x, y, x, y+1); // Right
  }

  private void connectUF(int xA, int yA, int xB, int yB) {
    if (xB < 1) {
      // Connect to top virtual site
      uf.union(0, xyTo1D(xA, yA));
    } else if (xB > gridSize) {
      // Connect to bottom virtual site
      uf.union(area-1, xyTo1D(xA, yA));
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
      StdOut.println(x + " " + y);
    }
    StdOut.println(perc.percolates() + " is true ?");
  }

  public boolean isOpen(int x, int y) {
    int arrayIndex = xyTo1D(x,y);
    return isOpenArray[arrayIndex];
  }

  public boolean isFull(int x, int y) {
    return uf.connected(0, xyTo1D(x, y));
  }

  public boolean percolates() {
    StdOut.println(uf.find(0) + " " + uf.find(area-1));
    return uf.connected(0, area-1);
  }

}
