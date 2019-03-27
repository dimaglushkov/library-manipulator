package toCollect;

import java.util.Random;

public class BookLocation
{
    int cupboard;
    int shelf;

    public BookLocation()
    {
        Random random = new Random();
        this.cupboard = random.nextInt(100) + 1;
        this.shelf = random.nextInt(1000) + 1;
    }

    public BookLocation(int cupboard, int shelf)
    {
        this.cupboard = cupboard;
        this.shelf = shelf;
    }

}
