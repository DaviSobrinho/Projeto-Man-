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
import android.widget.DatePicker.OnDateChangedListener
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.room.TypeConverter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date


class AddCriancaDialogFragment : DialogFragment() {

    private lateinit var inflater : LayoutInflater
    private lateinit var view : View
    private lateinit var nome : TextInputEditText
    private lateinit var nomeResponsavel : TextInputEditText
    private lateinit var dataNascimento : DatePicker
    private lateinit var telefone : TextInputEditText
    private lateinit var endereco : TextInputEditText
    private lateinit var observacoes : TextInputEditText
    private lateinit var cadastrar : MaterialButton
    private lateinit var botaosair : ImageButton
    private var dataAlterada : Boolean = false
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dataNascimento.setOnDateChangedListener{ view, year, monthOfYear, dayOfMonth -> dataAlterada = true }

        configuraBotaoSair(botaosair)
        cadastrar.setOnClickListener{
            var existeErro = false
            if(nome.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Por favor, insira o nome da crianca!", Toast.LENGTH_SHORT).show()
                existeErro = true
            }
            if(nomeResponsavel.text.isNullOrEmpty() && !existeErro){
                Toast.makeText(requireContext(), "Por favor, insira o nome do responsável!", Toast.LENGTH_SHORT).show()
                existeErro = true
            }
            if(telefone.text.isNullOrEmpty() && !existeErro){
                Toast.makeText(requireContext(), "Por favor, insira o telefone do responsável!", Toast.LENGTH_SHORT).show()
                existeErro = true
            }
            if(endereco.text.isNullOrEmpty() && !existeErro){
                Toast.makeText(requireContext(), "Por favor, insira o endereço da criança!", Toast.LENGTH_SHORT).show()
                existeErro = true
            }
            if(!dataAlterada && !existeErro){
                dataNascimento.updateDate(LocalDate.now().year,LocalDate.now().monthValue,LocalDate.now().dayOfMonth)
            }
            if(!existeErro){
                val db = AppDatabase.getDatabase(requireContext())
                db.daoFunctions().insertCrianca(Crianca(0,nome.text.toString(),dataNascimento.dayOfMonth.toString()+"/"+dataNascimento.month.toString()+"/"+dataNascimento.year.toString(),nomeResponsavel.text.toString(),telefone.text.toString(),endereco.text.toString(),observacoes.text.toString()))
                Toast.makeText(requireContext(), "Criança cadastrada!", Toast.LENGTH_SHORT).show()
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
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        inflater = LayoutInflater.from(requireContext())
        view = inflater.inflate(R.layout.addcriancadialog, null)
        nome = view.findViewById(R.id.criancaNome)
        nomeResponsavel = view.findViewById(R.id.criancaNomeResponsavel)
        dataNascimento= view.findViewById(R.id.criancaDataNascimento)
        telefone = view.findViewById(R.id.criancaTelefoneResponsavel)
        observacoes = view.findViewById(R.id.criancaObservacoes)
        endereco = view.findViewById(R.id.criancaEndereco)
        cadastrar = view.findViewById(R.id.criancaCadastrar)
        botaosair = view.findViewById(R.id.criancaSair)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
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
    fun DatePicker.getDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return calendar.time
    }
}