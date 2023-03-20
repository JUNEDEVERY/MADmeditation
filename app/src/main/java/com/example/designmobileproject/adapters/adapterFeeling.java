package com.example.designmobileproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designmobileproject.Masks.MaskFeeling;
import com.example.designmobileproject.R;

import java.io.InputStream;
import java.util.List;

public class adapterFeeling extends RecyclerView.Adapter<adapterFeeling.ViewHolder> {

    private List<MaskFeeling> dataModalArrayList;
    private Context context;

    public adapterFeeling(List<MaskFeeling> dataModalArrayList, Context context) { // конструктор
        this.dataModalArrayList = dataModalArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public adapterFeeling.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new adapterFeeling.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feeling, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MaskFeeling modal = dataModalArrayList.get(position);
        holder.title.setText(modal.getTitle());

        if(modal.getImage().equals("null"))
        {
            holder.image.setImageResource(R.drawable.absence);
        }
        else
        {
            new adapterFeeling.DownloadImageTask(holder.image)
                    .execute(modal.getImage());
        }
    }

    @Override
    public int getItemCount() {
        return dataModalArrayList.size();
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitleQuote);
            image = itemView.findViewById(R.id.imgFeeling);
        }
    }

}
