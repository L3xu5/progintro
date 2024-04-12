package markup;

public class Text implements Include {
    private final String text;

    public Text(String text) {
        super();
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(text);
    }
    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append(text);
    }
}
