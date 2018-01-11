package com.leandro.guerreirosapp.Adapter.Alunos;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leandro.guerreirosapp.Activities.NovaFaixaActivity;
import com.leandro.guerreirosapp.Model.Faixa;
import com.leandro.guerreirosapp.R;

import java.util.List;

/**
 * Created by leandro.araujo on 11/01/2018.
 */

public class FaixasSpinnerAdapter extends BaseAdapter {
    Context context;
    int resource;
    List<Faixa> faixas;

    public FaixasSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Faixa> faixas) {

        this.context = context;
        this.resource = resource;
        this.faixas = faixas;
    }

    @Override
    public int getCount() {
        return faixas.size();
    }

    public void addItem(Faixa faixa){
        faixas.add(faixa);
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NovaFaixaActivity activity = (NovaFaixaActivity) context;
        LayoutInflater layoutInflater =  activity.getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.row_spinner_faixas, parent, false);
        TextView txtView = layout.findViewById(R.id.txt_faixa);
        ImageView imageView = layout.findViewById(R.id.img_faixa);
        txtView.setText(faixas.get(position).getFaixa().toString());
        Resources resources = context.getResources();
        int imgId = resources.getIdentifier(faixas.get(position).getCor(), "drawable", context.getPackageName());
        imageView.setImageResource(imgId);
        return layout;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    public Faixa getItem(int position){
        return faixas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
