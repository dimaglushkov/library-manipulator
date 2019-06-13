package MBeans;

import Library.LibraryManipulator;

import javax.management.*;
import java.io.Serializable;
import java.util.PriorityQueue;

public class InputWatcher extends NotificationBroadcasterSupport implements InputWatcherMBean, Serializable
{
    private long seq = 0;
    private int numOfCorrectPoints = 0;
    private int numOfIncorrectPoints = 0;
    private int numOfExceptions = 0;
    private LibraryManipulator lm = new LibraryManipulator(new PriorityQueue<>(), "null");

    @Override
    public void Input(String toInput)
    {
        try
        {
            seq++;
            if (lm.add(toInput))
                numOfCorrectPoints++;
            else
                numOfIncorrectPoints++;
        }
        catch (Exception e)
        {
            numOfExceptions++;
            Notification notification = new Notification("Exception", this, seq, "Couldn't parse input string to JSON.");
            sendNotification(notification);
        }
    }

    @Override
    public int getNumOfCorrectInputs()
    {
        return numOfCorrectPoints;
    }

    @Override
    public int getNumOfIncorrectInputs()
    {
        return numOfIncorrectPoints;
    }

    @Override
    public int getNumOfExceptions()
    {
        return numOfExceptions;
    }

}
