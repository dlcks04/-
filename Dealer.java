package OOP_project;
import java.util.ArrayList;
import java.util.Collections;

public class Dealer implements DealerInterface {

    ArrayList<Integer> numbers;
    Card[] Deck = new Card[20];

    private int score;
    private int cardNum;
    PlayerInterface player;
    private int [][] cardInventory = new int[4][13];
    private int picked = 0;
    String[] shape = {"♤", "◇", "♡", "♧"};
    Dealer() {
        numbers = new ArrayList<>();
        for (int i = 0; i < 52; i++)
            numbers.add(i);
        Collections.shuffle(numbers);

        this.cardNum = 0;
        this.score = 0;

        for (int i = 0; i < 20; i++)
            Deck[i] = new Card(-1, -1);
    }
    public void connect(PlayerInterface player){
        this.player = player;
    }
    public void showRule()
    {
        System.out.println("---규칙---");
        System.out.println("본 게임은 블랙잭을 기반으로 짜여진 카드 게임으로, 카드의 숫자 총합이 21을 넘지 않으면서 딜러의 카드 숫자 총합보다 21에 가까우면 승립합니다." +
                "\n-게임이 시작하면, 플레이어는 카드를 두 장, 딜러는 카드의 숫자 총합이 17 이상이 될 때까지 카드를 받습니다." +
                "\n-카드를 받고 나면 원하는 금액을 배팅합니다. 이후 추가로 카드를 받을 수 있고, 카드의 숫자 총합이 21을 넘어가면 플레이어는 패배합니다." +
                "\n-카드의 숫자 총합이 21을 초과하지 않았다면, 딜러의 카드가 하나씩 공개됩니다. 플레이어는 추가 금액을 배팅할 수 있습니다" +
                "\n-카드 숫자의 총합이 딜러보다 21에 가까우면 승리합니다." +
                "\n-J, Q, K는 10으로, A는 1로 간주합니다." +
                "\n-만약 자신의 패가 특정 조건을 만족한다면 스페셜찬스가 발동됩니다. 스페셜찬스의 내용은 조건에 따라 상이합니다." +
                "\n--------\n");
    }

    public void narration(){
        System.out.println("딜러의 카드는 총 " + this.getCardNum() + "개 입니다.");
        System.out.println("추가 배팅을 시작합니다. 딜러의 카드가 차례로 공개되며, n만큼 걸면 floor(0.75 * n)만큼의 베팅금액이 적용됩니다.");
    }
    public void openCard(int i) {
        System.out.println(i+1 + "번 째 카드: " + this.cardShapeOf(i) + this.cardStrNumOf(i));
    }

    public void showUsedCard() {

        System.out.println("--- 사용된 카드 ---");
        for (int i = 0; i < player.getCardNum(); i++)
            cardInventory[player.cardPatternOf(i)][player.cardNumOf(i)] = 1;

        for (int i = 0; i < this.getCardNum(); i++)
            cardInventory[this.cardPatternOf(i)][this.cardNumOf(i)] = 1;

        System.out.println("  A 2 3 4 5 6 7 8 9 10 J Q K");
        for (int i = 0; i < 4; i++) {
            System.out.print(shape[i] + " ");
            for (int j = 0; j < 13; j++) {
                if (cardInventory[i][j] == 0) System.out.print(". ");
                else System.out.print(cardInventory[i][j] + " ");
                if (j == 9) System.out.print(" ");
            }

            System.out.print('\n');
        }

    }

    public int chooseCard(){
        if (picked++ == 52) {
            System.out.println("카드를 모두 사용하여, 이미 공개된 카드를 회수하여 Shuffle합니다.");
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 13; j++)
                    if (cardInventory[i][j] == 1) {
                        cardInventory[i][j] = 0;
                        numbers.add(13 * i + j); // 0 ~ 12 13~25 26~38 39~51
                        picked--;
                    }
            Collections.shuffle(numbers);
        }
        int n = numbers.getFirst(); numbers.removeFirst();
        return n;
    }

    public int getCardNum(){return this.cardNum;}


    public void showDeck() {
        System.out.println("--- 딜러의 덱 ---");
        for (int i = 0; i < cardNum; i++) {
            System.out.print(Deck[i].getShape() + Deck[i].getStrNum() + ' ');
        }
        System.out.println();
    }
    public int cardPatternOf(int i) {
        return Deck[i].getPattern();
    }
    public int cardNumOf(int i) {
        return Deck[i].getNum();
    }
    public String cardShapeOf(int i){
        return Deck[i].getShape();
    }
    public String cardStrNumOf(int i){
        return Deck[i].getStrNum();
    }

    public void win(){
        System.out.println("딜러가 승리하였습니다." + "딜러의 카드 총합은 " + this.score +"입니다.");
    }
    public void getCard(){
        int num = this.chooseCard();
        Card card = new Card(num/13, num%13);

        Deck[cardNum++] = card;
        if (num%13 >= 0 && num%13 <= 9)
            this.score += (num%13) + 1; // 수정 필요 0(A : 1) 1 2 3 4 5 6 7 8 9(10) 10(J) 11(Q) 12(K) // 2 9 8
        else if (num%13 >= 10)
            this.score += 10;
    }
    public int getScore(){return this.score;}
    public void reset(){
        this.cardNum = 0;
        this.score = 0;
    }

    public boolean isBurst(){
        return this.score > 21;
    }



}
