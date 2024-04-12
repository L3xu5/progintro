package markup;

import java.util.List;

public class Emphasis extends MarkingClass implements Include {
    public Emphasis(List<Include> includes) {
        super(includes);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append("*");
        super.toMarkdown(sb);
        sb.append("*");
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append("[i]");
        super.toBBCode(sb);
        sb.append("[/i]");
    }
}
