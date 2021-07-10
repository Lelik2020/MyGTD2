package ru.kau.mygtd2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.fragments.AddInformationFragment;
import ru.kau.mygtd2.interfaces.ResultResponse;
import ru.kau.mygtd2.objects.InfoStatus;
import ru.kau.mygtd2.objects.InfoTypes;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.Utils;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import ru.kau.mygtd.objects.Task;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder>{

    Context c;
    List<Information> lstInformation;

    private ResultResponse<Information> onItemClickListener;

    private ResultResponse<Information> onMenuClickListener;

    CardView.LayoutParams lParamscv;

    public InformationAdapter(Context c, List<Information> lstInformation) {
        this.c = c;
        this.lstInformation = lstInformation;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView infodetail;
        TextView infotitle;
        ImageView infotype;
        ImageView infotype2;

        ImageView editInfo;

        TextView infotype3;

        TextView dateCreate;
        ImageView menu;
        RoundTextView roundTextView;
        CardView card;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.allinfoinfo);
            infotype = itemView.findViewById(R.id.infotype);
            infotype2 = itemView.findViewById(R.id.infotype2);
            infotitle = itemView.findViewById(R.id.infotitle);

            infotype3 = itemView.findViewById(R.id.infotype3);

            editInfo = itemView.findViewById(R.id.editTask);

            title = (TextView) itemView.findViewById(R.id.informationtitle);
            infodetail = itemView.findViewById(R.id.infodetail);
            dateCreate = (TextView) itemView.findViewById(R.id.date_create);
            menu = (ImageView) itemView.findViewById(R.id.itemMenu);
            roundTextView = (RoundTextView) itemView.findViewById(R.id.status);

        }
    }

    @NonNull
    @Override
    public InformationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //View v= LayoutInflater.from(c).inflate(R.layout.information_cardview,viewGroup,false);
        View v= LayoutInflater.from(c).inflate(R.layout.info_cardview,viewGroup,false);

        return new InformationAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull InformationAdapter.ViewHolder viewHolder, int i) {
        final Information information = getItem(i);
        final InfoStatus infoStatus = MyApplication.getDatabase().infoStatusDao().getById(information.getIdstatus());

        lParamscv = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
        lParamscv.setMargins(10, 9, 5, 5);
        viewHolder.card.setLayoutParams(lParamscv);

        InfoTypes infoTypes = MyApplication.getDatabase().infoTypesDao().getById(information.getTypeOfInfo().Value);
        viewHolder.infotype.setImageResource(Utils.getImageResourceInfoType(information.getTypeOfInfo()));

        viewHolder.infotype.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewHolder.infotype2.setImageResource(Utils.getImageResourceInfoType(information.getTypeOfInfo()));

        viewHolder.infotype2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewHolder.infotitle.setText(infoTypes.getVisualTitle() + " - " + information.getId());

        viewHolder.infotype3.setText(infoTypes.getTitle());

        viewHolder.infotitle.setTextColor(Color.parseColor(infoTypes.getColor()));
        TxtUtils.underlineTextView(viewHolder.infotitle);

        viewHolder.title.setText(information.getTitle());
        viewHolder.infodetail.setText(information.getDescription());
        viewHolder.dateCreate.setText(Utils.dateToString(information.getDateCreate()));
        viewHolder.roundTextView.setText("  " + infoStatus.getTitle() + "  ");
        viewHolder.roundTextView.setCorner(15);
        viewHolder.roundTextView.setBgColor(Color.parseColor(infoStatus.getColor()));

        GradientDrawable gradientDrawable = new GradientDrawable();
        //gradientDrawable.setColor(Color.GREEN);
        //gradientDrawable.setShape();

        gradientDrawable.setColor(Color.parseColor(Const.DEFAULT_TASKBG_COLOR));

        /*try {
            gradientDrawable.setColor(Color.parseColor(lstTask.get(i).getBgColor()));
        } catch (Exception e) {
            //viewHolder.card.setBackgroundResource(R.drawable.selector_2);
            gradientDrawable.setColor(Color.parseColor(Const.DEFAULT_TASKBG_COLOR));
        }*/
        gradientDrawable.setStroke(7, R.color.colorPrimaryDark);
        setCornerRadius(gradientDrawable, 25f);
        viewHolder.card.setBackground(gradientDrawable);

        bindInformationView(viewHolder, i);
        //viewHolder.dateCreate.setText("88888");
    }

    @Override
    public int getItemCount() {
        return lstInformation.size();
    }

    private Information bindInformationView(final ViewHolder holder, final int position) {

        final Information information = (Information) lstInformation.get(position);

        holder.menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onMenuClickListener != null) {
                    onMenuClickListener.onResultRecive(information);
                    //lstInformation.
                    //InformationAdapter.this.notify();
                    //InformationAdapter.this.notifyAll();
                    //holder.roundTextView.setText(infoStatus.getTitle());
                    //Information info = MyApplication.getDatabase().informationDao().getById(information.getId());
                    //holder.roundTextView.setText(MyApplication.getDatabase().infoStatusDao().getById(3).getTitle());
                    //holder.roundTextView.setBgColor(Color.parseColor(MyApplication.getDatabase().infoStatusDao().getById(3).getColor()));
                }
            }

        });

        holder.editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInformationFragment addInformationFragment = new AddInformationFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("information", information);
                addInformationFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity)c).getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("addInformationFragment").replace(R.id.frame_container,addInformationFragment).commit();
            }
        });
        return null;
    }

    public void setOnItemClickListener(ResultResponse<Information> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnMenuClickListener(ResultResponse<Information> onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public Information getItem(int pos) {
        if (lstInformation == null || lstInformation.isEmpty()) {
            return null;
        }
        if (lstInformation.size() - 1 >= pos) {
            return lstInformation.get(pos);
        } else {
            return lstInformation.get(0);
        }
    }

    static void setCornerRadius(GradientDrawable drawable, float radius) {
        drawable.setCornerRadius(radius);
    }

}
