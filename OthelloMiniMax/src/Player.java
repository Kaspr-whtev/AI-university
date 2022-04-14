import java.util.ArrayList;

public abstract class Player {
    public String name = "p";
    public char symbol = 'p';

    public Player(String name){
        this.name = name;
    }

    public Player(String name, char symbol){
        this.name = name;
        this.symbol= symbol;
    }

    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

    public abstract int[] move(Othello game);

    public static boolean findArray(ArrayList<int[]> list, int[] arr){
        for (int[] a : list){
            if (a[0] == arr[0] && a[1] == arr[1])
                return true;
        }
        return false;
    }
}
