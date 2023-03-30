package firstWeek;

public class MultTable {
    public static int MAX_NUMBER = 9;
    public static int TABLE_LENGTH = 145;

    public static void main(String[] args) {
        printBorders();
        printColumns();
        printBorders();
    }

    private static void printColumns() {
        for (int i = 1; i <= MAX_NUMBER; i++) {
            for (int j = 1; j <= MAX_NUMBER; j++) {
                if (j == 1) {
                    System.out.printf("|\t%d x %d = %d\t|\t", j, i, i * j);
                } else {
                    System.out.printf("%d x %d = %d\t|\t", j, i, j * i);
                }
            }
            System.out.println();
        }
    }

    private static void printBorders() {
        for (int i = 0; i < TABLE_LENGTH; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}