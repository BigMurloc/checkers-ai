package pl.bigmurloc.checkersai.checkers.board;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardPrinter {

    public void printBoard(List<FieldDto> fields) {
        System.out.println("*** GAME BOARD ***");
    }

}
