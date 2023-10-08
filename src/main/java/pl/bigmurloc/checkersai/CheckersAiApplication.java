package pl.bigmurloc.checkersai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.bigmurloc.checkersai.checkers.game.Game;

@SpringBootApplication
public class CheckersAiApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(CheckersAiApplication.class, args);
		var game = context.getBean(Game.class);
		game.init();
	}

}
