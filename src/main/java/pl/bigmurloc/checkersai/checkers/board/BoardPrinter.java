package pl.bigmurloc.checkersai.checkers.board;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class that prints the board to the console
 */
@Component
public class BoardPrinter {

    /**
     * Prints the board to the console
     * @param board board to print
     */
    public void print(Board board) {
        System.out.println("               *** GAME BOARD ***");
        printTopBorder();
        printBoardInternally(board.getFields());
        printBottomBorder();

    }

    /**
     * Prints the top border of the board
     */
    void printTopBorder() {
        System.out.println("__________________________________________________");
    }

    /**
     * Prints the content of the board and its current state based on provided fields
     * @param fields list of fields to print
     */
    private void printBoardInternally(List<FieldDto> fields) {
        for (int i = 0; i < 10; i++) {
            int reversedCounter = 10 - i;
            if (i == 0) {
                System.out.print("  " + getLetterForInt(10) + " |");
            } else {
                System.out.print("  " + getLetterForInt(reversedCounter) + " |");
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

    /**
     * Prints the bottom border of the board
     */
    void printBottomBorder() {
        System.out.println("");
        System.out.println("      1   2   3   4   5   6   7   8   9   10");
    }

    /**
     * Helper method that returns a letter for a given integer on the board
     */
    char getLetterForInt(int i) {
        return switch (i) {
            case 10 -> 'J';
            case 9 -> 'I';
            case 8 -> 'H';
            case 7 -> 'G';
            case 6 -> 'F';
            case 5 -> 'E';
            case 4 -> 'D';
            case 3 -> 'C';
            case 2 -> 'B';
            case 1 -> 'A';
            default -> throw new IllegalArgumentException("Invalid value: " + i);
        };
    }

}
