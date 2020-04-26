package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;


public class FileSplit {

    private static String fileName = "";
    private static String ext = "";

    private static void writeFile(int start, int end, byte[] fileContent, int copy) {
        try {
            Path path = Paths.get(fileName + copy + ext);
            Path newFilePath = Files.createFile(path);
            StringBuilder s = new StringBuilder();
            for (int i = start; i < end && i < fileContent.length; i++) {
                // System.out.print((char) fileContent[i]);
                s.append((char) fileContent[i]);
            }
            Files.write(newFilePath, s.toString().getBytes());
            //System.out.println();
            byte[] hash = MessageDigest.getInstance("MD5").digest(s.toString().getBytes());
            System.out.println(" file " + copy + " hash " + new String(hash));
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public boolean fileUpload(String fileName) {
        fileName = Paths.get(fileName).getFileName().toString().split("[.]")[0];
        ext = Paths.get(fileName).getFileName().toString().split("[.]")[1];
        try {
            File file = new File(fileName);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            //System.out.println(fileContent.length);
            int start = 0;
            int end = fileContent.length / 4;
            int sizeM = end;
            System.out.println("end " + end);
            for (
                    int i = 1;
                    i <= 4; i++) {
                System.out.println(" iteration " + i + " size " + end);
                writeFile(start, end, fileContent, i);
                start = end;
                end += sizeM;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean fileExitis(String filePath) {
        return new File(filePath).exists();
    }

}
