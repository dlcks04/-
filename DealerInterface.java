package OOP_project;

public interface DealerInterface extends CommonInterface {
    void openCard(int i);
    void showUsedCard();
    void showRule();
    int chooseCard();
    void narration();
    void connect(PlayerInterface p);
}
