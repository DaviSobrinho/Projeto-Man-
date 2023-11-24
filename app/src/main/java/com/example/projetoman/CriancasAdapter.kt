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


class CriancasAdapter(private val context: Context, crianca: List<Crianca> = emptyList(),
) : RecyclerView.Adapter<CriancasAdapter.ViewHolder>() {
    private val crianca = crianca.toMutableList()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(crianca: Crianca) {
            val barra2 = itemView.findViewById<View>(R.id.barra2)
            val barra3 = itemView.findViewById<View>(R.id.barra3)
            val text2 = itemView.findViewById<View>(R.id.text2)
            val text3 = itemView.findViewById<View>(R.id.text3)
            val button = itemView.findViewById<MaterialButton>(R.id.criancasRecyclerViewMaterialButton)
            val nome = itemView.findViewById<TextView>(R.id.criancasRecyclerViewNome)
            val data = itemView.findViewById<TextView>(R.id.criancasRecyclerViewDataNascimento)
            val cancelar = itemView.findViewById<ImageButton>(R.id.criancasRecyclerViewCancelar)
            data.text = crianca.dataNascimento
            nome.text = (crianca.nome)
            text2.setOnClickListener{
                barra2.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                barra3.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                button.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                showDialogFragment(itemView, crianca.id)
            }
            text3.setOnClickListener{
                barra2.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                barra3.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                button.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                showDialogFragment(itemView, crianca.id)
            }
            button.setOnClickListener{
                barra2.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                barra3.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                button.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                showDialogFragment(itemView, crianca.id)
            }
            cancelar.setOnClickListener{
                barra2.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                barra3.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                button.startAnimation(AnimationUtils.loadAnimation(button.context, androidx.appcompat.R.anim.abc_popup_enter))
                showDialogFragmentRemove(itemView, crianca.id)
            }
        }
        private fun showDialogFragment(view: View, id : Int) {
            val dialogFragment: DialogFragment = VisualizarCriancaDialogFragment(id)
            val activity = view.context as AppCompatActivity
            dialogFragment.show(activity.supportFragmentManager, null)
        }
        private fun showDialogFragmentRemove(view: View,id: Int){
            val dialogFragment: DialogFragment = ConfirmarExclusaoDialog(id,"Deseja mesmo excluir essa criança da base de dados?")
            val activity = view.context as AppCompatActivity
            dialogFragment.show(activity.supportFragmentManager, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.criancasrecyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crianca = crianca[position]
        holder.bind(crianca)
    }

    override fun getItemCount(): Int = crianca.size
    fun refresh(crianca: List<Crianca>) {
        this.crianca.clear()
        this.crianca.addAll(crianca)
        notifyDataSetChanged()
    }

}