package MBeans;

import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;

public class AverageTimer extends NotificationBroadcasterSupport implements AverageTimerMBean, Serializable
{
    private long startTime;
    private int numOfInserts;
    private int averageTime;

    AverageTimer()
    {
        startTime = System.currentTimeMillis();
        numOfInserts = 0;
        averageTime = 0;
    }

    @Override
    public int getAverageTime()
    {
        numOfInserts++;
        averageTime = (int) ((System.currentTimeMillis() - startTime) / numOfInserts);
        return averageTime;
    }
}
