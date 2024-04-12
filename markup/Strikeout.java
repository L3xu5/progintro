package markup;

import java.util.List;

public class Strikeout extends MarkingClass implements Include {
    public Strikeout(List<Include> includes) {
        super(includes);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append("~");
        super.toMarkdown(sb);
        sb.append("~");
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append("[s]");
        super.toBBCode(sb);
        sb.append("[/s]");
    }
}
