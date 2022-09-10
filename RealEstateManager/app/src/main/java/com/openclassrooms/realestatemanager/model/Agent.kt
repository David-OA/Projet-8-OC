package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "agent")
data class Agent (@PrimaryKey @ColumnInfo(name = "agent_id") var agentId: String,
                  @ColumnInfo(name = "first_name") var firstName: String,
                  @ColumnInfo(name = "last_name") var lastName: String,
                  @ColumnInfo(name = "email") var email: String,
                  @ColumnInfo(name = "phone_number") var phoneNumber: String,
                  @ColumnInfo(name = "creation_date") var creationDate: String,
):Serializable