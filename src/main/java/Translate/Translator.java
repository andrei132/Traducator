package Translate;

import InputWord.Word;
import Enum.SingularOrPluralForm;
import Exception.LanguageNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class Translator {

    private Dictionaries dictionaries;

    public Dictionaries getDictionaries() {
        return dictionaries;
    }

    /**
     * Class to return 2 data
     **/
    private static final class Res{

        Word word;
        SingularOrPluralForm sigOrPlu;

        public Res(Word word, SingularOrPluralForm sigOrPlu) {
            this.word = word;
            this.sigOrPlu = sigOrPlu;
        }

    }

    public Translator() {
        this.dictionaries = new Dictionaries();
    }

    public Translator(Dictionaries dictionaries) {
        this.dictionaries = dictionaries;
    }

    public void setDictionaries(Dictionaries dictionaries) {
        this.dictionaries = dictionaries;
    }

    /**
     * Intern Function: Find translate for word from one language to another
     * @param word Word that need to be translated
     * @param fromLanguage Language of word
     * @param toLanguage language in which to be translated
     * @return Return a Class that have 2 var, Word and if word was SINGULAR or PLURAL
     **/
    private @NotNull Res translateWordIntern(@NotNull String word, String fromLanguage, String toLanguage)
            throws LanguageNotFoundException {

        word = word.toLowerCase(Locale.ROOT);
        ArrayList<Word> myWordArrayList = this.dictionaries.getDictionaries().get(fromLanguage);
        if(myWordArrayList == null)
            throw new LanguageNotFoundException("Language with ID " + fromLanguage + " doesn't exist");

        Word word1 = new Word();
        word1.setWord(word);

        int index = -1;
        int i = 0;
        SingularOrPluralForm singOrPlu = SingularOrPluralForm.NOT_SPECIFIED;

        for (Word myWord : myWordArrayList) {

            if(myWord.getWord().equals(word)){
                index = i;
                singOrPlu = SingularOrPluralForm.SINGULAR_FORM;
                break;
            }

            for (String s : myWord.getSingular()) {
                if(s.equals(word)){
                    index = i;
                    singOrPlu = SingularOrPluralForm.SINGULAR_FORM;
                    break;
                }
            }

            for (String s : myWord.getPlural()) {
                if(s.equals(word)){
                    index = i;
                    singOrPlu = SingularOrPluralForm.PLURAL_FORM;
                    break;
                }
            }

            i++;
        }

        if(index == -1) return new Res(word1, singOrPlu);

        //If word need to be translated in english
        String wordEn = myWordArrayList.get(index).getWord_en();
        if(toLanguage.equals("en")) {
            Word _word = new Word();
            _word.setWord(wordEn);
            return new Res(_word, SingularOrPluralForm.NOT_SPECIFIED);
        }

        myWordArrayList = this.dictionaries.getDictionaries().get(toLanguage);
        if(myWordArrayList == null)
            throw new LanguageNotFoundException("Language with ID " + toLanguage + " doesn't exist");

        for (Word myWord : myWordArrayList) {
            if(myWord.getWord_en().equals(wordEn))
                return new Res(myWord, singOrPlu);
        }

        return new Res(word1, SingularOrPluralForm.NOT_SPECIFIED);

    }

    /**
     * Translate a word from one language to another
     * @param word Word that need to be translated
     * @param fromLanguage Language of word
     * @param toLanguage language in which to be translated
     * @return String with the best associated word in toLanguage
     **/
    public String translateWord(@NotNull String word, String fromLanguage, String toLanguage)
            throws LanguageNotFoundException {

        word = word.toLowerCase(Locale.ROOT);
        Res res = this.translateWordIntern(word,fromLanguage,toLanguage);

        switch (res.sigOrPlu){
            case SINGULAR_FORM -> { return res.word.getSingular().size() == 0?
                                            res.word.getWord():res.word.getSingular().get(0);}

            case PLURAL_FORM -> { return res.word.getPlural().size() == 0?
                                            res.word.getWord():res.word.getPlural().get(0);}

            default -> { return  res.word.getWord(); }
        }

    }

    /**
     * Translate a sentence
     * @param sentence Sentence that need to be translated
     * @param fromLanguage Language of sentence
     * @param toLanguage Language in witch to be translated
     * @return Translated sentence
     **/
    public String translateSentence(String sentence, String fromLanguage, String toLanguage)
            throws LanguageNotFoundException {

        if(sentence == null) return null;
        sentence = sentence.toLowerCase(Locale.ROOT);
        StringBuilder returnSentence = new StringBuilder();
        String[] splitSentence = sentence.split("[, ?!.]+");

        for (String s : splitSentence) {
            returnSentence.append(this.translateWord(s, fromLanguage, toLanguage)).append(" ");
        }

        return returnSentence.toString();
    }

    /**
     * Translate a sentence and return if possible 3 variants
     * @param sentence Sentence that need to be translated
     * @param fromLanguage Language of sentence
     * @param toLanguage Language in witch to be translated
     * @return List of 3 variant of translated sentence
     **/
    public ArrayList<String> translateSentences(@NotNull String sentence, String fromLanguage, String toLanguage)
            throws LanguageNotFoundException {

        sentence = sentence.toLowerCase(Locale.ROOT);
        ArrayList<String> stringArrayList = new ArrayList<>(3);
        StringBuilder returnSentence;
        String[] splitSentence = sentence.split("[, ?!.]+");
        int[] position = new int[splitSentence.length];

        for(int i = 0; i < 3; i++){

            returnSentence = new StringBuilder();
            for (int j = 0; j < splitSentence.length; j++) {

                String s = splitSentence[j];
                Res res = this.translateWordIntern(s,fromLanguage,toLanguage);

                if(res.word.getWord_en() == null) {
                    returnSentence.append(res.word.getWord()).append(" ");
                    continue;
                }

                switch (res.sigOrPlu){
                    case SINGULAR_FORM -> {
                        if(position[j] == res.word.getAllSynonymes().size())
                            returnSentence.append(res.word.getWord()).append(" ");
                        else if(position[j] < res.word.getAllSynonymes().size()){

                            returnSentence.append(res.word.getAllSynonymes().get(position[j])).append(" ");
                            position[j]++;

                        }
                    }

                    case PLURAL_FORM -> {
                        if(position[j] < res.word.getPlural().size()) {

                            returnSentence.append(res.word.getPlural().get(position[j])).append(" ");
                            position[j]++;

                        } else returnSentence.append(res.word.getPlural().get(position[j] - 1)).append(" ");
                    }
                }
            }

            if(!stringArrayList.contains(returnSentence.toString())) stringArrayList.add(returnSentence.toString());

        }

        return stringArrayList;
    }

}
