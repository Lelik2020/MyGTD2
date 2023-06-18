package ru.kau.mygtd2.adapters.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.objects.ProjectStatus;

public class ProjectStatusAdapter extends ArrayAdapter<ProjectStatus> {

    private Context mContext;
    private ArrayList<ProjectStatus> listState;
    private ProjectStatusAdapter myAdapter;
    private boolean isFromView = false;

    public ProjectStatusAdapter(Context context, int resource, List<ProjectStatus> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<ProjectStatus>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_list, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getTitle());
        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();
                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                    Log.i("checkpos", "" + listState.get(position).getTitle());
                    Log.i("valueif", "check" + isFromView);
                    Toast.makeText(getContext(), "" + listState.get(position).isSelected(), Toast.LENGTH_LONG).show();
                    if (listState.get(position).isSelected()) {
                        Log.i("showStatus", "You are beast");
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(listState.get(position).getTitle(), listState.get(position).getTitle());
                        editor.apply();
                    } else {
                        Log.i("showStatus", "You are Nut");
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(listState.get(position).getTitle());
                        editor.apply();
                    }
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        public CheckBox mCheckBox;
    }


}
