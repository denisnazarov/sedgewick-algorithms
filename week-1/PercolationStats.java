public class PercolationStats {

  public static void main(String[] args)  {
    int N = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);

    for (int i = 1; i <= T; i++) {
      Percolation perc = new Percolation(N);
      int sitesOpen = 0;
      while(!perc.percolates()) {
        int x = random(N);
        int y = random(N);
        if (!perc.isOpen(x, y)) {
          sitesOpen++;
          perc.open(x, y);
        }
      }
      StdOut.println(sitesOpen);
    }

  }

  public static int random(int N) {
    return (int)Math.floor((StdRandom.uniform() * N) + 1);
  }

}
