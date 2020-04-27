package utils;

import crytography.FileEncyDecy;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;


public class FileSplit {

    private static FileEncyDecy cryptoFile = new FileEncyDecy();
    private static String fileName = "";
    private static String ext = "";

    private static void writeFile(int start, int end, byte[] fileContent, int copy) {

        try {
            Path path = Paths.get(fileName + copy + ext);
            Path newFilePath = Files.createFile(path);
            StringBuilder fileChar = new StringBuilder();
            for (int i = start; i < end && i < fileContent.length; i++) {
                // System.out.print((char) fileContent[i]);
                fileChar.append((char) fileContent[i]);
            }

            switch (copy) {
                case 1:
                    byte[] md5 = MessageDigest.getInstance("MD5").digest(fileChar.toString().getBytes());
                    Files.write(newFilePath, cryptoFile.encrypt(fileChar.toString(), new String(md5)).getBytes());
                    break;
                case 2:
                    byte[] sha1 = MessageDigest.getInstance("SHA-1").digest(fileChar.toString().getBytes());
                    Files.write(newFilePath, cryptoFile.encrypt(fileChar.toString(), new String(sha1)).getBytes());
                    break;
                case 3:
                    byte[] sha256 = MessageDigest.getInstance("SHA-256").digest(fileChar.toString().getBytes());
                    Files.write(newFilePath, cryptoFile.encrypt(fileChar.toString(), new String(sha256)).getBytes());
                    break;
                case 4:
                    byte[] sha384 = MessageDigest.getInstance("SHA-384").digest(fileChar.toString().getBytes());
                    Files.write(newFilePath, cryptoFile.encrypt(fileChar.toString(), new String(sha384)).getBytes());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public boolean fileUpload(String fileName) {
        fileName = Paths.get(fileName).getFileName().toString().split("[.]")[0];
        ext = Paths.get(fileName).getFileName().toString().split("[.]")[1];
        try {
            File file = new File(fileName);
            byte[] fileEncyContent = Files.readAllBytes(file.toPath());
            //put hashkey here to get original content of file


            byte[] fileContent = cryptoFile.decrypt(new String(fileEncyContent), "").getBytes();




            int start = 0;
            int end = fileContent.length / 4;
            int sizeM = end;
            for (
                    int i = 1;
                    i <= 4; i++) {
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
