package com.example.projetoman

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Layout
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class ConfirmarExclusaoDialog(private var idAlvo: Int, private var alvo: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // ... (restante do código)

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(alvo)
                .setPositiveButton("Excluir",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Adicione o código para excluir o item do banco de dados aqui
                        excluirItemDoBancoDeDados(idAlvo  , alvo)

                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })

            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun excluirItemDoBancoDeDados(idAlvo: Int, alvo: String) {
        if(alvo == "Deseja mesmo excluir essa criança da base de dados?"){
            val db = AppDatabase.getDatabase(requireContext())
            val crianca : Crianca = db.daoFunctions().findCriancaByID(idAlvo)
            db.daoFunctions().deleteCrianca(crianca)
            Toast.makeText(requireContext(), "Criança excluída da base de dados!", Toast.LENGTH_SHORT).show()
        }else{
            val db = AppDatabase.getDatabase(requireContext())
            val encontro : Encontro = db.daoFunctions().findEncontroByID(idAlvo)
            db.daoFunctions().deleteEncontro(encontro)
            Toast.makeText(requireContext(), "Encontro excluído da base de dados!", Toast.LENGTH_SHORT).show()

        }

        // Adicione aqui o código para excluir o item do banco de dados
        // Você pode usar um ViewModel e um Repository para gerenciar as operações no banco de dados
        // Exemplo: viewModel.excluirItem(idAlvo, alvo)
    }
}
