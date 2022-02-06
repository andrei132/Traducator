import InputWord.Definition;
import InputWord.Word;
import Translate.Dictionaries;
import Translate.Translator;
import Exception.FileProblemException;
import Exception.LanguageNotFoundException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Scanner;

public class AppTest {
    @Contract(pure = true)
    public static @NotNull String help(){

        return """
                Hello :)
                You can test this project with default tests or you can test some utilities manually
                Just let me know :)
                0  -> This message
                1  -> Manual test
                2  -> Test addWord with my test
                3  -> Test removeWord with my test
                4  -> Test addDefinitionForWord with my test
                5  -> Test removeDefinition with my test
                6  -> Test translateWord with my test
                7  -> Test translateSentence with my test
                8  -> Test translateSentences with my test
                9  -> Test getDefinitionsForWord with my test
                10 -> Test exportDictionary with my test
                11 -> Exit loop
                I hope it's ok :)
                """;
    }

    @Contract(pure = true)
    public static @NotNull String helpManual(){

        return """
                You don't trust my test? Ok your choice :)
                You can test this project with your test
                Just let me know :)
                0  -> This message
                1  -> Test translateWord with your test
                2  -> Test translateSentence with your test
                3  -> Test translateSentences with your test
                4  -> Test getDefinitionsForWord with your test
                5 -> Test exportDictionary with your test
                6 -> Exit loop
                I hope it's ok :)
                """;
    }

    private static void testAddWordExceptionCase(@NotNull Translator translator){

        System.out.println("Add a word with null word");
        Word word = new Word();
        System.out.println(translator.getDictionaries().addWord(word,"ro"));

    }

    private static void testAddWordNormalCase(@NotNull Translator translator){

        System.out.println("Existent word without changes");
        ArrayList<Word> wordArrayList = translator.getDictionaries().getDictionaries().get("fr");
        System.out.println(translator.getDictionaries().addWord(wordArrayList.get(0),"fr"));
        System.out.println();

        System.out.println("Existent word(given word is not complete) with changes(singular new)");
        wordArrayList = new ArrayList<>(translator.getDictionaries().getDictionaries().get("fr"));

        Word word = wordArrayList.get(0);

        Word word1 = new Word();
        word1.setWord(word.getWord());
        word1.setWord_en(word.getWord_en());
        word1.setType(word.getType());
        word1.setDefinitions(word.getDefinitions());
        word1.setSingular(word.getSingular());
        word1.getSingular().add("Singular Adaugat");

        System.out.println(translator.getDictionaries().addWord(word1,"fr"));

        for (String s : word.getSingular()) {
            System.out.print("[" +s+"] ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Existent word(given word is not complete) with changes(definition new)");
        wordArrayList = new ArrayList<>(translator.getDictionaries().getDictionaries().get("fr"));

        word = wordArrayList.get(0);
        word1 = new Word();
        word1.setWord(word.getWord());
        word1.setWord_en(word.getWord_en());
        word1.setType(word.getType());
        Definition definition = new Definition();
        definition.setDictType(word.getDefinitions().get(0).getDictType());
        definition.setDict(word.getDefinitions().get(0).getDict());
        ArrayList<String> text = new ArrayList<>();
        text.add("definition new");
        definition.setText(text);

        word1.getDefinitions().add(definition);

        System.out.println(translator.getDictionaries().addWord(word1,"fr"));
        System.out.println();
        try {
            ArrayList<Definition> definitionArrayList = translator.getDictionaries().getDefinitionsForWord(word.getWord(),"fr");
            for (Definition definition1 : definitionArrayList) {
                System.out.println("Name: " + definition1.getDict());
                System.out.println("Type: " + definition1.getDictType());
                System.out.println("Text:");
                for (String s : definition1.getText()) {
                    System.out.println(s);
                }

                System.out.println();
            }

        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
    }

    public static void testAddWordAuto(Translator translator){

        testAddWordNormalCase(translator);
        testAddWordExceptionCase(translator);

    }

    private static void testRemoveWordExceptionCaseAuto(@NotNull Translator translator){

        System.out.println("Remove from nonexistent language");
        System.out.println("Remove \"chat\" from \"fg\":");
        try {
            System.out.println(translator.getDictionaries().removeWord("chat","fg"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testRemoveWordNormalCaseAuto(@NotNull Translator translator)
            throws LanguageNotFoundException {

        for (Word word : translator.getDictionaries().getDictionaries().get("fr")) {

            System.out.print(word.getWord() + " ");

        }
        System.out.println();

        System.out.println("Remove first");
        System.out.println(translator.getDictionaries().removeWord(
                translator.getDictionaries().getDictionaries().get("fr").get(0).getWord(),"fr"));
        for (Word word : translator.getDictionaries().getDictionaries().get("fr")) {

            System.out.print(word.getWord() + " ");

        }
        System.out.println();

        System.out.println("Remove nonexistent");
        System.out.println(translator.getDictionaries().removeWord("nonexistent","fr"));
        for (Word word : translator.getDictionaries().getDictionaries().get("fr")) {

            System.out.print(word.getWord() + " ");

        }
        System.out.println();
    }

    public static void testRemoveWordAuto(Translator translator) throws LanguageNotFoundException {

        testRemoveWordNormalCaseAuto(translator);
        testRemoveWordExceptionCaseAuto(translator);

    }

    private static void testAddDefinitionExceptionCaseAuto(@NotNull Translator translator){

        System.out.println("Add definition nonexistent language");
        System.out.println("Add definition to \"chat\" from language\"fg\"");
        Definition definition = new Definition();
        try {
            System.out.println(translator.getDictionaries().addDefinitionForWord("chat", "fg",definition));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testAddDefinitionNormalCaseAuto(@NotNull Translator translator)
            throws LanguageNotFoundException {

        Definition definition = new Definition();
        definition.setDict("Dict Added");
        definition.setDictType("noun");
        definition.setYear(1999);

        ArrayList<String> text = new ArrayList<>();
        text.add("Text1");
        text.add("Text2");
        definition.setText(text);

        System.out.println("Add definition to \"chat\" from language\"fr\"");
        System.out.println(translator.getDictionaries().addDefinitionForWord("chat","fr",definition));
        ArrayList<Definition> definitions = translator.getDictionaries().getDefinitionsForWord("chat","fr");

        for (Definition definition1 : definitions) {

            System.out.println("Name: " + definition1.getDict());
            System.out.println("Type: " + definition1.getDictType());
            System.out.println("Text:");
            for (String s : definition1.getText()) {
                System.out.println(s);
            }

            System.out.println();
        }

    }

    public static void testAddDefinitionAuto(Translator translator) throws LanguageNotFoundException {

        testAddDefinitionNormalCaseAuto(translator);
        testAddDefinitionExceptionCaseAuto(translator);

    }

    private static void testRemoveDefinitionExceptionCaseAuto(@NotNull Translator translator){

        System.out.println("Remove a null dictionary");
        try {
            System.out.println(translator.getDictionaries().removeDefinition("chat","fr",null));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testRemoveDefinitionNormalCaseAuto(@NotNull Translator translator)
            throws LanguageNotFoundException {

        String def = translator.getDictionaries().getDefinitionsForWord("chat","fr").get(0).getDict();
        System.out.println("Remove first definition from \"chat\"");
        System.out.println("Before:");
        ArrayList<Definition> defs = translator.getDictionaries().getDefinitionsForWord("chat", "fr");
        for (Definition definition1 : defs) {

            System.out.println("Name: " + definition1.getDict());
            System.out.println("Type: " + definition1.getDictType());
            System.out.println("Text:");
            for (String s : definition1.getText()) {
                System.out.println(s);
            }

            System.out.println();

        }

        System.out.println(translator.getDictionaries().removeDefinition("chat","fr",def));
        defs = translator.getDictionaries().getDefinitionsForWord("chat", "fr");

        for (Definition definition1 : defs) {

            System.out.println("Name: " + definition1.getDict());
            System.out.println("Type: " + definition1.getDictType());
            System.out.println("Text:");
            for (String s : definition1.getText()) {
                System.out.println(s);
            }

            System.out.println();

        }

    }

    public static void testRemoveDefinitionAuto(Translator translator) throws LanguageNotFoundException {

        testRemoveDefinitionNormalCaseAuto(translator);
        testRemoveDefinitionExceptionCaseAuto(translator);

    }

    private static void testTranslationWordExceptionCaseAuto(@NotNull Translator translator){

        System.out.println("Translate word that is not in dictionary");
        System.out.print("Translate word: \"dictionar\" from \"ro\" to \"fr\": ");
        try {
            System.out.println(translator.translateWord("dictionar","ro","fr"));
        } catch (LanguageNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Translate from language that doesn't exist");
        System.out.print("Translate word: \"dictionar\" from \"es\" to \"fr\": ");
        try {
            System.out.println(translator.translateWord("dictionar","es","fr"));
        } catch (LanguageNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Translate to language that doesn't exist");
        System.out.print("Translate word: \"chat\" from \"fr\" to \"es\": ");
        try {
            System.out.println(translator.translateWord("chat","fr","es"));
        } catch (LanguageNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Translate to language that doesn't exist, but word does not exist in the dictionary");
        System.out.print("Translate word: \"dictionar\" from \"fr\" to \"es\": ");
        try {
            System.out.println(translator.translateWord("dictionar","fr","es"));
        } catch (LanguageNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        System.out.println();

    }

    private static void testTranslateWordNormalCaseAuto(@NotNull Translator translator)
            throws LanguageNotFoundException {

        System.out.print("Translate Word: \"pisica\" from \"ro\" to \"en\" -> ");
        System.out.println(translator.translateWord("pisica","ro","en"));

        System.out.print("Translate Word: \"pisici\" from \"ro\" to \"fr\" -> ");
        System.out.println(translator.translateWord("pisici","ro","fr"));

        System.out.print("Translate Word: \"public\" from \"ro\" to \"fr\" -> ");
        System.out.println(translator.translateWord("public","ro","fr"));

    }

    public static void testTranslateWordAuto(Translator translator) throws LanguageNotFoundException {

        testTranslateWordNormalCaseAuto(translator);
        System.out.println();
        testTranslationWordExceptionCaseAuto(translator);

    }

    private static void testTranslateSentenceExceptionCaseAuto(@NotNull Translator translator){

        System.out.println("Language from doesn't exist:");
        System.out.println("Translate sentence: \"Nu conteaza!\" from \"fg\" to \"fr\": ");
        try {
            System.out.println(translator.translateSentence("Nu conteaza!","fg","fr"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Language to doesn't exist:");
        System.out.println("Translate sentence: \"Nu conteaza!\" from \"fr\" to \"fg\": ");
        try {
            System.out.println(translator.translateSentence("Nu conteaza!","fr","fg"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Sentence empty:");
        System.out.println("Translate sentence: \" from \"fr\" to \"ro\": ");
        try {
            System.out.println(translator.translateSentence("","fr","ro"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Sentence null:");
        System.out.println("Translate sentence: \" from \"fr\" to \"ro\": ");
        try {
            System.out.println(translator.translateSentence(null,"fr","ro"));
        } catch (LanguageNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

    }

    private static void testTranslateSentenceNormalCaseAuto(@NotNull Translator translator)
            throws LanguageNotFoundException {

        System.out.println("Translate sentence: \"Eu public picturi cu câini si pisici!\" from \"ro\" to \"fr\": ");
        System.out.println(translator.translateSentence("Eu public picturi cu câini si pisici!",
                "ro","fr"));
        System.out.println();

        System.out.println("Translate sentence: \"Eu merg.\" from \"ro\" to \"en\": ");
        System.out.println(translator.translateSentence("Eu merg.","ro", "en"));
        System.out.println();

        System.out.println("Translate sentence: \"Nu stiu nici un cuvant\" from \"ro\" to \"fr\": ");
        System.out.println(translator.translateSentence("Nu stiu nici un cuvant",
                "ro", "fr"));
        System.out.println();

    }

    public static void testTranslateSentenceAuto(Translator translator) throws LanguageNotFoundException {

        testTranslateSentenceNormalCaseAuto(translator);
        testTranslateSentenceExceptionCaseAuto(translator);

    }

    private static void testTranslateSentencesExceptionCaseAuto(@NotNull Translator translator){

        System.out.println("Language from doesn't exist:");
        System.out.println("Translate sentence: \"Nu conteaza!\" from \"fg\" to \"fr\": ");
        try {
            System.out.println(translator.translateSentences("Nu conteaza!","fg","fr"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Language to doesn't exist:");
        System.out.println("Translate sentence: \"Nu conteaza!\" from \"fr\" to \"fg\": ");
        try {
            System.out.println(translator.translateSentences("Nu conteaza!","fr","fg"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Sentence empty:");
        System.out.println("Translate sentence: \" from \"fr\" to \"ro\": ");
        try {
            System.out.println(translator.translateSentences("","fr","ro"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Sentence null:");
        System.out.println("Translate sentence: \" from \"fr\" to \"ro\": ");
        try {
            System.out.println(translator.translateSentences(null,"fr","ro"));
        } catch (LanguageNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

    }

    private static void testTranslateSentencesNormalCaseAuto(@NotNull Translator translator)
            throws LanguageNotFoundException {

        System.out.println("Translate sentence: \"Eu public picturi cu câini si pisica mea!\" from \"ro\" to \"fr\": ");
        System.out.println(translator.translateSentences("Eu public picturi cu câini si pisica mea!",
                "ro","fr"));
        System.out.println();

        System.out.println("Translate sentence: \"Eu merg.\" from \"ro\" to \"en\": ");
        System.out.println(translator.translateSentences("Eu merg.","ro", "en"));
        System.out.println();

        System.out.println("Translate sentence: \"Nu stiu nici un cuvant\" from \"ro\" to \"fr\": ");
        System.out.println(translator.translateSentences("Nu stiu nici un cuvant",
                "ro", "fr"));
        System.out.println();

    }

    public static void testTranslateSentencesAuto(Translator translator) throws LanguageNotFoundException {

        testTranslateSentencesNormalCaseAuto(translator);
        testTranslateSentencesExceptionCaseAuto(translator);

    }

    private static void testGetDefinitionExceptionCase(@NotNull Translator translator){

        System.out.println("Word not found in language");
        System.out.println("Definition for\"exist\"");
        try {
            System.out.println(translator.getDictionaries().getDefinitionsForWord("exist","ro"));
        } catch (LanguageNotFoundException e) {

            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Language not found");
        System.out.println("Definition for\"exist\"");
        try {
            System.out.println(translator.getDictionaries().getDefinitionsForWord("exist","fg"));
        } catch (LanguageNotFoundException e) {

            System.out.println(e.getMessage());
        }
        System.out.println();

    }

    private static void testGetDefinitionNormalCase(@NotNull Translator translator) throws LanguageNotFoundException {

        System.out.println("Definition for \"chat\"");
        ArrayList<Definition> definitions = translator.getDictionaries().getDefinitionsForWord("chat","fr");
        for (Definition definition : definitions) {

            System.out.println("Dictionary: " + definition.getDict());
            System.out.println("Dictionary type: " + definition.getDictType());
            System.out.println("Dictionary year: " + definition.getYear());
            System.out.println("Dictionary text: ");
            for (String s : definition.getText()) {
                System.out.println(s);
            }

            System.out.println();
        }

        System.out.println("Definition for \"câini\"");
        System.out.println(translator.getDictionaries().getDefinitionsForWord("câini","ro"));

    }

    public static void testGetDefinitionAuto(Translator translator) throws LanguageNotFoundException {

        testGetDefinitionNormalCase(translator);
        testGetDefinitionExceptionCase(translator);

    }

    public static void testExportDictionaryAuto(@NotNull Translator translator){

        System.out.println("Export \"fr\"");
        try {
            translator.getDictionaries().exportDictionary("fr");
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Export not exist language");
        System.out.println("Export \"fg\"");
        try {
            translator.getDictionaries().exportDictionary("fg");
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void testTranslateWordManual(@NotNull Translator translator){

        Scanner scanner = new Scanner(System.in);

        System.out.println("word fromLanguageID toLanguageID");
        String line ;
        line = scanner.nextLine();

        String[] word = line.split(" ");
        System.out.print("Translate word \"" + word[0] +
                " \" from \"" + word[1] + "\""+ " \" to \"" + word[2] + "\": ");

        try {
            System.out.println(translator.translateWord(word[0],word[1],word[2]));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void testTranslateSentenceManual(@NotNull Translator translator){

        Scanner scanner = new Scanner(System.in);

        System.out.println("sentence\\n fromLanguageID toLanguageID");
        String line = scanner.nextLine();
        String line2 = scanner.nextLine();

        String[] word = line2.split(" ");
        System.out.print("Translate sentence \"" + line +
                " \" from \"" + word[0] + "\""+ " \" to \"" + word[1] + "\": ");

        try {
            System.out.println(translator.translateSentence(line,word[0],word[1]));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void testTranslateSentencesManual(@NotNull Translator translator){

        Scanner scanner = new Scanner(System.in);

        System.out.println("sentence \\n fromLanguageID toLanguageID");
        String line = scanner.nextLine();
        String line2 = scanner.nextLine();
        String[] word = line2.split(" ");
        System.out.print("Translate sentences \"" + line +
                "\" from \"" + word[0] + "\""+ " \" to \"" + word[1] + "\": ");

        try {
            System.out.println(translator.translateSentences(line,word[0],word[1]));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void testGetDefinitionManual(@NotNull Translator translator){

        Scanner scanner = new Scanner(System.in);

        System.out.println("word language");
        String line = scanner.nextLine();

        String[] word = line.split(" ");
        System.out.println("Get definition for \"" + word[0] +
                " \" from language \"" + word[1] + "\": ");

        try {
            ArrayList<Definition> definitions = translator.getDictionaries().getDefinitionsForWord(word[0],word[1]);
            for (Definition definition : definitions) {

                System.out.println("Dictionary: " + definition.getDict());
                System.out.println("Dictionary type: " + definition.getDictType());
                System.out.println("Dictionary year: " + definition.getYear());
                System.out.println("Dictionary text: ");
                for (String s : definition.getText()) {
                    System.out.println(s);
                }

                System.out.println();
            }

        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void testExportDictionaryManual(@NotNull Translator translator){

        Scanner scanner = new Scanner(System.in);

        System.out.println("language");
        String line = scanner.nextLine();

        System.out.println("Try to export dictionary in \"./DictioOut/" + line +
                "_dict.json\"");

        try {
            translator.getDictionaries().exportDictionary(line);
            System.out.println("Success!");
        } catch (LanguageNotFoundException e) {
            System.out.println("Fail!\n" + e.getMessage());
        }

    }

    public static void testManual(Translator translator){

        Scanner scanner = new Scanner(System.in);
        int testNr = 0;

        while (true) {

            switch (testNr) {
                case 0  -> System.out.println(helpManual());
                case 1  -> testTranslateWordManual(translator);
                case 2  -> testTranslateSentenceManual(translator);
                case 3  -> testTranslateSentencesManual(translator);
                case 4  -> testGetDefinitionManual(translator);
                case 5  -> testExportDictionaryManual(translator);
                default -> { return;}
            }

            testNr = scanner.nextInt();
        }
    }

    public static void main(String[] args) throws LanguageNotFoundException, FileProblemException {

        Dictionaries dictionaries = new Dictionaries();
        Translator translator = new Translator();
        Scanner scanner = new Scanner(System.in);

        dictionaries.populateDict();
        translator.setDictionaries(dictionaries);

        int testNr = 0;

        while (testNr != 11){

            switch (testNr){
                case 0  -> System.out.println(help());
                case 1  -> testManual(translator);
                case 2  -> testAddWordAuto(translator);
                case 3  -> testRemoveWordAuto(translator);
                case 4  -> testAddDefinitionAuto(translator);
                case 5  -> testRemoveDefinitionAuto(translator);
                case 6  -> testTranslateWordAuto(translator);
                case 7  -> testTranslateSentenceAuto(translator);
                case 8  -> testTranslateSentencesAuto(translator);
                case 9  -> testGetDefinitionAuto(translator);
                case 10 -> testExportDictionaryAuto(translator);
                default -> System.out.println("Sorry, wrong command :~(");
            }
            testNr = scanner.nextInt();
        }

    }

}
