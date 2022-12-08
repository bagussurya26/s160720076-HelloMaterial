package com.bumi.s160720076_hellomaterial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_add_playlist.*
import kotlinx.android.synthetic.main.card_playlist.*
import kotlinx.android.synthetic.main.card_playlist.view.*

class AddPlaylistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_playlist)

        btnCancel.setOnClickListener {
            finish()
        }

        btnSubmit.setOnClickListener {
            val q = Volley.newRequestQueue(this)
            val url = "http://10.0.2.2:80/music/insert_playlist.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener {
                    Log.d("cekparams", it) },
                Response.ErrorListener {
                    Log.d("cekparams", it.message.toString())
                }
            )
            {
                override fun getParams(): MutableMap<String, String>? {
                    var params = HashMap<String, String>()
                    params["title"] = txtInputTitle.text.toString()
                    params["subtitle"] = txtInputSubtitle.text.toString()
                    params["description"] = txtInputDescription.text.toString()
                    params["image_url"] = txtInputUrl.text.toString()
                    return params
                }
            }
            q.add(stringRequest)
            finish()
        }
    }
}