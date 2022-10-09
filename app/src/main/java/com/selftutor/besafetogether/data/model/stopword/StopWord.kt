package com.selftutor.besafetogether.data.model.stopword

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stopWords")
class StopWord(
	@ColumnInfo(name ="timeStamp") val timeStamp: String,
	@ColumnInfo(name = "word") val word: String) {
	@PrimaryKey(autoGenerate = true) var id = 0

	companion object{
		const val ARG_WORD = "word"
		const val ARG_TIMESTAMP = "timeStamp"
	}
}