package dz.imane.travel_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class  WishAdapter extends RecyclerView.Adapter<WishAdapter.ViewHolder> {

    List<Wish> wishes;
    private WishAdapter.OnWishRemoveListener removeListener;

    public  WishAdapter(List<Wish> wishes ,  WishAdapter.OnWishRemoveListener removeListener) {
        this.wishes = wishes;
        this.removeListener = removeListener;
    }

    public void updatewishes(List<Wish> newWishes ) {
        wishes.clear();
        wishes.addAll(newWishes);
        notifyDataSetChanged();
    }
    public Wish getItem(int position) {
        return wishes.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText poiName;
        Button viewDetails;
        ImageView removewish;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //   favoriteImage = itemView.findViewById(R.id.favo_image);
            poiName = itemView.findViewById(R.id.poi_namew);
            viewDetails = itemView.findViewById(R.id.voirpoiwish);
            removewish = itemView.findViewById(R.id.sup_wish);
        }
    }

    @NonNull
    @Override
    public WishAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wish, parent, false);
        return new WishAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishAdapter.ViewHolder holder, int position) {
        Wish wishItem = wishes.get(position);

        // Bind the data to the views in the item layout
        // holder.favoriteImage.setImageResource(favoriteItem.getImageResource()); // Remplacez favoriteItem.getImageResource() par la méthode appropriée pour obtenir la ressource d'image de l'objet favori
        holder.poiName.setText(wishItem.getNom_poi());
        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for view details button
                // You can launch a new activity or perform any other action here
            }
        });
        holder.removewish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeListener != null) {
                    removeListener.onWishRemove(holder.getAdapterPosition());
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return wishes.size();
    }

    public interface OnWishRemoveListener {
        void onWishRemove(int position);
    }
}
