package dz.imane.travel_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    List<Favorie> favorites;
    private OnFavoriteRemoveListener removeListener;

    public FavoriteAdapter(List<Favorie> favorites , OnFavoriteRemoveListener removeListener) {
        this.favorites = favorites;
        this.removeListener = removeListener;
    }


    public void updateFavorites(List<Favorie> newFavorites) {
        favorites.clear();
        favorites.addAll(newFavorites);

        notifyDataSetChanged();
    }

    public Favorie getItem(int position) {
        return favorites.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favoriteImage;
        EditText poiName;
        Button viewDetails;
        ImageView removeFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
         //   favoriteImage = itemView.findViewById(R.id.favo_image);
            poiName = itemView.findViewById(R.id.poi_name);
            viewDetails = itemView.findViewById(R.id.voirpoi);
            removeFavorite = itemView.findViewById(R.id.sup_favorie);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorie favoriteItem = favorites.get(position);

        // Bind the data to the views in the item layout
       // holder.favoriteImage.setImageResource(favoriteItem.getImageResource()); // Remplacez favoriteItem.getImageResource() par la méthode appropriée pour obtenir la ressource d'image de l'objet favori
        holder.poiName.setText(favoriteItem.getNom_poi());
        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for view details button
                // You can launch a new activity or perform any other action here
            }
        });

        holder.removeFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeListener != null) {
                    removeListener.onFavoriteRemove(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }
    public interface OnFavoriteRemoveListener {
        void onFavoriteRemove(int position);
    }


}
