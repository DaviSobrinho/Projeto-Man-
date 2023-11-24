package com.example.projetoman

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniciaFragment(AddCriancaDialogFragment(), findViewById(R.id.cadastrarNovaCrianca))
        iniciaFragment(AddEncontroDialogFragment(), findViewById(R.id.cadastrarNovoEncontro))
        configuraRecyclerViewEncontros()
    }
    private fun iniciaFragment(dialogFragment: DialogFragment,materialButton : MaterialButton){
        materialButton.setOnClickListener(){
            materialButton.startAnimation(AnimationUtils.loadAnimation(applicationContext,androidx.appcompat.R.anim.abc_tooltip_enter))
            if(this.supportFragmentManager.fragments.isEmpty()){
                dialogFragment.show(supportFragmentManager, "CustomFragment")
            }
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        configuraRecyclerViewEncontros()
        configuraRecyclerViewCriancas()
    }
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
    fun configuraRecyclerViewEncontros(){
        val adapter = EncontrosAdapter(context = this)
        val db = AppDatabase.getDatabase(this)
        adapter.refresh(db.daoFunctions().getAllEncontro())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerEncontros )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    fun configuraRecyclerViewCriancas(){
        val adapter = CriancasAdapter(context = this)
        val db = AppDatabase.getDatabase(this)
        adapter.refresh(db.daoFunctions().getAllCrianca())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerCriancas )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}