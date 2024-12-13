package com.calseto.firebasedefinitivo.login


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.menu.MainActivity
import com.calseto.firebasedefinitivo.usuarios.NewUsuarioActivity
import com.calseto.firebasedefinitivo.usuarios.RestableserPassActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Usa tu ID de cliente web de Firebase
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etContraseña: EditText = findViewById(R.id.etContraseña)
        val btnEntrar: Button = findViewById(R.id.btnIngresar)
        val btnGoogle: ImageView = findViewById(R.id.btnGoogle)
        val btnRecuperarContraseña: TextView = findViewById(R.id.btnRecuperarContraseña)
        val btnAnonimo: ImageView = findViewById(R.id.btnAnonimo)
        val btnNuevoUsuario: Button = findViewById(R.id.btnNuevoUsuario)

        btnNuevoUsuario.setOnClickListener {
            val intent = Intent(this, NewUsuarioActivity::class.java)
            startActivity(intent)
        }

        btnAnonimo.setOnClickListener {
            signInAnonymously()
        }


        btnRecuperarContraseña.setOnClickListener{
            val inten = Intent(this, RestableserPassActivity::class.java)
            startActivity(inten)
        }


        btnGoogle.setOnClickListener {
            signInWithGoogle()
        }

        btnEntrar.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etContraseña.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val verifica = user?.isEmailVerified
                            if (verifica == true) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "El email no está verificado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Ingrese los datos requeridos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun signInWithGoogle() {
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.w("GoogleSignIn", "Google sign in failed", e)
                Toast.makeText(this, "Inicio de sesión fallido: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        guardarUsuarioEnBaseDeDatos(user) // Llama a esta función aquí
                        updateUI(user)
                    }
                } else {
                    Log.w("GoogleSignIn", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Autenticación fallida.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun guardarUsuarioEnBaseDeDatos(user: FirebaseUser) {
        val database = FirebaseDatabase.getInstance().reference
        val userId = user.uid
        val email = user.email

        // Verificar si el proveedor es Google
        val isGoogleUser = user.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }

        if (isGoogleUser && email != null) {
            // Guardar solo el email en el nodo específico para usuarios de Google
            database.child("usuarios_google").child(userId).setValue(mapOf("email" to email))
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Database", "Usuario de Google guardado correctamente en 'usuarios_google'")
                    } else {
                        Log.e("Database", "Error al guardar usuario de Google", task.exception)
                    }
                }
        } else {
            // Nodo para usuarios normales
            val userData = mapOf(
                "nombre" to (user.displayName ?: "Sin nombre"),
                "email" to email
            )
            database.child("usuarios").child(userId).setValue(userData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Database", "Usuario guardado correctamente en 'usuarios'")
                    } else {
                        Log.e("Database", "Error al guardar usuario", task.exception)
                    }
                }
        }
    }


    private fun signInAnonymously() {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión anónimo exitoso
                    val user = auth.currentUser
                    Toast.makeText(this, "Sesión anónima iniciada", Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else {
                    // Error en el inicio de sesión anónimo
                    Toast.makeText(this, "Error al iniciar sesión de forma anónima", Toast.LENGTH_SHORT).show()
                    Log.e("AuthAnonimo", "Error: ${task.exception?.message}")
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

