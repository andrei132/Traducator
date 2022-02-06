package InputWord;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Word{

    private  String word;
    private  String word_en;
    private  String type;
    private  ArrayList<String> singular;
    private  ArrayList<String> plural;
    private  ArrayList<Definition> definitions;

    public Word(){

        this.singular = new ArrayList<>();
        this.plural = new ArrayList<>();
        this.definitions = new ArrayList<>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord_en() {
        return word_en;
    }

    public void setWord_en(String word_en) {
        this.word_en = word_en;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getSingular() {
        return singular;
    }

    public void setSingular(ArrayList<String> singular) {
        this.singular = singular;
    }

    public ArrayList<String> getPlural() {
        return plural;
    }

    public void setPlural(ArrayList<String> plural) {
        this.plural = plural;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    /**
     * Objects will be considered equal if they have the same word
     * @param o Object to be verify
     * @return True if word have the same name, else false
     **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return this.word.equals(word1.word);
    }

    /**
     * Check if 2 words are the same, if not make add where is possible and update the object
     * @param o Reference obj
     * @return true if obj was identical, false if not
    **/
    public boolean updateWordAppend(@NotNull Word o) {

        boolean wasEquals = true;

        if (!this.getWord().equals(o.getWord())) return false;
        if (!this.getWord_en().equals(o.getWord_en())) return false;
        if (!this.getType().equals(o.getType())){

            wasEquals = false;
            this.type = o.getType();

        }

        if(!this.getSingular().equals(o.getSingular())){

            wasEquals = false;

            //Merge without duplicates
            this.singular.removeAll(o.getSingular());
            this.singular.addAll(o.getSingular());

        }

        if(!this.plural.equals(o.getPlural())){
            wasEquals = false;

            //Merge without duplicates
            this.plural.removeAll(o.getPlural());
            this.plural.addAll(o.getPlural());

        }

        //Verify definition
        for (Definition definition : o.definitions) {
            boolean wasUpdated = false;

            for (Definition definition1 : this.definitions) {

                if (definition.getDict().equals(definition1.getDict()) &&
                    definition.getDictType().equals(definition1.getDictType())) {

                    wasUpdated = true;
                    boolean x = definition1.updateDefinitionAppend(definition);
                    wasEquals = wasEquals && x;

                }

            }

            if(!wasUpdated) {
                wasEquals = false;
                this.definitions.add(definition);
            }
        }

        return wasEquals;

    }

    /**
     * Get all synonymes for this Word
     * @return List with all synonymes
     **/
    public ArrayList<String> getAllSynonymes(){
        ArrayList<String> syn = new ArrayList<>();

        if(this.definitions == null) return syn;
        for (Definition definition : this.definitions) {

            if(definition.getDictType().equals("synonyms")) syn.addAll(definition.getText());

        }

        return syn;
    }

}
