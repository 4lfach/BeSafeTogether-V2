package com.selftutor.besafetogether.data.model.contact

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Query("SELECT * FROM contacts order by id ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT EXISTS(SELECT * FROM contacts WHERE phone = :phone)")
    suspend fun isPhoneExists(phone:String): Boolean
}