package com.selftutor.besafetogether.screens.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selftutor.besafetogether.databinding.StopWordItemBinding
import com.selftutor.besafetogether.data.model.stopword.StopWord


//todo Implement DiffUtils for better performance
class StopWordsAdapter(
	private val stopWordsActionListener: StopWordsActionListener
) : RecyclerView.Adapter<StopWordsAdapter.ViewHolder>(){

	var stopWords : ArrayList<StopWord> = ArrayList()
		set(value){
			field = value
			notifyDataSetChanged()
		}

	class ViewHolder(val binding: StopWordItemBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater =LayoutInflater.from(parent.context)
		val binding = StopWordItemBinding.inflate(inflater, parent, false)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		with(holder.binding){
			val stopWord = stopWords[position]
			wordTextView.text = stopWord.word
			timeTextView.text = stopWord.timeStamp

			deleteImageView.setOnClickListener{
				stopWordsActionListener.onStopWordDelete(stopWord)
			}
		}
	}

	override fun getItemCount(): Int = stopWords.size

	fun updateList(newList : List<StopWord>){
		stopWords.clear()
		stopWords.addAll(newList)

		notifyDataSetChanged()
	}
}

interface StopWordsActionListener {
	// creating a method for click
	// action on delete image view.
	fun onStopWordDelete(stopWord: StopWord)

	fun onStopWordAdd(stopWord: StopWord)
}
