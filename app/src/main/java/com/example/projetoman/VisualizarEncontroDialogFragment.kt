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
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class VisualizarEncontroDialogFragment(val idEncontro : Int) : DialogFragment() {

    private lateinit var inflater : LayoutInflater
    private lateinit var view : View
    private lateinit var titulo : TextView
    private lateinit var observacoes : TextView
    private lateinit var data : TextView
    private lateinit var fazerChamada : MaterialButton
    private lateinit var botaosair : ImageButton
    private lateinit var azul : MaterialButton
    private lateinit var vermelho : MaterialButton
    private lateinit var verde : MaterialButton
    private lateinit var laranja : MaterialButton
    private lateinit var amarelo : MaterialButton

    private lateinit var mazul : MaterialButton
    private lateinit var mvermelho : MaterialButton
    private lateinit var mverde : MaterialButton
    private lateinit var mlaranja : MaterialButton
    private lateinit var mamarelo : MaterialButton

    private lateinit var pazul : MaterialButton
    private lateinit var pvermelho : MaterialButton
    private lateinit var pverde : MaterialButton
    private lateinit var plaranja : MaterialButton
    private lateinit var pamarelo : MaterialButton

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val db = AppDatabase.getDatabase(requireContext())
        val encontro: Encontro = db.daoFunctions().findEncontroByID(idEncontro)
        titulo.text = "Encontro: ${encontro.titulo}"
        data.text = "Data: ${encontro.dataEncontro}"
        observacoes.text = "Observações: ${encontro.observacoes}"
        azul.text = encontro.equipeAzul.toString()
        vermelho.text = encontro.equipeVermelha.toString()
        verde.text = encontro.equipeVerde.toString()
        laranja.text = encontro.equipeLaranja.toString()
        amarelo.text = encontro.equipeAmarela.toString()
        mazul.setOnClickListener {
            diminuiValor("azul")
            atualizaBanco()
        }
        mvermelho.setOnClickListener {
            diminuiValor("vermelho")
            atualizaBanco()
        }
        mverde.setOnClickListener {
            diminuiValor("verde")
            atualizaBanco()
        }
        mlaranja.setOnClickListener {
            diminuiValor("laranja")
            atualizaBanco()
        }
        mamarelo.setOnClickListener {
            diminuiValor("amarelo")
            atualizaBanco()
        }
        pazul.setOnClickListener {
            aumentaValor("azul")
            atualizaBanco()
        }
        pvermelho.setOnClickListener {
            aumentaValor("vermelho")
            atualizaBanco()
        }
        pverde.setOnClickListener {
            aumentaValor("verde")
            atualizaBanco()
        }
        plaranja.setOnClickListener {
            aumentaValor("laranja")
            atualizaBanco()
        }
        pamarelo.setOnClickListener {
            aumentaValor("amarelo")
            atualizaBanco()
        }
        configuraBotaoSair(botaosair)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        inflater = LayoutInflater.from(requireContext())
        view = inflater.inflate(R.layout.visualizarencontrodialog, null)
        titulo = view.findViewById(R.id.visualizarEncontroEncontro)
        data = view.findViewById(R.id.visualizarEncontroData)
        observacoes= view.findViewById(R.id.visualizarEncontroObservacoes)
        fazerChamada = view.findViewById(R.id.visualizarEncontroFazerChamada)
        botaosair = view.findViewById(R.id.visualizarEncontroSair)
        azul = view.findViewById(R.id.visualizarEncontroPontuacaoAzul)
        vermelho = view.findViewById(R.id.visualizarEncontroPontuacaoVermelha)
        verde = view.findViewById(R.id.visualizarEncontroPontuacaoVerde)
        laranja = view.findViewById(R.id.visualizarEncontroPontuacaoLaranja)
        amarelo = view.findViewById(R.id.visualizarEncontroPontuacaoAmarela)

        pazul = view.findViewById(R.id.visualizarEncontroAumentarPontuacaoAzul)
        pvermelho = view.findViewById(R.id.visualizarEncontroAumentarPontuacaoVermelho)
        pverde = view.findViewById(R.id.visualizarEncontroAumentarPontuacaoVerde)
        plaranja = view.findViewById(R.id.visualizarEncontroAumentarPontuacaoLaranja)
        pamarelo = view.findViewById(R.id.visualizarEncontroAumentarPontuacaoAmarelo)

        mazul = view.findViewById(R.id.visualizarEncontroDiminuirPontuacaoAzul)
        mvermelho = view.findViewById(R.id.visualizarEncontroDiminuirPontuacaoVermelho)
        mverde = view.findViewById(R.id.visualizarEncontroDiminuirPontuacaoVerde)
        mlaranja = view.findViewById(R.id.visualizarEncontroDiminuirPontuacaoLaranja)
        mamarelo = view.findViewById(R.id.visualizarEncontroDiminuirPontuacaoAmarelo)

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
    private fun aumentaValor(equipe : String){
        when(equipe){
            "azul"->azul.setText((azul.text.toString().toInt()+1).toString())
            "vermelho"->vermelho.setText((vermelho.text.toString().toInt()+1).toString())
            "verde"->verde.setText((verde.text.toString().toInt()+1).toString())
            "amarelo"->amarelo.setText((amarelo.text.toString().toInt()+1).toString())
            "laranja"->laranja.setText((laranja.text.toString().toInt()+1).toString())
        }
    }
    private fun diminuiValor(equipe: String) {
        val valorAtual: Int
        val novoValor: Int
        when (equipe) {
            "azul" -> {
                valorAtual = azul.text.toString().toInt()
                novoValor = valorAtual - 1
                if (novoValor >= 0) {
                    azul.text = novoValor.toString()
                }
            }
            "vermelho" -> {
                valorAtual = vermelho.text.toString().toInt()
                novoValor = valorAtual - 1
                if (novoValor >= 0) {
                    vermelho.text = novoValor.toString()
                }
            }
            "verde" -> {
                valorAtual = verde.text.toString().toInt()
                novoValor = valorAtual - 1
                if (novoValor >= 0) {
                    verde.text = novoValor.toString()
                }
            }
            "amarelo" -> {
                valorAtual = amarelo.text.toString().toInt()
                novoValor = valorAtual - 1
                if (novoValor >= 0) {
                    amarelo.text = novoValor.toString()
                }
            }
            "laranja" -> {
                valorAtual = laranja.text.toString().toInt()
                novoValor = valorAtual - 1
                if (novoValor >= 0) {
                    laranja.text = novoValor.toString()
                }
            }
        }
    }
    private fun atualizaBanco(){
        val db = AppDatabase.getDatabase(requireContext())
        val encontro : Encontro = db.daoFunctions().findEncontroByID(idEncontro)
        db.daoFunctions().updateEncontro(Encontro(encontro.id,encontro.titulo,encontro.dataEncontro,encontro.observacoes, azul.text.toString().toInt(),vermelho.text.toString().toInt(),verde.text.toString().toInt(),laranja.text.toString().toInt(),amarelo.text.toString().toInt()))
    }
}