package com.example.designmobileproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designmobileproject.Masks.MaskProfile;
import com.example.designmobileproject.R;

import java.util.List;

public class adapterProfile extends BaseAdapter {

    private Context mContext;
    List<MaskProfile> maskList;

    public adapterProfile(Context mContext, List<MaskProfile> maskList) {
        this.mContext = mContext;
        this.maskList = maskList;
    }


    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return  maskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return maskList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MaskProfile maskProfileImage  = maskList.get(position);
        View v = null;
        if(maskProfileImage.getImageProfile() == null) // Если картинка не указана, то это последний элемент
        {
            v = View.inflate(mContext, R.layout.item_profile_image_add, null); // Выводится форма с кнопкой
        }
        else
        {
            v = View.inflate(mContext, R.layout.item_profile_image, null); // Вывод стандартной формы

            ImageView Image = v.findViewById(R.id.imgImage);
            TextView dateCreat = v.findViewById(R.id.tvDate);

            if(maskProfileImage.getImageProfile().exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(maskProfileImage.getImageProfile().getAbsolutePath());
                Image.setImageBitmap(myBitmap);
            }
            dateCreat.setText(maskProfileImage.getData());
        }
        return v;
    }
}

