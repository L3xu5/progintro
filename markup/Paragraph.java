package markup;

import java.util.List;

public class Paragraph extends MarkingClass implements ListInclude {
    public Paragraph(List<Include> includes) {
        super(includes);
    }
}
