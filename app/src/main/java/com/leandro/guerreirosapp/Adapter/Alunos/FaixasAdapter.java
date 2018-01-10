package com.leandro.guerreirosapp.Adapter.Alunos;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leandro.guerreirosapp.Helper.DateHelper;
import com.leandro.guerreirosapp.Model.Faixa;
import com.leandro.guerreirosapp.Model.Graduacao;
import com.leandro.guerreirosapp.R;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by leandro.araujo on 10/01/2018.
 */

public class FaixasAdapter extends RecyclerView.Adapter<FaixasAdapter.FaixasViewHolder> implements ItemTouchHelperAdapter {

    List<Graduacao> mDataSet;

    public FaixasAdapter(List<Graduacao> mDataSet) {
        this.mDataSet = mDataSet;
    }

    @Override
    public FaixasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_graducaoes, parent,false);
        return new FaixasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FaixasViewHolder holder, int position) {
        holder.txtFaixa.setText(mDataSet.get(position).getFaixa().getFaixa());
        holder.txtData.setText(DateHelper.timeStampToBr(mDataSet.get(position).getDataGraduacao()));
        holder.imageFaixa.setImageResource(R.drawable.amarelo);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void add(Graduacao graduacao){
        mDataSet.add(graduacao);
        notifyItemInserted(getItemCount());
    }

    public void remove(int position){
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDataSet, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDataSet, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public boolean onItemDismiss(int position) {
        remove(position);
        return true;
    }

    public class FaixasViewHolder extends RecyclerView.ViewHolder{

        TextView txtFaixa, txtData;
        ImageView imageFaixa;
        public FaixasViewHolder(View itemView) {
            super(itemView);

            txtFaixa = itemView.findViewById(R.id.txt_faixa);
            txtData = itemView.findViewById(R.id.txt_data);
            imageFaixa = itemView.findViewById(R.id.img_faixa);
        }
    }

}

