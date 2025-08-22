package com.example.projectf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// Simple ArrayAdapter for AudioItem rows.
// أداپتر بسيط لعرض عناصر الصوتيات في الليست فيو
import java.util.List;

public class AudioListAdapter extends ArrayAdapter<SurahItem> {

    public AudioListAdapter(Context context, List<SurahItem> data) {
        super(context, 0, data);
    }

    private static class ViewHolder {
        TextView tvNumber;
        TextView tvName;
        TextView tvAyatCount;
        TextView tvType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SurahItem item = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_surah, parent, false);

            holder = new ViewHolder();
            holder.tvNumber = convertView.findViewById(R.id.tvSuraNumber);
            holder.tvName = convertView.findViewById(R.id.tvSuraName);
            holder.tvAyatCount = convertView.findViewById(R.id.tvSuraVerses);
            holder.tvType = convertView.findViewById(R.id.tvSuraType);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {
            holder.tvNumber.setText(String.valueOf(item.getNumber()));
            holder.tvName.setText(item.getName());
            holder.tvAyatCount.setText(item.getAyatCount() + " آيات");
            holder.tvType.setText(item.getSurahType()); // النوع
        }

        return convertView;
    }
}
