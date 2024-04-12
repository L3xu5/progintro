package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class BBCodeList implements BBCodeElement, ListInclude {
    List<ListItem> includes;

    public BBCodeList(List<ListItem> includes) {
        this.includes = includes;
    }
    @Override
    public void toBBCode(StringBuilder sb) {
        for (ListItem include : includes) {
            include.toBBCode(sb);
        }
    }
}
