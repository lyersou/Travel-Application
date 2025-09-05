package dz.imane.travel_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dz.imane.travel_app.NestedItemDataModel;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {

    private List<NestedItemDataModel> mList;

    public NestedAdapter(List<NestedItemDataModel> mList){
        this.mList = mList;
    }
    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item , parent , false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {

        NestedItemDataModel nestedItem = mList.get(position);

        holder.mCBox.setText(mList.get(position).getItemText());
        holder.mCBox.setChecked(mList.get(position).isChecked());

        holder.mCBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nestedItem.setChecked(isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        private CheckBox mCBox;
        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            mCBox = itemView.findViewById(R.id.nestedItemCB);
        }
    }
}
