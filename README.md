# Tema 2 POO 2021 Translator
## Girnet Andrei 321CB

## Descriere
Acest proiect este creat pentru a fi in stare sa traduca din limba X in limba Y, daca are dictionarele X si Y in format JSON

Ce poate face programul:

* Citesteste dintr-un JSON un dictionar
* Traduce un cuvant
* Traduce o propozitie in varianta unica
* Traduce o propozitie in 3 variante
* Exporta un dictionar in format JSON

---
## Functionalitate
Clasa Dictionaries, este clasa care tine toate dictionarele intr-un HashMap unde key e un String care reprezinta language ID, si value e un ArrayList de cuvinte in aceea limba. Pentru a declara si popula un dictionar:
```java
Class Program {

    /**
    * Initializarea unui dictionar si popularea lui cu limbi aflate in  
    * ./src/main/resources/Dicto
    **/
    Dictionaries dictionaries = new Dictionaries();
    dictionaries.populate();

}
```
Clasa principala Translator se ocupa de toate traducerile din toate dictionarele, odata initializat un Translator el necesita un dictionar. Pentru a utiliza Translator:
```java
Class Program {

    /**
    * Initializarea unui translator cu dictionarele aflate in 
    * ./src/main/resources/Dicto
    **/
    Dictionaries dictionaries = new Dictionaries();
    dictionaries.populate();
    Translator translator = new Translator(dictionaries);

}
```

### <b> Metode de prelucrare a unui dictionar:</b>
*  <b>addWord</b>
```java
     /**
     * Adauga un cuvant in dictionar, sau il actualizezea pe unul care 
     * are acelasi word si word_en
     * @param word Cuvantul care va fi adaugat sau actualizat(Word)
     * @param language Limba cuvantului(String)
     * @return true daca cuvantul a fost adaugat sau actualizat
     **/
    boolean wasAdded = dictionaries.addWord(word, language);

```
Implementare: Validez cuvantul(nu are nimic null), gasesc ArrayList-ul cu limba language, apoi gasesc cuvantul si daca deja a existat il trimit la actualizare, daca nu il adaug

* <b>public boolean addDefinitionForWord</b>
```java
    /**
     * Adauga o definitie noua la un cuvant sau actualizeaza una
     * @param word Cuvantul la care se va adauga sau actualiza(String)
     * @param language Limba cuvantului(String)
     * @param definition Definitia noua sau una vheche cu info nou(Definition)
     * @return true daca a fost actualizat, daca nu false
     **/
    boolean wasAdded = dictionaries.addDefinitionForWord(word, language, definition);

```
Implementarea: Validez ca definitia nu e null, creez un cuvant identic cu cel cautat si ii adaug definitia noua, si apoi cer sa se adauge cuvantul, asa cum e cuvant deja existent va fi actualizat

* <b>public boolean removeWord</b>
```java

    /**
     * Sterge un cuvant din dictionar
     * @param word Cuvantul ce trebuie sters(String)
     * @param language Limba cuvantului(String)
     * @return true daca a fost sters cuvantul, false daca nu
     **/
    boolean wasRemoved = dictionaries.removeWord(word, language);

```
Implementare: Verific word diferit de null, gasesc word si il sterg din lista

* <b>removeDefinition</b>
```java
    /**
     * Sterge toate dictionarele cu numele dictionary
     * @param word Cuvantul care contine dictionarele(String)
     * @param language Limba cuvantului(String)
     * @param dictionary Numele dictionarului(String)
     **/
    boolean wasremoved = dictionaries.removeDefinition(word, language, dictionary);

```
Implemenare: Validez cuvantul si dictionarul, caut dictionarele si le sterg

* <b>getDefinitionsForWord</b>
```java
    /**
     * Toate definitiile cuvantului sortate dupa an
     * @param word Cuvantul la care se cer definitiile(String)
     * @param language Limba cuvantului(String)
     * @return Lista cu toate definitiile sortate
     **/
    ArrayList<Definition> allDefinition = dictionaries.getDefinitionsForWord(word, language);

```
Implementare: Extrag toate definitiile si le sortez cu ajutorul arrayList.sort(Comparator.naturalOrder)

* <b>exportDictionary</b>
```java
    /**
     * Exporta un dictionar intr-un fisier JSON, sortate alfabetic
     * @param language Limba dictionarului(String)
     **/
    dictionaries.exportDictionary(language);

```
Implementare: Sortez comform conditiei cu arrayList.sort(Comparator), scriu cu ajutorul gson in fisier cu numele language_dict.json

### <b> Metode de traducere:</b>
* <b>translateWord</b>
```java
    /**
     * Traduce un cuvant dintr-o limba in alta
     * @param word Cuvantul care trebuie traduc(String)
     * @param fromLanguage Limba cuvantului(String)
     * @param toLanguage Limba in care trebuie tradus(String)
     * @return String cu cel mai bine considerat cuvant in limba toLanguage
     **/
    String translatedWord = translator.translateWord(word, fromLanguage, toLanguage)

```
Implementare: Folosesc o metoda interna care cauta cuvantul in limba fromLanguage, il cauta pe cel toLanguage, cu ajutorul variabilei membru word_en, si intoarce cuvantul in format obiect Word si forma singular sau plural, apoi doar ma uit ce as trebui sa intorc singular, plural

* <b>translateSentence</b>
```java
    /**
     * Traduce o propozitie in varianta unica
     * @param sentence Propozitia ce trebuie tradusa(String)
     * @param fromLanguage Limba din care trebuie tradus(String)
     * @param toLanguage Limba in care trebuie tradus(String)
     * @return Propozitia tradusa
     **/
    String sentanceTranslated = translateSentence(sentence, fromLanguage, toLanguage);

```
Implementare: Trec prin toate cuvintele si le traduc cu ajutorul translateWord

* <b>translateSentences</b>
```java
    /**
     * Traduce o propozitie in 3 variante, daca e posibil
     * @param sentence Propozitia ce trebuie tradusa(String)
     * @param fromLanguage Limba din care trebuie tradus(String)
     * @param toLanguage Limba in care trebuie tradus(String)
     * @return Lista cu cele 3 variante de traduceri
     **/
    ArrayList<String> translateSentances = translateSentences(sentence, fromLanguage, toLanguage);

```
Implementare: Am un array de pozitii care stie ultima pozitie din multitudinea de posibilitati a cuvantului care sa scris data precedenta, si daca am ajuns la sfarsitul posibilitatilor pentru un cuvant incep sa incrementez pozitiile urmatorului cuvant tradus, asa cum se cer doar 3 variante posibile in cazul cel mai defavorabil vor fi 2 cuvinte cu doar 2 posibilitati de scris, deci metoda va face propozitiile: 1 1; 2 1; 2 2, deci 3 variante, satisface conditia, cuvantul il extrag cu metoda interna care returneaza cuvantul in obiect Word si forma lui

## Structura Proiectului
* Enum
    
    + SingularOrPluralForm - Enum utilizat pentru a arata forma singulara, plurala sau nespecificata a unui cuvant

* Exception

    + FileProblemException - Exceptie pentru a arata probleme cu fisierele, nu sa format path pentru Dictio, sau nu sa format fisierul pentru export

    + LanguageNotFoundException - Exceptie care apare cand nu se gaseste limba dorita

* InputWord

    + Word - Toata informatia necesara despre un cuvant

    + Definition - Toate definitiile unui cuvant

* Json

    + JsonWork - Citirea si scriere din JSON

* Translator

    + Dictionaries - Toate dictionarele cu toate limbile

    + Translator - Toate metodele de traducere

* resources

    + Dictio - Toate dictionarele de intrare se afla aici

    + DictioOut - Toate dictionarele exportate se afla aici

## Testari

Testarile se pot face din AppTest,auto si manual, contine o clasa cu main, care poate testa toate utilitatile
Deasemenea daca se vrea un test care imiteaza un checher, se poate utiliza maven test