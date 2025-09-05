package dz.imane.travel_app;

import java.util.List;

public class FirstLevelItemDataModel {

    private List<NestedItemDataModel> nestedList;
    private String itemText;
    private boolean isExpandable;

    public FirstLevelItemDataModel(List<NestedItemDataModel> itemList, String itemText) {
        this.nestedList = itemList;
        this.itemText = itemText;
        isExpandable = false;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public List<NestedItemDataModel> getNestedList() {
        return nestedList;
    }

    public String getItemText() {
        return itemText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }
}
