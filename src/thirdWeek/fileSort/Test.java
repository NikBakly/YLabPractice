package thirdWeek.fileSort;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate(DirectoryName.DIRECTORY_NAME + "/data.txt", 375_000_000);
        System.out.println(new Validator(dataFile).isSorted()); // false
        double startTime = System.currentTimeMillis();
        File sortedFile = new Sorter().sortFile(dataFile);
        double endTime = System.currentTimeMillis();
        System.out.println(new Validator(sortedFile).isSorted()); // true


        System.out.printf("Время выполнения: %s мс.\n", (endTime - startTime));
        System.out.printf("Время выполнения: %f сек.\n", (endTime - startTime) / 1000);
        System.out.printf("Время выполнения: %f мин.", (endTime - startTime) / 1000 / 60);
        // У меня получилось 29,583600 мин.
    }

}
