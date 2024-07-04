package OOP_project;

public class Companion
{
    public static PlayerInterface getInstanceP()
    {
        return new Player();
    }
    public static DealerInterface getInstanceD()
    {
        return new Dealer();
    }
}