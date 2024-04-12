package markup;

import java.util.ArrayList;
import java.util.List;

public class ListItem implements BBCodeElement {
    List<ListInclude> includes;

    public ListItem(List<ListInclude> includes) {
        this.includes = includes;
    }
    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append("[*]");
        for (ListInclude include : includes) {
            include.toBBCode(sb);
        }
    }
}
