package mimly.guessgame.model;

import org.apache.commons.lang3.StringUtils;

import org.springframework.web.context.annotation.SessionScope;

@Grab("commons-lang3")

@Component
@SessionScope
public class GuessGame {

    int min;
    int max;
    final int correctGuess;
    String error;
    boolean over;

    public GuessGame(@Value("0") int min, @Value("100") int max, @Value("89") int correctGuess) {
        this.min = min;
        this.max = max;
        this.correctGuess = correctGuess;
        this.over = false;
    }

    public void updateErrorMessage(Guess guess) {
        this.error = !StringUtils.isNumeric(guess.asString()) ? "Invalid guess: Not a positive integer!"
                : guess.asInt() <= this.min ? "Invalid guess: Lower or equal to " + this.min + "!"
                : guess.asInt() >= this.max ? "Invalid guess: Greater or equal to " + this.max + "!"
                : "";
    }

    public boolean isValid(Guess guess) {
        return StringUtils.isNumeric(guess.asString()) && guess.asInt() > this.min && guess.asInt() < this.max;
    }

    public boolean isCorrect(Guess guess) {
        return this.correctGuess == guess.asInt();
    }

    public void setLimits(Guess guess) {
        this.min = guess.asInt() < this.correctGuess ? guess.asInt() : this.min;
        this.max = guess.asInt() > this.correctGuess ? guess.asInt() : this.max;
    }
}

