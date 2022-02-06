package Exception;

import java.io.IOException;

public class LanguageNotFoundException extends IOException {

    public LanguageNotFoundException(String message){
        super(message);
    }

}
