package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.dialogs.Dialogs;
import ru.kau.mygtd2.interfaces.ResultResponse;
import ru.kau.mygtd2.objects.Comment;
import ru.kau.mygtd2.utils.Utils;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private Context c;
    private List<Comment> lstComment;
    private ResultResponse<Comment> onMenuClickListener;

    public CommentAdapter(Context c, List<Comment> lstComment) {
        this.c = c;
        this.lstComment = lstComment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.comment_cardview,viewGroup,false);

        return new CommentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.title.setText(lstComment.get(position).getTitle());
        viewHolder.datecreate.setText(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), lstComment.get(position).getDateCreate()));

        viewHolder.editComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Вызываем диалог комментария.
                //Bundle bundle = new Bundle();

                //bundle.putSerializable("comment", lstComment.get(position));
                Dialogs.addCommentDialog(c, null, null, null, lstComment.get(position), false);

            }
        });

        viewHolder.moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewHolder.moreDetails.setText(R.string.hide);

                // Считаем количество линий
                Dialogs.addCommentDialog(c, null, null, null, lstComment.get(position), true);

                //viewHolder.title.setLines(5);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView datecreate;
        ImageView editComment;
        TextView moreDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            datecreate = (TextView) itemView.findViewById(R.id.datecreate);
            title = (TextView) itemView.findViewById(R.id.commentTitle);
            editComment = (ImageView) itemView.findViewById(R.id.editComment);
            moreDetails = (TextView) itemView.findViewById(R.id.moreDetails);



        }
    }

    public void setOnMenuClickListener(ResultResponse<Comment> onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

}
