package MBeans;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main
{
    public static void main( String[] args ){
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try
        {
            ObjectName inputWatcherName = new ObjectName("MBeans:type=InputWatcher");
            ObjectName averageTimerName = new ObjectName("MBeans:type=AverageTimer");
            InputWatcher inputWatcher = new InputWatcher();
            AverageTimer averageTimer = new AverageTimer();
            mBeanServer.registerMBean(inputWatcher, inputWatcherName);
            mBeanServer.registerMBean(averageTimer, averageTimerName);
            inputWatcher.Input("{\"name\":\"A\", \"author\":\"B\", \"size\":1, \"location\":{\"cupboard\":1, \"shelf\":1}}\n");
            inputWatcher.Input("{\"name\":\"D\", \"asfthor\":\"AD\", \"size\":100, \"location\":{\"cupboard\":1, \"shelf\":231}}\n");
            Thread.sleep(Long.MAX_VALUE);
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }

    }
}