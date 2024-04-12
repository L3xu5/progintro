package markup;

import java.util.List;

public class UnorderedList extends BBCodeList {
    public UnorderedList(List<ListItem> includes) {
        super(includes);
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append("[list]");
        super.toBBCode(sb);
        sb.append("[/list]");
    }
}