package OOP_project;

public class Card
{
    private int number;
    private String Number;
    private int pattern;
    private String Pattern;
    Card(int pattern, int number)
    {
        this.number = number;
        this.pattern = pattern;

        this.Pattern = switch(pattern)
        {
            case 0:
                yield "♤";
            case 1:
                yield "◇";
            case 2:
                yield "♡";
            default: // case 3
                yield "♧";
        };
        this.Number = switch(number + 1)
        {
            case 1:
                yield "A";
            case 2:
                yield "2";
            case 3:
                yield "3";
            case 4:
                yield "4";
            case 5:
                yield "5";
            case 6:
                yield "6";
            case 7:
                yield "7";
            case 8:
                yield "8";
            case 9:
                yield "9";
            case 10:
                yield "10";
            case 11:
                yield "J";
            case 12:
                yield "Q";
            default:
                yield "K";
        };

    }
    public int getNum(){return this.number;}
    public int getPattern(){return this.pattern;}
    public String getShape(){return this.Pattern;}
    public String getStrNum(){return this.Number;}
}
