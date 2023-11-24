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
import android.widget.CalendarView
import android.widget.DatePicker
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


class AddEncontroDialogFragment : DialogFragment() {

    private lateinit var inflater : LayoutInflater
    private lateinit var view : View
    private lateinit var titulo : TextInputEditText
    private lateinit var dataEncontro : DatePicker
    private lateinit var observacoes : TextInputEditText
    private lateinit var cadastrar : MaterialButton
    private lateinit var botaosair : ImageButton
    private var dataAlterada : Boolean = false
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        configuraBotaoSair(botaosair)
        cadastrar.setOnClickListener{
            var existeErro = false
            if(titulo.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Por favor, insira um título!", Toast.LENGTH_SHORT).show()
                existeErro = true
            }
            if(!dataAlterada && !existeErro){
                dataEncontro.updateDate(
                    LocalDate.now().year,
                    LocalDate.now().monthValue,
                    LocalDate.now().dayOfMonth)
            }
            if(!existeErro){
                val db = AppDatabase.getDatabase(requireContext())
                db.daoFunctions().insertEncontro(Encontro(0,titulo.text.toString(),dataEncontro.dayOfMonth.toString()+"/"+dataEncontro.month.toString()+"/"+dataEncontro.year.toString(),observacoes.toString(),0,0,0,0,0))
                Toast.makeText(requireContext(), "Encontro criado!", Toast.LENGTH_SHORT).show()
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
        view = inflater.inflate(R.layout.addencontrodialog, null)
        titulo = view.findViewById(R.id.encontroTitulo)
        dataEncontro = view.findViewById(R.id.encontroData)
        observacoes= view.findViewById(R.id.encontroObservacoes)
        cadastrar = view.findViewById(R.id.encontroCadastrar)
        botaosair = view.findViewById(R.id.encontroVoltar)
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