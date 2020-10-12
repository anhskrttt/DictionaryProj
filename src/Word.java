public class Word {

    private String word_target;
    private String word_explain;


    public Word(){}

    public Word(String word_target, String word_explain){
        this.word_target = word_target;
        this.word_explain = word_explain;
    }


    public String getWord_target() {
        return word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    @Override
    public String toString() {
        /* Printing list vocabs to console by format capitalization the first letter of word_target and word_explain. */
        String word = Character.toUpperCase(word_target.charAt(0)) + word_target.substring(1).toLowerCase();
        String def = Character.toUpperCase(word_explain.charAt(0)) + word_explain.substring(1).toLowerCase();

        return String.format("| %-20s| %s", word, def);
    }


}
