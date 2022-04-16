import java.util.ArrayList;

//abstract class for both types of players
//Othello.play takes 2 of these as input
public abstract class Player {
    public String name = "p";
    //symbol is assigned in Othello.play
    //it will be either the char representing white or black
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

    //every child needs to override this
    public abstract int[] move(Othello game);

    //ArrayList.contains wasn't working for me when the arraylist contained other collections (namely int[])
    //so I wrote a replacement specifically for this
    //it only checks the 0th and 1st index, because here I only create 2 element arrays
    public static boolean findArray(ArrayList<int[]> list, int[] arr){
        for (int[] a : list){
            if (a[0] == arr[0] && a[1] == arr[1])
                return true;
        }
        return false;
    }
}
