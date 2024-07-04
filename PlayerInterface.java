package OOP_project;

public interface PlayerInterface extends CommonInterface{
    boolean continueGame();
    boolean extraBetting();
    boolean startGame();
    int getMoney();
    boolean isRich();
    void betting();
    boolean wantToGetCard();
    void checkSpecialChance();
    void draw();
    void initialBet();
    void connect(DealerInterface d);

}
