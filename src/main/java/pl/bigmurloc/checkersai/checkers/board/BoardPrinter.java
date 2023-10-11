package pl.bigmurloc.checkersai.checkers.board;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardPrinter {

    public void print(Board board) {
        System.out.println("               *** GAME BOARD ***");
        printTopBorder();
        printBoardInternally(board.getFields());
        printBottomBorder();

    }

    void printTopBorder() {
        System.out.println("__________________________________________________");
    }

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

    void printBottomBorder() {
        System.out.println("");
        System.out.println("      1   2   3   4   5   6   7   8   9   10");
    }

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
