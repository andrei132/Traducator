package JSON;

import Exception.FileProblemException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonWork {

    /**
     * Read from JSON to Object with Type
     * @param path Path where are JSON file
     * @param type Type of Object that are written in JSON
     * @return Object that was read from JSON
     **/
    public static Object readJson(String path, Type type) throws FileProblemException {

        // Initialize Json Reader
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        String textFromFile;

        //Get all text from file
        try {
            textFromFile = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileProblemException("Error: Path" + path + " doesn't exist");
        }

        return gson.fromJson(textFromFile,type);
    }

    /**
     * Write Object to file JSON
     * @param path Path where are folder where to write Object
     * @param fileName File name of file with JSON extension
     * @param objToWrite Object that need to be written in JSON file
     **/
    public static void writeJson(String path, String fileName, Object objToWrite) throws FileProblemException {

        //Writer for JSON file
        Writer writer;
        try {
            writer = new FileWriter(path + "/" + fileName);
        } catch (IOException e) {
            throw new FileProblemException("Error: Cannot create " + path + "/" + fileName);
        }

        // Initialize Json Writer
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();

        gson.toJson(objToWrite, writer);

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
