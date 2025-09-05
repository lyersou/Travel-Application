package dz.imane.travel_app;

import android.widget.CheckBox;

public class NestedItemDataModel {

    private String itemText;
    private boolean isChecked;

    private int cat_id;

    private CheckBox checkBox;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public NestedItemDataModel(String itemText, boolean isChecked_, int id_cat) {
        this.itemText = itemText;
        isChecked = isChecked_;
        this.cat_id = id_cat;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
