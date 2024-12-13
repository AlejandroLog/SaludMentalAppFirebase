package com.calseto.firebasedefinitivo.datosemociones

import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.style.LineBreak.Companion.Paragraph
import com.calseto.firebasedefinitivo.R
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.properties.TextAlignment
import java.io.File

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var textViewIdEmpleado: TextView
    private lateinit var textViewNombreEmpleado: TextView
    private lateinit var textViewEdadEmpleado: TextView
    private lateinit var textViewSalarioEmpleado: TextView
    private lateinit var btnDescargarPdf: ImageView

    private fun initView() {
        textViewIdEmpleado = findViewById(R.id.txtId)
        textViewNombreEmpleado = findViewById(R.id.txtNombre)
        textViewEdadEmpleado = findViewById(R.id.txtEdad)
        textViewSalarioEmpleado = findViewById(R.id.txtSalario)

        btnDescargarPdf = findViewById(R.id.btnPdf)


    }

    private fun setValuesToViews() {
        textViewIdEmpleado.text = intent.getStringExtra("idEmpleado")
        textViewNombreEmpleado.text = intent.getStringExtra("nombreEmpleado")
        textViewEdadEmpleado.text = intent.getStringExtra("edadEmpleado")
        textViewSalarioEmpleado.text = intent.getStringExtra("salarioEmpleado")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_employee_details)

        val btnExitDetalles: Button = findViewById(R.id.btnExitDetalles)

        initView()
        setValuesToViews()

        btnExitDetalles.setOnClickListener {
            finish()
        }

        btnDescargarPdf.setOnClickListener {
            generarYGuardarPdf()
        }


    }

    private fun generarYGuardarPdf() {
        val pdfName = "DetallesEmociones.pdf"
        val downloadsFolder =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val pdfFile = File(downloadsFolder, pdfName)

        try {
            val writer = PdfWriter(pdfFile)
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc)

            val titulo = com.itextpdf.layout.element.Paragraph("Detalles en pdf de mis emociones")
                .setFontSize(20f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
            document.add(titulo)

            document.add(
                com.itextpdf.layout.element.Paragraph("\nID: ${textViewIdEmpleado.text}")
                    .setFontSize(14f))
            document.add(
                com.itextpdf.layout.element.Paragraph("Horario del dia: ${textViewNombreEmpleado.text}").setFontSize(14f))
            document.add(
                com.itextpdf.layout.element.Paragraph("Estado de animo: ${textViewEdadEmpleado.text}").setFontSize(14f))
            document.add(
                com.itextpdf.layout.element.Paragraph("Descripcion: ${textViewSalarioEmpleado.text}").setFontSize(14f))


            document.close()

            Toast.makeText(this, "PDF descargado", Toast.LENGTH_LONG).show()


        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}


