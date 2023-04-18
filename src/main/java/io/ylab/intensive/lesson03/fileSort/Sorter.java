package io.ylab.intensive.lesson03.fileSort;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sorter {
    private static final int MAX_SIZE_CHUNK = 1024 * 1024 * 10; // 10 MB
    private final File directoryForSavingTemp = new File(DirectoryName.DIRECTORY_NAME);

    public File sortFile(File dataFile) throws IOException {
        List<File> sortedChunks = getSortedChunks(dataFile);
        File result = mergeChunks(sortedChunks);
        for (File sortedChunk : sortedChunks) {
            sortedChunk.delete();
        }
        return result;
    }

    private List<File> getSortedChunks(File dataFile) throws IOException {
        List<File> chunks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            List<Long> values = new ArrayList<>();

            while (br.ready()) {
                values.add(Long.parseLong(br.readLine()));

                if (values.size() == MAX_SIZE_CHUNK) {
                    chunks.add(getSortedChunk(values));
                    values.clear();
                }
            }
            if (values.size() != 0) {
                chunks.add(getSortedChunk(values));
                values.clear();
            }
        }
        return chunks;
    }

    private File getSortedChunk(List<Long> values) throws IOException {
        Collections.sort(values);
        File chunk = File.createTempFile("chunk", null, directoryForSavingTemp);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chunk))) {
            for (Long value : values) {
                bw.write(value + "\n");
            }
        }
        return chunk;
    }

    private File mergeChunks(List<File> chunks) throws IOException {
        List<File> tempFiles = new ArrayList<>();
        for (File chunk : chunks) {
            if (tempFiles.size() == 0) {
                tempFiles.add(chunk);
                continue;
            }
            File newTemp = File.createTempFile("data", null, directoryForSavingTemp);
            File oldTemp = tempFiles.get(tempFiles.size() - 1);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(newTemp));
                 BufferedReader readerOldTemp = new BufferedReader(new FileReader(oldTemp));
                 BufferedReader readerChunk = new BufferedReader(new FileReader(chunk))) {

                Long valueFromTemp = null;
                Long valueFromChunk = null;
                while (readerOldTemp.ready() && readerChunk.ready()) {
                    if (valueFromTemp == null) {
                        valueFromTemp = Long.parseLong(readerOldTemp.readLine());
                        valueFromChunk = Long.parseLong(readerChunk.readLine());
                        continue;
                    }
                    if (valueFromTemp <= valueFromChunk) {
                        writer.write(valueFromTemp + "\n");
                        valueFromTemp = Long.parseLong(readerOldTemp.readLine());
                    } else {
                        writer.write(valueFromChunk + "\n");
                        valueFromChunk = Long.parseLong(readerChunk.readLine());
                    }
                }

                while (readerOldTemp.ready()) {
                    writer.write(readerOldTemp.readLine() + "\n");
                }
                while (readerChunk.ready()) {
                    writer.write(readerChunk.readLine() + "\n");
                }
            }
            tempFiles.add(newTemp);
        }

        String nameResultFile = DirectoryName.DIRECTORY_NAME + "/sortedData.txt";
        File result = new File(nameResultFile);
        if (result.exists()) {
            result.delete();
        }
        File lastTrueTemp = tempFiles.get(tempFiles.size() - 1);
        lastTrueTemp.renameTo(new File(nameResultFile));

        for (File tempFile : tempFiles) {
            tempFile.delete();
        }
        tempFiles.clear();
        return result;
    }


}