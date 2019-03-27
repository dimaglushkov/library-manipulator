import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import toCollect.Book;

import java.io.File;
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

        Book book = new Book();
        book.setName("book 1");
        book.setAuthor("author 1");
        book.setLocation(1,1);
        book.setNumOfPages(228);
        book.setPrintDate("01-01-2001");
        book.write(fileName);
        book.write(fileName);

    }
}
