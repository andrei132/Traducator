package InputWord;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Definition implements Comparable<Definition>{

    private String dict;

    
    private String dictType;
    private int year;
    private ArrayList<String> text;

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }

    /**
     * Objects will be considered equal if they have the same dict name
     * @param o Object to be verify
     * @return True if word have the same name, else false
     **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition that = (Definition) o;
        return Objects.equals(dict, that.dict);
    }

    /**
     * Check if 2 definitions are the same, if not make add where is possible and update the object
     * @param o Reference obj
     * @return true if obj was identical, false if not
     **/
    public boolean updateDefinitionAppend(@NotNull Definition o){

        boolean wasEquals = true;

        if(!this.dict.equals(o.dict)) return false;
        if(!this.text.equals(o.text)){

            wasEquals = false;

            //Merge without duplicates
            this.text.removeAll(o.getText());
            this.text.addAll(o.getText());

        }

        return wasEquals;
    }

    @Override
    public int compareTo(@NotNull Definition o) {
        return this.year - o.year;
    }

}
