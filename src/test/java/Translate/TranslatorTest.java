package Translate;

import Exception.LanguageNotFoundException;
import Exception.FileProblemException;

import org.junit.jupiter.api.Test;

class TranslatorTest {

    @Test
    void translateWord() throws FileProblemException, LanguageNotFoundException {
        Dictionaries dictionaries = new Dictionaries();
        dictionaries.populateDict();
        Translator translator = new Translator(dictionaries);

        System.out.println("---------------------------------------------");
        System.out.println("--------------Test translateWord-------------");
        System.out.println("---------------------------------------------");

        if(translator.translateWord("pisică","ro","fr").equals("chat"))
            System.out.println("----Translate Word pisică ro -> fr PASSED----");else
            System.out.println("----Translate Word pisică ro -> fr FAILED----");

        if(translator.translateWord("pisici","ro","fr").equals("chats"))
            System.out.println("----Translate Word pisici ro -> fr PASSED----");else
            System.out.println("----Translate Word pisici ro -> fr FAILED----");

        if(translator.translateWord("public","ro","fr").equals("publier"))
            System.out.println("----Translate Word public ro -> fr PASSED----");else
            System.out.println("----Translate Word public ro -> fr FAILED----");

        if(translator.translateWord("dictio","ro","fr").equals("dictio"))
            System.out.println("Translate NoExist Word dictio ro -> fr PASSED");else
            System.out.println("Translate NoExist Word dictio ro -> fr FAILED");

        try {
            translator.translateWord("dictio","es","fr");
            System.out.println("Translate NoExist Lang dictio es -> fr FAILED");
        } catch (LanguageNotFoundException e) {
            System.out.println("Translate NoExist Lang dictio es -> fr PASSED");
        }

        System.out.println("---------------------------------------------");

    }

    @Test
    void translateSentence() {

        System.out.println("---------------------------------------------");
        System.out.println("------------Test translateSentence-----------");
        System.out.println("---------------------------------------------");
        System.out.println("-----------Test this manual please-----------");
        System.out.println("--------------Use Class AppTest--------------");
        System.out.println("---------------------------------------------");

    }

    @Test
    void translateSentences() {

        System.out.println("---------------------------------------------");
        System.out.println("-----------Test translateSentences-----------");
        System.out.println("---------------------------------------------");
        System.out.println("-----------Test this manual please-----------");
        System.out.println("--------------Use Class AppTest--------------");
        System.out.println("---------------------------------------------");

    }
}