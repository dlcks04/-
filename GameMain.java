package OOP_project;

public class GameMain {
    public static void main(String[] args) {

        PlayerInterface player = Companion.getInstanceP();
        DealerInterface dealer = Companion.getInstanceD();

        player.connect(dealer); dealer.connect(player);
        dealer.showRule();

        while (true) {
            if (!player.startGame()) break;
            player.showDeck();

            while (dealer.getScore() < 17) dealer.getCard();
            player.initialBet();

            player.betting();

            while (player.wantToGetCard()) {
                player.getCard();
                player.showDeck();
                if (player.isBurst()) break;
                player.checkSpecialChance();
            }

            if (player.isBurst()) dealer.win();
            else if (dealer.isBurst()) player.win();
            else {
                player.showDeck();
                dealer.narration();
                for (int i = 0; i < dealer.getCardNum()-1; i++) {
                    dealer.openCard(i);
                    player.extraBetting();
                }
                if (dealer.getScore() < player.getScore()) player.win();
                else if (dealer.getScore() == player.getScore()) player.draw();
                else dealer.win();
            }

            if (player.isRich()) break;

            player.showDeck(); dealer.showDeck();
            dealer.showUsedCard();
            player.reset(); dealer.reset();
            if (!player.continueGame()) break;

        }

    }
}


