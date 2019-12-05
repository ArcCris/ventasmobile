package com.example.ventas.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ventas.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaPagados extends BaseAdapter{

    private List<PagadosTO> lPagados;
    private Context mContext;

    public ListaPagados(Context mContext, List<PagadosTO> persona){
        this.mContext = mContext;
        this.lPagados = persona;
    }


    @Override
    public int getCount() {
        return lPagados.size();
    }

    @Override
    public Object getItem(int position) {
        return lPagados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.helper_listado_pagados, null);

        final TextView numeroPagado = view.findViewById(R.id.numeropagado);

        numeroPagado.setText(String.valueOf(position+1));
        if(lPagados.get(position).getStatus()==0){
            numeroPagado.setBackgroundColor(Color.parseColor("#008577"));
        }else if(lPagados.get(position).getStatus()==1){
            numeroPagado.setBackgroundColor(Color.RED);
        }
        numeroPagado.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        numeroPagado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();
                int estado = 0;
                if(lPagados.get(position).getStatus() == 0 ){
                    estado = 1;
                    //numeroPagado.setBackgroundColor(Color.parseColor("#008577"));
                }else if(lPagados.get(position).getStatus() == 1){
                    estado = 0;
                    //numeroPagado.setBackgroundColor(Color.RED);
                }

                PagadosTO p= new PagadosTO();
                p.setId(lPagados.get(position).getId());
                p.setStatus(estado);
                p.setNumero(lPagados.get(position).getNumero());
                p.setDesc(lPagados.get(position).getDesc());

                ref.child("pagados").child(p.getId()).setValue(p);

            }
        });

        return view;
    }
}
