package com.cetinje.bozo.cetinjevodic;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Petar on 3/21/2017.
 */

public class CulturalHeritageRecyclerAdapter extends RecyclerView.Adapter<CulturalHeritageRecyclerAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<CulturalHeritage> culturalHeritages;

    CulturalHeritageRecyclerAdapter(Context context, ArrayList<CulturalHeritage> culturalHeritages)
    {
        this.context = context;
        this.culturalHeritages = culturalHeritages;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cutural_heritage_row_layout, parent, false);
        CulturalHeritageRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new CulturalHeritageRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.culturalHeritageTextViewTitle.setText(culturalHeritages.get(position).getName());
        holder.culturalHeritageTextViewTitle.setTag(culturalHeritages.get(position).getId());
        Glide.with(context).load(context.getFileStreamPath(culturalHeritages.get(position).getLogo())).into(holder.culturalHeritageImageView);
        holder.culturalHeritageTextViewDescription.setText(culturalHeritages.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return culturalHeritages.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView culturalHeritageImageView;
        TextView culturalHeritageTextViewTitle, culturalHeritageTextViewDescription;
        public RecyclerViewHolder(final View view)
        {
            super(view);
            culturalHeritageImageView = (ImageView) view.findViewById(R.id.cultural_heritage_image_view);
            culturalHeritageTextViewTitle = (TextView) view.findViewById(R.id.cultural_heritage_text_view_title);
            culturalHeritageTextViewDescription = (TextView) view.findViewById(R.id.cultural_heritage_text_view_description);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    DatabaseHelper db = new DatabaseHelper(context);
                    ArrayList<Video> videos = db.getCulturalHeritageVideos(String.valueOf(culturalHeritageTextViewTitle.getTag()));
                    showRadioButtonDialog(context, videos);

                }
            });

        }
    }

    private static void showRadioButtonDialog(final Context context, final ArrayList<Video> videos) {
        // custom dialog
        final Intent ii = new Intent(context, CulturalHeritagePagerActivity.class);
        ii.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });
        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
        if(videos.size() == 1)
        {
            ii.putExtra("name", videos.get(0).getName());
            context.startActivity(ii);
        }
        else{
            for(int i=0;i<videos.size();i++){
                RadioButton rb=new RadioButton(context); // dynamically creating RadioButton and adding to RadioGroup.
                rb.setText(videos.get(i).getName());
                rg.addView(rb);
            }
            dialog.show();
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        ii.putExtra("name", videos.get(x).getName());
                        context.startActivity(ii);
                    }
                }
                dialog.hide();
            }
        });

    }
}
