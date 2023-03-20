package com.example.designmobileproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designmobileproject.Masks.MaskQuote;
import com.example.designmobileproject.R;

import java.io.InputStream;
import java.util.List;

public class adapterQuote  extends BaseAdapter {

    Context mContext;
    List<MaskQuote> maskList;

    public adapterQuote(Context mContext, List<MaskQuote> listview) {
        this.mContext = mContext;
        this.maskList = listview;
    }
    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.item_quote,null);

        TextView title = v.findViewById(R.id.tvTitleQuote);
        ImageView Image = v.findViewById(R.id.imgImage);
        TextView description = v.findViewById(R.id.tvDescription);

        MaskQuote maskQuote = maskList.get(position);
        title.setText(maskQuote.getTitle());

        if(maskQuote.getImage().equals("null") || maskQuote.getImage().equals("NULL") )
        {
            Image.setImageResource(R.drawable.absence);
        }
        else
        {
            new DownloadImageTask(Image).execute(maskQuote.getImage());
        }

        description.setText(maskQuote.getDescription());
        return v;
    }
    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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
}
