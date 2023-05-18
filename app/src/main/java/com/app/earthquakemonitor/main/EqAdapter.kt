package com.app.earthquakemonitor.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.earthquakemonitor.Earthquake
import com.app.earthquakemonitor.databinding.EqListItemBinding

class EqAdapter: ListAdapter<Earthquake, EqAdapter.EqViewHolder>(DiffCallback) {

    lateinit var onItemClickListener: (Earthquake) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqViewHolder {
        val binding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))
        return EqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EqViewHolder, position: Int) {
        val earthquake = getItem(position)
        holder.bind(earthquake)
    }

    inner class EqViewHolder(private val binding: EqListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(earthQuake: Earthquake){
            val formattedMag = String.format("%.1f", earthQuake.magnitude)
            binding.eqMagnitudeText.text = formattedMag
            binding.eqPlaceText.text = earthQuake.place
            binding.root.setOnClickListener {
                if(::onItemClickListener.isInitialized){
                    onItemClickListener(earthQuake)
                } else {
                    Log.e("TAG", "OnItemClickListener was not initialized")
                }
            }
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }
    }
}