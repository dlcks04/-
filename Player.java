package OOP_project;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Player implements PlayerInterface {
    DealerInterface dealer;
    Card[] Deck = new Card[20];
    Scanner scanner = new Scanner(System.in);
    private int money;
    private boolean is_draw;
    private int score;

    private int betAmount;
    private int cardNum;
    Player(){
        this.is_draw = false;
        this.cardNum = 0;
        this.score = 0;
        this.money = 5000;
        this.betAmount = 0;

        for (int i = 0; i < 20; i++)
            Deck[i] = new Card(-1, -1);

    }
    public void connect(DealerInterface dealer) {
        this.dealer = dealer;
    }

    public void reset(){
        this.cardNum = 0;
        this.score = 0;
        if (!is_draw) this.betAmount = 0;
        is_draw = false;
        specialChance = false;
    }
    public boolean startGame(){
        this.getCard();
        this.getCard();
        if (this.money < 100) {
            System.out.println("파산하셨습니다..."); return false;
        }
        return true;
    }

    public int getMoney(){return this.money;}

    public void win(){
        if (specialChance) {
            this.money += 4 * this.betAmount;
            System.out.println("당신의 승리! 예측에 성공하셨군요. 당신은 " + 4 * this.betAmount + "만큼 획득합니다.");
        }

        if (!specialChance) {
            this.money += 2 * this.betAmount;
            System.out.println("당신의 승리! 당신은 " + 2 * this.betAmount + "만큼 획득합니다.");
        }
    }
    public void getCard(){
        int num = dealer.chooseCard();
        Card card = new Card(num/13, num%13);

        Deck[cardNum++] = card;
        if (num%13 >= 0 && num%13 <= 9)
            this.score += (num%13) + 1;
        else if (num%13 >= 10)
            this.score += 10;
    }

    public int getScore(){return this.score;}
    public void betting(){
        int n;
        System.out.println("얼마나 배팅하시겠습니까?");
        while (true) {
            try {
                n = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("올바른 숫자를 입력하세요.");
                scanner.next();
            }
        }

        while (this.money < n || n < 0) {
            System.out.println("0이상의 올바른 배팅금액을 설정해주세요. " + this.money + " 만큼 보유 중");
            n = scanner.nextInt();
        }
        this.betAmount += n;
        this.money -= n;
    }

    public boolean extraBetting(){
        int n;
        System.out.println("얼마나 추가로 배팅하시겠습니까?");
        while (true) {
            try {
                n = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("올바른 숫자를 입력하세요.");
                scanner.next();
            }
        }

        while (this.money < n || n < 0) {
            System.out.println("0이상의 올바른 배팅금액을 설정해주세요. " + this.money + " 만큼 보유 중");
            n = scanner.nextInt();
        }
        this.betAmount += (int) (n * 3.0 / 4);
        this.money -= n;
        return true;
    }

    public boolean isBurst(){
        return this.score > 21;
    }
    public void draw(){
        System.out.println("무승부입니다. 배팅금액은 유지된 채 새 게임이 시작됩니다.");
        is_draw = true;
    }
    public void initialBet(){
        System.out.println("100을 배팅했습니다.");
        this.money -= 100;
        this.betAmount += 100;
    }

    public boolean wantToGetCard(){
        System.out.println("카드를 더 받으려면 O나 o를 입력하세요.");
        String x = scanner.next();
        return (x.equals("O") || x.equals("o"));
    }

    private boolean specialChance = false;
    public void checkSpecialChance(){
        int aCount = 0;
        for (int i = 0; i < cardNum; i++) {
            if (Deck[i].getStrNum().equals("A")) {
                aCount++;
            }
        }
        if (aCount >= 2) {
            System.out.println("special chance 발동! A가 2개 이상입니다. 강제로 카드를 가져갑니다.");
            this.getCard();
            this.showDeck();
        }


        if(cardNum >= 5)
        {
            System.out.println("special chance 발동! 카드 5장을 보유하여 딜러가 강제로 카드를 한 장 더 가져갑니다.");
            dealer.getCard();
        }

        int jCount = 0;
        int qCount = 0;
        int kCount = 0;
        if ((jCount + qCount + kCount) >= 2) {
            System.out.println("special chance 발동! J, Q, K 중 2장이 있습니다. 자신의 점수를 랜덤으로 1만큼 올리거나 내립니다.");
            Random rand = new Random();
            int change = rand.nextBoolean() ? 1 : -1;
            this.score += change;
            System.out.println("점수가 " + change + "만큼 변경되었습니다. 현재 점수: " + this.score);
        }


        int[] patternCount = new int[4];
        int prediction;
        for (int count : patternCount) {
            if (count >= 3) {
                System.out.println("special chance 발동! 동일 문양 3개 조건을 만족하였습니다. 딜러의 순수 카드 점수를 예측해 주세요. \n맞춘다면, 게임 승리 시 배팅 금액의 두 배를 추가로 받습니다.");
                prediction = scanner.nextInt();
                if (prediction == dealer.getScore()) {
                    specialChance = true;
                }
                break;
            }
        }
    }

    public int getCardNum(){return this.cardNum;}

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
    public boolean isRich() {
        if (this.money >= 25000) {
            System.out.println("엄청난 부자가 되셨군요! 당신의 승리입니다. 총 자산: " + this.getMoney());
            return true;
        }
        return false;

    }

    public void showDeck() {
        System.out.println("--- 당신의 덱 ---");
        for (int i = 0; i < cardNum; i++) {
            System.out.print(Deck[i].getShape() + Deck[i].getStrNum() + ' ');
        }
        System.out.println();
    }


    public boolean continueGame(){
        System.out.println("계속 진행... : Y or y, 끝내기... : X or x");
        String isContinue = scanner.next();
        while (!(isContinue.equals("X") || isContinue.equals("x") ||isContinue.equals("Y") ||isContinue.equals("y"))) {
            System.out.println("Y(y) or X(x) 중에 하나를 입력해주세요.");
            isContinue = scanner.next();
        }
        if((isContinue.equals("X") || isContinue.equals("x"))) return false;
        return true;
    }

}
