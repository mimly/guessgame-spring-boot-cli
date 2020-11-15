package mimly.guessgame.model;

public class Guess {

    String param;

    public String asString() {
        return param;
    }

    public int asInt() {
        return Integer.parseInt(param);
    }
}
