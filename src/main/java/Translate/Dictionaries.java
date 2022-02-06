package Translate;

import InputWord.Definition;
import InputWord.Word;
import Exception.FileProblemException;
import Exception.LanguageNotFoundException;
import JSON.JsonWork;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@SuppressWarnings("unchecked")
public class Dictionaries {

    private final HashMap<String, ArrayList<Word>> dictionaries;

    public Dictionaries(){
        dictionaries = new HashMap<>();
    }

    public HashMap<String, ArrayList<Word>> getDictionaries() {
        return dictionaries;
    }

    /**
     * Update a word with all new info
     * @param word Old word that must be updated
     * @param updateWord Word that contain all new info
     * @return true if something new was added, false if not
     **/
    private boolean changeWord(@NotNull Word word, @NotNull Word updateWord){
        return !word.updateWordAppend(updateWord);
    }

    /**
     * Add new word in Global Dictionary or update an existent word
     * @param word Word that will be added or updated
     * @param language Language of word
     * @return true if word was added or updated, false if not
     **/
    public boolean addWord(@NotNull Word word, String language){

        //Validate word
        if(word.getWord() == null || word.getWord_en() == null || word.getSingular() == null
            || word.getPlural() == null || word.getType() == null || word.getDefinitions() == null)
            return false;

        //Check for new language
        if(!this.dictionaries.containsKey(language)) {

            this.dictionaries.put(language,new ArrayList<>());
            this.dictionaries.get(language).add(word);

            return true;

        }

        ArrayList<Word> myWords = this.dictionaries.get(language);

        //Add or update word
        if (!myWords.contains(word)) myWords.add(word); else {
            return changeWord(myWords.get(myWords.indexOf(word)),word);
        }

        return true;

    }

    /**
     * Add a new definition in Global Dictionary or update an existent definition
     * @param word Word that will be added or updated
     * @param language Language of word
     * @param definition New Definition or old one with new info
     * @return true if add or update, false else
     **/
    public boolean addDefinitionForWord(String word, String language, Definition definition)
            throws LanguageNotFoundException {

        if(definition == null) return false;
        Word word1 = new Word();
        word1.setWord(word);

        ArrayList<Word> wordArrayList = this.dictionaries.get(language);
        if(wordArrayList == null)
            throw new LanguageNotFoundException("Language with ID " + language + " doesn't exist");

        int index = wordArrayList.indexOf(word1);
        if(index == -1) return false;
        Word word2 = wordArrayList.get(index);

        //Create a word with new definition
        word1.setWord_en(word2.getWord_en());
        word1.setDefinitions((ArrayList<Definition>) word2.getDefinitions().clone());
        word1.getDefinitions().add(definition);
        word1.setType(word2.getType());

        //Add an existent word with new word, will be updated
        return addWord(word1,language);
    }

    /**
     * Read all file in folder ./src/main/resources/Dictio, must be JSON file
     **/
    public void populateDict() throws FileProblemException {

        File folder = new File("./src/main/resources/Dictio");

        for (File file : Objects.requireNonNull(folder.listFiles())) {

            //Get all word from a dictionary
            ArrayList<Word> words = (ArrayList<Word>) JsonWork.readJson(file.getPath(),
                                                                        new TypeToken<ArrayList<Word>>() {}.getType());
            if(words == null) throw new FileProblemException(file.getPath());
            String languageID = file.getName().substring(0,file.getName().indexOf("_"));

            for (Word word : words) {

                addWord(word,languageID);

            }

        }

    }


    /**
     * Remove a word from Global Dictionary
     * @param word Word that need to be removed
     * @param language Language of word
     * @return true if word was deleted, false if not
     **/
    public boolean removeWord(String word, String language) throws LanguageNotFoundException {

        if(word == null) return false;

        Word word1 = new Word();
        word1.setWord(word);

        ArrayList<Word> myWordArrayList = this.dictionaries.get(language);
        if(myWordArrayList == null)
            throw new LanguageNotFoundException("Language with ID " + language + " doesn't exist");

        int index = myWordArrayList.indexOf(word1);
        if(index == -1) return false;

        myWordArrayList.remove(index);

        return true;
    }

    /**
     * Remove a definition from a word
     * @param word Word that have definition that need to be removed
     * @param language Languages of word
     * @param dictionary Dictionaries that need to be removed
     **/
    public boolean removeDefinition(String word, String language, String dictionary)
            throws LanguageNotFoundException {

        if(word == null || dictionary == null) return false;

        Word word1 = new Word();
        word1.setWord(word);

        ArrayList<Word> myWordArrayList = this.dictionaries.get(language);
        if(myWordArrayList == null)
            throw new LanguageNotFoundException("Language with ID " + language + " doesn't exist");

        int index = myWordArrayList.indexOf(word1);
        if (index == -1) return false;

        Word word2 = myWordArrayList.get(index);
        Definition definition = new Definition();
        definition.setDict(dictionary);

        boolean wasDelete = false;

        while (true){

            int indexD = word2.getDefinitions().indexOf(definition);
            if (indexD == -1) break;

            wasDelete = true;
            word2.getDefinitions().remove(indexD);

        }

        return wasDelete;
    }

    /**
     * Get all definition(sorted by year) for a word
     * @param word Word for which the definition will be extracted
     * @param language Language of word
     * @return List with all definition
     **/
    public ArrayList<Definition> getDefinitionsForWord(String word, String language)
            throws LanguageNotFoundException {

        ArrayList<Definition> definitionArrayList = new ArrayList<>();
        Word word1 = new Word();
        word1.setWord(word);

        ArrayList<Word> myWordArrayList = this.dictionaries.get(language);
        if(myWordArrayList == null)
            throw new LanguageNotFoundException("Language with ID " + language + " doesn't exist");

        int index = myWordArrayList.indexOf(word1);
        if(index == -1) return definitionArrayList;

        definitionArrayList = myWordArrayList.get(index).getDefinitions();
        definitionArrayList.sort(Comparator.naturalOrder());

        return definitionArrayList;
    }

    /**
     * Export a Dictionary(sorted alphabetic(if letters are the same, sorted by year)) in JSON format
     * @param language Language of dictionary
     **/
    public void exportDictionary(String language) throws LanguageNotFoundException {

        ArrayList<Word> myWordArrayList = this.dictionaries.get(language);
        if(myWordArrayList == null)
            throw new LanguageNotFoundException("Language with ID " + language + " doesn't exist");

        ArrayList<Word> wordArrayList = new ArrayList<>(myWordArrayList.size());

        for (Word word : myWordArrayList) {
            word.getDefinitions().sort(Comparator.naturalOrder());
            wordArrayList.add(word);
        }

        wordArrayList.sort(Comparator.comparing(Word::getWord));

        try {
            JsonWork.writeJson("./src/main/resources/DictioOut",language+"_dict.json",wordArrayList);
        } catch (FileProblemException e) {
            e.printStackTrace();
        }

    }

}
