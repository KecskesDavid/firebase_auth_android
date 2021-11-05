package com.example.firesbaseauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var logoutBtn: Button
    private lateinit var updatePass: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            auth.currentUser!!.getIdToken(true)
                .addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result!!.token
                        // Send token to your backend via HTTPS
                        Toast.makeText(this, idToken, Toast.LENGTH_LONG).show()
                        Log.e("token", idToken)
                    } else {
                        // Handle error -> task.getException();
                    }
                })
        }

        setContentView(R.layout.activity_main)

        logoutBtn = findViewById(R.id.logout_btn)
        updatePass = findViewById(R.id.update_pass_btn)

        logoutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}