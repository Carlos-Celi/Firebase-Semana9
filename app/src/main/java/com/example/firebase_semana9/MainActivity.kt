package com.example.firebase_semana9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db=FirebaseFirestore.getInstance()
        val tvCurso: TextView = findViewById(R.id.tvCurso)
        val tvNota: TextView = findViewById(R.id.tvNote)
        db.collection("courses")
            .addSnapshotListener { snapshots, e->
                if(e!=null){
                        Snackbar
                            .make(
                                findViewById(android.R.id.content)
                                ,"Ocurrio un errro al consultar la coleccion"
                                ,Snackbar.LENGTH_LONG

                            ).show()
                    return@addSnapshotListener
                }
                for(dc in snapshots!!.documentChanges){
                    when(dc.type){
                        DocumentChange.Type.ADDED->{
                            Snackbar
                                .make(
                                    findViewById(android.R.id.content)
                                    ,"Se arrego el documento"
                                    ,Snackbar.LENGTH_LONG

                                ).show()
                            tvCurso.text=dc.document.data["description"].toString()
                            tvNota.text=dc.document.data["score"].toString()

                        }
                        DocumentChange.Type.MODIFIED->{
                            tvCurso.text=dc.document.data["description"].toString()
                            tvNota.text=dc.document.data["score"].toString()

                        }
                        DocumentChange.Type.REMOVED->{
                            Snackbar
                            .make(
                                findViewById(android.R.id.content)
                                ,"Se removio el documento"
                                ,Snackbar.LENGTH_LONG

                            ).show()
                        }
                        else->{
                            Snackbar
                                .make(
                                    findViewById(android.R.id.content)
                                    ,"Error al consultar la conexión"
                                    ,Snackbar.LENGTH_LONG

                                ).show()

                        }
                    }
                }
            }
    }
}