package com.steven.diseoconstrainlayout.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.steven.diseoconstrainlayout.data.NuevaNotaDialogViewModel;
import com.steven.diseoconstrainlayout.db.entity.NotaEntity;
import com.steven.diseoconstrainlayout.R;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<NotaEntity> mValues;
    private final Context context;
    private NuevaNotaDialogViewModel viewModel;
    public MyItemRecyclerViewAdapter(List<NotaEntity> items, Context context) {
        mValues = items;
        this.context = context;
        viewModel = new ViewModelProvider((AppCompatActivity) context).get(NuevaNotaDialogViewModel.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_nota_lista, parent, false);
        return new ViewHolder(view);
    }
    public void setNuevasNotas(List<NotaEntity> nuevasNotas) {
        this.mValues = nuevasNotas;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txtTitulo.setText(holder.mItem.getTitulo());
        holder.txtContenido.setText(holder.mItem.getContenido());

        fondo(holder);

        holder.mView.setOnLongClickListener(view -> {
            if (view.getId() == R.id.cardView) {
                viewModel.abrirOpciones(context, holder.mItem.getId());
                return true;
            }
            return false;
        });

        if (holder.mItem.isFavorita()) {
            holder.imgFavorito.setImageResource(R.drawable.ic_baseline_star_24);
        }

        holder.imgFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mItem.isFavorita()) {
                    holder.mItem.setFavorita(false);
                    holder.imgFavorito.setImageResource(R.drawable.ic_baseline_star_border_24);
                }else {
                    holder.mItem.setFavorita(true);
                    holder.imgFavorito.setImageResource(R.drawable.ic_baseline_star_24);
                }
                viewModel.updateNota(holder.mItem);
            }
        });
    }
    private void fondo(ViewHolder holder) {
        switch (holder.mItem.getColor()) {
            case "rojo":
                holder.constraintLayout.setBackgroundResource(R.color.rojo);
                break;
            case "verde":
                holder.constraintLayout.setBackgroundResource(R.color.verde);
                break;
            case "azul":
                holder.constraintLayout.setBackgroundResource(R.color.azul);
                break;
        }
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtTitulo;
        public final TextView txtContenido;
        public final ImageView imgFavorito;
        public final ConstraintLayout constraintLayout;
        public NotaEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            constraintLayout = view.findViewById(R.id.fondo);
            txtTitulo = view.findViewById(R.id.txtTitulo);
            txtContenido = view.findViewById(R.id.txtContenido);
            imgFavorito = view.findViewById(R.id.imgFavorito);
        }
    }
}