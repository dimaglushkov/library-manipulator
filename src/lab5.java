import toCollect.Book;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Scanner;

public class lab5
{
    public static void main(String[] args)
    {
        String fileName;
        try
        {
            fileName = args[0];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.print("Enter file name: ");
            Scanner in = new Scanner(System.in);
            fileName = in.nextLine();
        }

        File file = new File(fileName);
        if (!file.exists())
        {
            System.out.print("This file does not exist");
            return;
        }


        PriorityQueue<Book> queue = new PriorityQueue<>(20, Book::compareTo);

        Book.readCollectionFromFile(queue, fileName);

        System.out.print("\n");
        Book newBook = new Book();
        newBook.setName("f");
        newBook.setAuthor("kappa");
        newBook.setNumOfPages(666);
        queue.add(newBook);

        Book.writeCollectionToFile(queue, fileName);


    }
}
