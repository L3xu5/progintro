package markup;

import java.util.List;

public class OrderedList extends BBCodeList {
    public OrderedList(List<ListItem> includes) {
        super(includes);
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append("[list=1]");
        super.toBBCode(sb);
        sb.append("[/list]");
    }
}