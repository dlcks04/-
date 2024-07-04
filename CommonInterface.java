package OOP_project;

public interface CommonInterface {
    int getCardNum();

    void showDeck();
    int cardPatternOf(int i);
    int cardNumOf(int i);
    String cardShapeOf(int i);

    String cardStrNumOf(int i);
    void win();
    int getScore();
    void getCard();
    void reset();
    boolean isBurst();
}
