package main.java.EZCode.Pantallas;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import main.java.EZCode.Entidades.Meta;



public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {


    private int resource;
    private ArrayList<Meta> listaMetas;
    String id= PantallaAutenticacion.autenticacion.getCurrentUser().getUid();




    public ListaAdapter(ArrayList<Meta> listaMetas, int resource){
        this.listaMetas=listaMetas;
        this.resource=resource;
    }
    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAdapter.ViewHolder holder, int position) {

        Meta x = listaMetas.get(position);
        holder.nombre.setText(x.getNombre());

        holder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PantallaMoficarMeta.class);
                intent.putExtra("item",x);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaMetas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button nombre;

        public View view;

        public ViewHolder(@NonNull  View view) {
            super(view);
            this.view=view;
            this.nombre=(Button) view.findViewById(R.id.nombre_meta);

        }
    }


    }


