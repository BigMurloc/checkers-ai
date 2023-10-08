package pl.bigmurloc.checkersai.checkers.board;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardPrinter {

    public void printBoard(List<FieldDto> fields) {
        System.out.println("               *** GAME BOARD ***");
        printTopBorder();
        printBoardInternally(fields);
        printBottomBorder();

    }

    void printTopBorder() {
        System.out.println("__________________________________________________");
    }

    private void printBoardInternally(List<FieldDto> fields) {
        for (int i = 0; i < 10; i++) {
            int reversedCounter = 10 - i;
            if (i == 0) {
                System.out.print(" " + 10 + " |");
            } else {
                System.out.print("  " + reversedCounter + " |");
            }
            List<FieldDto> toPrintForRow = fields.subList(reversedCounter * 10 - 10, reversedCounter * 10);
            for (FieldDto field : toPrintForRow) {
                if (field.isOccupied()) {
                    String color = field.isOccupied(CheckerColor.BLACK) ? "B" : "W";
                    System.out.print(" " + color + " |");
                } else {
                    System.out.print("   |");
                }
            }
            System.out.println();
        }
    }

    void printBottomBorder() {
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        System.out.println("      A   B   C   D   E   F   G   H   I   J");
    }

}
