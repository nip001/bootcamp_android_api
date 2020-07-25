package com.juaracoding.haniffathullahujian4_ganjil.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.juaracoding.haniffathullahujian4_ganjil.R;
import com.juaracoding.haniffathullahujian4_ganjil.model.Data;
import com.juaracoding.haniffathullahujian4_ganjil.model.GetData;
import com.juaracoding.haniffathullahujian4_ganjil.model.TampilData.GetAllData;
import com.juaracoding.haniffathullahujian4_ganjil.model.TampilData.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GetDataAdapter extends RecyclerView.Adapter<GetDataAdapter.MyViewHolder>{
    private List<Product> filmList;
    private Data data;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView product,price,desc,variant;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            product=(TextView) view.findViewById(R.id.editProduct);
            price=(TextView) view.findViewById(R.id.editPrice);
            desc=(TextView) view.findViewById(R.id.editDesc);
            variant=(TextView) view.findViewById(R.id.editVariant);
            image = (ImageView)view.findViewById(R.id.imageView);
        }
    }


    public GetDataAdapter(List<Product> filmList) {
        this.filmList = filmList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product film = filmList.get(position);
        holder.product.setText(film.getProductName());
        holder.price.setText(film.getPrice());
        holder.variant.setText(film.getVariant());
        holder.desc.setText(film.getDescription());

        Picasso.get().load(film.getImage()).into(holder.image);
    }



    @Override
    public int getItemCount() {

        return filmList.size();
    }

}
