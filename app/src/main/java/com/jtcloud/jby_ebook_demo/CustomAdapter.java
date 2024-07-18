package com.jtcloud.jby_ebook_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jtcloud.jbyebook.EbookItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<EbookItem> {
    private Context context;
    private List<EbookItem> items;

    public CustomAdapter(Context context, List<EbookItem> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        EbookItem currentItem = items.get(position);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView textView = convertView.findViewById(R.id.item_text);

        textView.setText(currentItem.getText());

        // 使用Picasso加载网络图片
        Picasso.get().load(currentItem.getImageUrl()).into(imageView);

        // 使用Glide加载网络图片
        // Glide.with(context).load(currentItem.getImageUrl()).into(imageView);

        return convertView;
    }
}

