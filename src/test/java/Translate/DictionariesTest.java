package Translate;
import Exception.LanguageNotFoundException;
import Exception.FileProblemException;
import InputWord.Definition;
import InputWord.Word;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
class DictionariesTest {

    @Test
    void addWord() throws FileProblemException {
        Dictionaries dictionaries = new Dictionaries();
        dictionaries.populateDict();
        System.out.println("---------------------------------------------");
        System.out.println("----------------Test addWord-----------------");
        System.out.println("---------------------------------------------");
        ArrayList<Word> wordArrayList = dictionaries.getDictionaries().get("fr");

        if (!dictionaries.addWord(wordArrayList.get(0),"fr"))
            System.out.println("----------addWord Existent PASSED------------"); else
            System.out.println("----------addWord Existent FAILED------------");

        wordArrayList = new ArrayList<>(dictionaries.getDictionaries().get("fr"));
        Word word = wordArrayList.get(0);

        Word word1 = new Word();
        word1.setWord(word.getWord());
        word1.setWord_en(word.getWord_en());
        word1.setType(word.getType());
        word1.setDefinitions(word.getDefinitions());
        word1.setSingular(word.getSingular());
        word1.getSingular().add("Singular Adaugat");

        if(dictionaries.addWord(word1,"fr") &&
                dictionaries.getDictionaries().get("fr").get(0).getSingular().contains("Singular Adaugat"))
            System.out.println("-------addWord Existent Changed PASSED-------"); else
            System.out.println("-------addWord Existent Changed FAILED-------");

        Definition definition = new Definition();
        definition.setDictType(word.getDefinitions().get(0).getDictType());
        definition.setDict(word.getDefinitions().get(0).getDict());
        ArrayList<String> text = new ArrayList<>();
        text.add("definition new");
        definition.setText(text);

        word1.getDefinitions().add(definition);

        if(dictionaries.addWord(word1,"fr") &&
           dictionaries.getDictionaries().get("fr").get(0).getDefinitions().get(0).getText().contains("definition new"))
            System.out.println("-------addWord Existent Changed PASSED-------"); else
            System.out.println("-------addWord Existent Changed FAILED-------");

        word1 = new Word();
        if(!dictionaries.addWord(word1,"fr"))
            System.out.println("-------addWord NULLWord Changed PASSED-------"); else
            System.out.println("-------addWord NULLWord Changed FAILED-------");

        System.out.println("---------------------------------------------");
    }

    @Test
    void removeWord() throws LanguageNotFoundException, FileProblemException {

        Dictionaries dictionaries = new Dictionaries();
        dictionaries.populateDict();
        System.out.println("---------------------------------------------");
        System.out.println("---------------Test removeWord---------------");
        System.out.println("---------------------------------------------");
        Word word = dictionaries.getDictionaries().get("fr").get(0);

        if(dictionaries.removeWord(dictionaries.getDictionaries().get("fr").get(0).getWord(),"fr") &&
            !dictionaries.getDictionaries().get("fr").contains(word))
            System.out.println("-------------Remove Valid PASSED-------------"); else
            System.out.println("-------------Remove Valid FAILED-------------");

        if (!dictionaries.removeWord("nonexistent","fr"))
            System.out.println("---------Remove Non-Existent PASSED----------"); else
            System.out.println("---------Remove Non-Existent FAILED----------");

        try {
            System.out.println(dictionaries.removeWord("chat","fg"));
            System.out.println("--Remove from non-existent language FAILED--");
        } catch (LanguageNotFoundException e) {
            System.out.println("--Remove from non--existent language PASSED--");
        }

        System.out.println("---------------------------------------------");
    }

    @Test
    void addDefinitionForWord() throws FileProblemException, LanguageNotFoundException {

        Dictionaries dictionaries = new Dictionaries();
        dictionaries.populateDict();

        System.out.println("---------------------------------------------");
        System.out.println("-------------Test addDefinition--------------");
        System.out.println("---------------------------------------------");

        Definition definition = new Definition();
        definition.setDict("Dict Added");
        definition.setDictType("noun");
        definition.setYear(1999);

        ArrayList<String> text = new ArrayList<>();
        text.add("Text1");
        text.add("Text2");
        definition.setText(text);
        ArrayList<Definition> definitions = dictionaries.getDefinitionsForWord("chat","fr");

        if(dictionaries.addDefinitionForWord("chat","fr",definition) &&
           definitions.contains(definition))
            System.out.println("---------------Add Valid PASSED--------------"); else
            System.out.println("---------------Add Valid FAILED--------------");


        definition = new Definition();
        try {
            System.out.println(dictionaries.addDefinitionForWord("chat", "fg",definition));
            System.out.println("-----------Add NON-Valid-Lang FAILED----------");
        } catch (LanguageNotFoundException e) {
            System.out.println("----------Add NON-Valid-Lang PASSED----------");
        }
        System.out.println("---------------------------------------------");
    }

    @Test
     void removeDefinition() throws FileProblemException, LanguageNotFoundException {

        Dictionaries dictionaries = new Dictionaries();
        dictionaries.populateDict();
        ArrayList<Definition> def;
        def = (ArrayList<Definition>) dictionaries.getDefinitionsForWord("chat", "fr").clone();
        System.out.println("---------------------------------------------");
        System.out.println("------------Test removeDefinition------------");
        System.out.println("---------------------------------------------");

        if(dictionaries.removeDefinition("chat","fr",def.get(0).getDict()) &&
        !dictionaries.getDefinitionsForWord("chat","fr").contains(def.get(0)))
            System.out.println("--------------Remove Valid PASSED------------"); else
            System.out.println("--------------Remove Valid FAILED------------");

        if(!dictionaries.removeDefinition("chat","fr",null))
            System.out.println("----------Remove NULLDefinition PASSED-------"); else
            System.out.println("----------Remove NULLDefinition FAILED-------");

        System.out.println("---------------------------------------------");

    }

    @Test
    void getDefinitionsForWord() {

        System.out.println("---------------------------------------------");
        System.out.println("------------Test removeDefinition------------");
        System.out.println("---------------------------------------------");
        System.out.println("-----------Test this manual please-----------");
        System.out.println("--------------Use Class AppTest--------------");
        System.out.println("---------------------------------------------");

    }

    @Test
    void exportDictionary() {
        System.out.println("---------------------------------------------");
        System.out.println("------------Test ExportDictionary------------");
        System.out.println("---------------------------------------------");
        System.out.println("-----------Test this manual please-----------");
        System.out.println("--------------Use Class AppTest--------------");
        System.out.println("---------------------------------------------");

    }

}