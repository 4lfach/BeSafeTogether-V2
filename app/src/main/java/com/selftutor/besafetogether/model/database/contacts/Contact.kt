package com.selftutor.besafetogether.model.database.contacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
class Contact(
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "gpsOn") val gpsOn: Boolean)
{
        @PrimaryKey(autoGenerate = true) var id = 0

        companion object{
            const val ARG_NAME = "name"
            const val ARG_PHONE = "phone"
            const val ARG_GPS_ON = "gpsOn"
        }
}