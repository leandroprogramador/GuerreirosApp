package com.leandro.guerreirosapp.Adapter.Alunos;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

import com.leandro.guerreirosapp.Model.Aluno;
import com.leandro.guerreirosapp.R;

import java.util.List;

/**
 * Created by leani on 01/12/2017.
 */

public class AlunosAdapter extends RecyclerView.Adapter<AlunosAdapter.AlunosViewHolder>{

    List<Aluno> mDataset;
    IAlunoClick iAlunoClick;

    public AlunosAdapter(List<Aluno> mDataset, IAlunoClick iAlunoClick) {
        this.mDataset = mDataset;
        this.iAlunoClick = iAlunoClick;
    }

    @Override
    public AlunosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_alunos,parent,false);
        return new AlunosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlunosViewHolder holder, int position) {
        holder.txtNome.setText(mDataset.get(position).getNome());
        if(mDataset.get(position).isFuncional() && mDataset.get(position).isJiujitsu() ){
          holder.txtModalidade.setText("JiuJitsu/Funcional");
        } else if(mDataset.get(position).isFuncional() && !mDataset.get(position).isJiujitsu()){
            holder.txtModalidade.setText("Funcional");
        } else{
            holder.txtModalidade.setText("Jiu Jitsu");
        }
    }

    public void addItem(Aluno aluno){
        mDataset.add(aluno);
        notifyItemInserted(getItemCount());
    }

    public void removeAll(){
        mDataset.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class AlunosViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView txtNome, txtModalidade;
        ImageView imgPhone, imgMail;
        CircleImageView imgProfile;
        public AlunosViewHolder(View itemView) {
            super(itemView);
            txtNome = (AppCompatTextView) itemView.findViewById(R.id.aluno_nome);
            txtModalidade = (AppCompatTextView) itemView.findViewById(R.id.aluno_modalidade);
            imgPhone = (ImageView) itemView.findViewById(R.id.img_call);
            imgMail = (ImageView) itemView.findViewById(R.id.img_mail);
            imgProfile = (CircleImageView) itemView.findViewById(R.id.aluno_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iAlunoClick.onItemClick(mDataset.get(getLayoutPosition()));
                }
            });

            imgPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iAlunoClick.onPhoneClick(mDataset.get(getLayoutPosition()), getLayoutPosition());
                }
            });

            imgMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iAlunoClick.onMailClick(mDataset.get(getLayoutPosition()), getLayoutPosition());
                }
            });
        }
    }

    public interface IAlunoClick{
        void onItemClick(Object object);
        void onPhoneClick(Object object, int position);
        void onMailClick(Object object, int position);
    }
}
