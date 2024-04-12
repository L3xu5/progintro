package markup;

import java.util.List;

public abstract class MarkingClass implements MarkdownElement, BBCodeElement {
    List<Include> includes;
    MarkingClass(List<Include> includes) {
        this.includes = includes;
    }
    @Override
    public void toMarkdown(StringBuilder sb) {
        for (Include include : includes) {
            include.toMarkdown(sb);
        }
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        for (Include include : includes) {
            include.toBBCode(sb);
        }
    }
}
