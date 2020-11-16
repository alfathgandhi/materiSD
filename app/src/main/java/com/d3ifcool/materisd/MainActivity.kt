package com.d3ifcool.materisd

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.auth.FirebaseAuth
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import java.io.File

class MainActivity : AppCompatActivity() {


    private var session: Session? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        session = Session(this)

        val pdf = intent.extras?.getString("pdf")
        FileLoader.with(this).load(pdf)
                .fromDirectory("PDFFile", FileLoader.DIR_INTERNAL)
                .asFile(object : FileRequestListener<File> {
                    override fun onLoad(request: FileLoadRequest?, response: FileResponse<File>?) {
                        val pdfFile = response!!.body
                        findViewById<PDFView>(R.id.pdf_view)
                                .fromFile(pdfFile)
                                .defaultPage(0)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .onDraw { canvas, pageWidth, pageHeight, displayedPage ->

                                }
                                .onDrawAll { canvas, pageWidth, pageHeight, displayedPage ->

                                }

                                .onPageChange { page, pageCount ->

                                }.onPageError { page, t ->
                                    Toast.makeText(this@MainActivity, "Error while opening page " + page, Toast.LENGTH_SHORT)
                                    Log.d("ERROR", "" + t.localizedMessage)
                                }
                                .onTap { false }
                                .onRender { nbPages, pageWidth, pageHeight ->
                                    findViewById<PDFView>(R.id.pdf_view)
                                            .fitToWidth()
                                }
                                .enableAnnotationRendering(true)
                                .invalidPageColor(Color.RED)
                                .load()
                    }


                    override fun onError(request: FileLoadRequest?, t: Throwable?) {
                        Toast.makeText(this@MainActivity, " " + t!!.message, Toast.LENGTH_LONG).show()
                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val men = MenuInflater(this)
        men.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bt_logout->{
                session!!.setLoggedin(false)
                FirebaseAuth.getInstance().signOut()
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.quiz ->{
                startActivity(Intent(this,QuizActivity::class.java))
            }
        }

        return true

    }

}
