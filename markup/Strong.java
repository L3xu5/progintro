package markup;

import java.util.List;

public class Strong extends MarkingClass implements Include {
    public Strong(List<Include> includes) {
        super(includes);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append("__");
        super.toMarkdown(sb);
        sb.append("__");
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append("[b]");
        super.toBBCode(sb);
        sb.append("[/b]");
    }
}
