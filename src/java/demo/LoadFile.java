/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

/**
 *
 * @author thinh
 */
@ManagedBean
public class LoadFile implements Serializable {

    private Part uploadedFile;
    private String folder = "Users\\thinh\\Documents\\NetBeansProjects\\dictionary";

    public Part getFile() {
        return uploadedFile;
    }

    public void setFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void saveFile() {

        try (InputStream input = uploadedFile.getInputStream()) {
            String fileName = uploadedFile.getSubmittedFileName();
            File file = new File(folder, fileName);
            Files.copy(input, file.toPath());
            String filePath = "Users\\thinh\\Documents\\NetBeansProjects\\dictionary" + fileName;
            insertDiction(filePath);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertDiction(String filePath) throws IOException {
        FileReader in = new FileReader(filePath);

        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains(":")) {
                String[] words = line.split(":");
                Test2 test2 = new Test2(words[0].trim(), words[1].trim());
                test2.saveWordMean();
            } else {
                if (line.contains(",")) {
                    int index = line.indexOf(',');
                    String w = line.substring(0, index).trim();
                    String m = line.substring(index + 1).trim();
                    Test2 test = new Test2(w, m);
                    test.saveWordMean();
                }
            }
        }
        br.close();
    }

}
