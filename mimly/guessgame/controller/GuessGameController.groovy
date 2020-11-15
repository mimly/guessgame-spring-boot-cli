package mimly.guessgame.controller;

import mimly.guessgame.model.Guess;
import mimly.guessgame.model.GuessGame;

import javax.servlet.http.HttpSession;

import groovy.util.logging.Slf4j;

@Grab("spring-boot-starter-thymeleaf")

@Controller
@RequestMapping("/")
@Slf4j(category = "** Guess Game **")
public class GuessGameController {

    private final GuessGame guessGame;

    @Autowired
    public GuessGameController(GuessGame guessGame) {
        this.guessGame = guessGame;
    }

    @GetMapping("/invalidate")
    public String doInvalidateGet(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/gameover")
    public String doGameOverGet() {
        if (!guessGame.isOver()) {
            return "redirect:/";
        }
        return "gameover";
    }

    @GetMapping
    public String doGet(Model model) {
        if (guessGame.isOver()) {
            return "redirect:/gameover";
        }

        model.addAttribute("guessGame", guessGame);
        return "index";
    }

    @PostMapping
    public String doPost(Guess guess) {
        log.info("Received: " + guess.asString());

        guessGame.updateErrorMessage(guess);
        if (guessGame.isValid(guess)) {
            guessGame.setLimits(guess);
            if (guessGame.isCorrect(guess)) {
                guessGame.setOver(true);
                return "redirect:/gameover";
            }
        }

        return "redirect:/";
    }
}
