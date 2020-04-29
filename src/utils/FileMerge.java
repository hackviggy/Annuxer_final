package utils;

import crytography.FileEncyDecy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileMerge {
    private String fileName, ext;
    private static FileEncyDecy cryptoFile = new FileEncyDecy();

    public File fileMerge(String fName) {
        fileName = Paths.get(fName).getFileName().toString().split("[.]")[0];
        ext = Paths.get(fName).getFileName().toString().split("[.]")[1];
        File ofile = new File(Paths.get(fName).getFileName().toUri());
        FileOutputStream fos;
        FileInputStream fis;
        byte[] fileBytes;
        int bytesRead;
        List<File> list = new ArrayList<>();
        list.add(new File(fileName + 1 + ext));
        list.add(new File(fileName + 2 + ext));
        list.add(new File(fileName + 3 + ext));
        list.add(new File(fileName + 4 + ext));
        try {
            fos = new FileOutputStream(ofile, true);
            for (File file : list) {
                fis = new FileInputStream(file);
                fileBytes = new byte[(int) file.length()];
                bytesRead = fis.read(fileBytes, 0, (int) file.length());
                assert (bytesRead == fileBytes.length);
                assert (bytesRead == (int) file.length());
                fos.write(cryptoFile.encrypt(new String(fileBytes), "").getBytes());
                //
                fos.flush();
                fileBytes = null;
                fis.close();
                fis = null;
            }
            fos.close();
            fos = null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ofile;
    }
}
