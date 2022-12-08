package com.bumi.s160720076_hellomaterial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_playlist.*
import org.json.JSONObject


class PlaylistFragment : Fragment() {

    var playlists:ArrayList<Playlist> = ArrayList()

    fun updateList() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.playlistView)
        recyclerView?.layoutManager = lm
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = PlaylistAdapter(playlists)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2:80/music/get_playlist.php"
        var stringRequest = StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK"){
                    val data = obj.getJSONArray("data")

                    for(i in 0 until data.length()){
                        val playObj = data.getJSONObject(i)
                        val playlist = Playlist(
                            playObj.getInt("id"),
                            playObj.getString("title"),
                            playObj.getString("subtitle"),
                            playObj.getString("description"),
                            playObj.getString("image_url"),
                            playObj.getInt("num_likes")
                        )
                        playlists.add(playlist)
                    }

                    updateList()

                    Log.d("cekisiarray", playlists.toString())
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabNew.setOnClickListener {
            startActivity(Intent(activity, AddPlaylistActivity::class.java))
        }
    }


}