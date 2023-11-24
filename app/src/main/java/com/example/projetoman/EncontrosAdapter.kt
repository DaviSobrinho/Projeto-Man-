package com.example.projetoman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton


class EncontrosAdapter(private val context: Context, encontro: List<Encontro> = emptyList(),
) : RecyclerView.Adapter<EncontrosAdapter.ViewHolder>() {
    private val encontro = encontro.toMutableList()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(encontro: Encontro) {
            val barra1 = itemView.findViewById<View>(R.id.barra1)
            val text1 = itemView.findViewById<View>(R.id.encontrosRecyclerViewData)
            val button = itemView.findViewById<MaterialButton>(R.id.encontrosRecyclerViewMaterialButton)
            val titulo = itemView.findViewById<TextView>(R.id.encontrosRecyclerViewTitulo)
            val data = itemView.findViewById<TextView>(R.id.encontrosRecyclerViewData)
            val cancelar = itemView.findViewById<ImageButton>(R.id.encontrosRecyclerViewCancelar)
            data.text = encontro.dataEncontro
            titulo.text = (encontro.titulo)
            button.setOnClickListener{
                barra1.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                button.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                showDialogFragment(itemView, encontro.id)
            }
            cancelar.setOnClickListener{
                barra1.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                button.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                showDialogFragmentRemove(itemView, encontro.id)
            }
            text1.setOnClickListener{
                barra1.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                button.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                showDialogFragment(itemView, encontro.id)
            }
        }
        private fun showDialogFragment(view: View, id : Int) {
            val dialogFragment: DialogFragment = VisualizarEncontroDialogFragment(id)
            val activity = view.context as AppCompatActivity
            dialogFragment.show(activity.supportFragmentManager, null)
        }
        private fun showDialogFragmentRemove(view: View,id: Int){
            val dialogFragment: DialogFragment = ConfirmarExclusaoDialog(id,"Deseja mesmo excluir esse encontro da base de dados?")
            val activity = view.context as AppCompatActivity
            dialogFragment.show(activity.supportFragmentManager, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.encontrosrecyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val encontro = encontro[position]
        holder.bind(encontro)
    }

    override fun getItemCount(): Int = encontro.size
    fun refresh(encontro: List<Encontro>) {
        this.encontro.clear()
        this.encontro.addAll(encontro)
        notifyDataSetChanged()
    }

}