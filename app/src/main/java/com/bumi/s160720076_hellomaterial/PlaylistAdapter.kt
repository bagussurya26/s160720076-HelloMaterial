package com.bumi.s160720076_hellomaterial

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_playlist.view.*


class PlaylistAdapter(var playlists:ArrayList<Playlist>)
    : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>()  {
    class PlaylistViewHolder(val view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.card_playlist, parent,false)
        return PlaylistViewHolder(view)
    }
    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = playlists[position]

        with(holder.view){
            val url = playlist.image_url
            Picasso.get().load(url).into(imgPlaylist)
            txtTitle.text = playlist.title
            txtSubtitle.text = playlist.subtitle
            txtDescription.text = playlist.description
            btnLikes.text = "${playlist.num_likes} LIKES"

            btnLikes.setOnClickListener {
                val q = Volley.newRequestQueue(context)
                val url = "http://10.0.2.2/music/set_likes.php"
                val stringRequest = object : StringRequest
                    (com.android.volley.Request.Method.POST,
                    url,
                    Response.Listener {
                        Log.d("cekparams", it)
                        // make sure to change num_likes to var in Playlist data class
                        playlists[position].num_likes++
                        var newlikes = playlists[position].num_likes
                        btnLikes.text = "$newlikes LIKES"
                    },
                    Response.ErrorListener {
                        Log.d("cekparams", it.message.toString())
                    }
                )
                {
                    override fun getParams(): MutableMap<String, String> {
                        return hashMapOf("id" to playlist.id.toString())
                    }
                }
                q.add(stringRequest)
            }
        }
    }
    override fun getItemCount(): Int {
        return playlists.size
    }
}