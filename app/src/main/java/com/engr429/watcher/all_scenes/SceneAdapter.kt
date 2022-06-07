package com.engr429.watcher.all_scenes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.engr429.watcher.Constants
import com.engr429.watcher.databinding.CellSceneBinding

class SceneAdapter(private val keys: List<String>): RecyclerView.Adapter<SceneViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SceneViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CellSceneBinding.inflate(inflater, parent, false)
        return SceneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SceneViewHolder, position: Int) {
        holder.bind(keys[position])
    }

    override fun getItemCount() = keys.size
}

class SceneViewHolder(private val binding: CellSceneBinding): RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    fun bind(imageKey: String) {
        Glide.with(context).load("${Constants.serverUrl}/$imageKey").into(binding.imgScene)
    }
}