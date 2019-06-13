package MBeans;

public interface InputWatcherMBean
{
    void Input(String toInput);
    int getNumOfCorrectInputs();
    int getNumOfIncorrectInputs();
    int getNumOfExceptions();
}
