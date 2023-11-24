package com.example.projetoman

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate

class VisualizarCriancaDialogFragment(val idCrianca : Int) : DialogFragment() {

    private lateinit var inflater : LayoutInflater
    private lateinit var view : View
    private lateinit var nome : EditText
    private lateinit var nomeResponsavel : EditText
    private lateinit var telefone : EditText
    private lateinit var observacoes : EditText
    private lateinit var endereco : EditText
    private lateinit var data : DatePicker
    private lateinit var salvar : MaterialButton
    private lateinit var botaosair : ImageButton
    private var dataAlterada : Boolean = false
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val db = AppDatabase.getDatabase(requireContext())
        val crianca: Crianca = db.daoFunctions().findCriancaByID(idCrianca)
        nome.setText(crianca.nome)
        nomeResponsavel.setText(crianca.nomeResponsavel)
        telefone.setText(crianca.telefoneResponsavel)
        observacoes.setText(crianca.observacoes)
        endereco.setText(crianca.endereco)
        data.updateDate(
            crianca.dataNascimento.substring(6, 10).toInt(),
            crianca.dataNascimento.substring(3, 5).toInt(),
            crianca.dataNascimento.substring(0, 2).toInt()
        )
        configuraBotaoSair(botaosair)
        salvar.setOnClickListener {
            var existeErro = false
            if (nome.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Por favor, insira o nome da crianca!",
                    Toast.LENGTH_SHORT
                ).show()
                existeErro = true
            }
            if (nomeResponsavel.text.isNullOrEmpty() && !existeErro) {
                Toast.makeText(
                    requireContext(),
                    "Por favor, insira o nome do responsável!",
                    Toast.LENGTH_SHORT
                ).show()
                existeErro = true
            }
            if (telefone.text.isNullOrEmpty() && !existeErro) {
                Toast.makeText(
                    requireContext(),
                    "Por favor, insira o telefone do responsável!",
                    Toast.LENGTH_SHORT
                ).show()
                existeErro = true
            }
            if (endereco.text.isNullOrEmpty() && !existeErro) {
                Toast.makeText(
                    requireContext(),
                    "Por favor, insira o endereço da criança!",
                    Toast.LENGTH_SHORT
                ).show()
                existeErro = true
            }
            if (!existeErro) {
                val database = AppDatabase.getDatabase(requireContext())
                database.daoFunctions().updateCrianca(
                    Crianca(
                        idCrianca,
                        nome.text.toString(),
                        data.dayOfMonth.toString() + "/" + data.month.toString() + "/" + data.year.toString(),
                        nomeResponsavel.text.toString(),
                        telefone.text.toString(),
                        endereco.text.toString(),
                        observacoes.text.toString()
                    )
                )
                Toast.makeText(requireContext(), "Os dados da criança foram atualizados!", Toast.LENGTH_SHORT).show()
                if (dialog?.isShowing == true) {
                    dialog?.dismiss()
                }
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (!imm.isActive) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        inflater = LayoutInflater.from(requireContext())
        view = inflater.inflate(R.layout.visualizarcriancadialog, null)
        nome = view.findViewById(R.id.visualizarCriancaNome)
        nomeResponsavel = view.findViewById(R.id.visualizarCriancaNomeResponsavel)
        telefone = view.findViewById(R.id.visualizarCriancaTelefoneResponsavel)
        data = view.findViewById(R.id.visualizarCriancaDataNascimento)
        observacoes = view.findViewById(R.id.visualizarCriancaObservacoes)
        endereco = view.findViewById(R.id.visualizarCriancaEndereco)
        salvar = view.findViewById(R.id.visualizarCriancaCadastrar)
        botaosair = view.findViewById(R.id.visualizarCriancaSair)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
        return builder.create()
    }
    private fun configuraBotaoSair(button: ImageButton){
        button.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if(!imm.isActive){
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}