package Exception;

import java.io.IOException;

public class FileProblemException extends IOException {

    public FileProblemException(String pathName){
        super("Error: " + pathName + " does not exist!");
    }

}
