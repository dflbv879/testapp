package com.example.testapp.data.entities.user

import androidx.room.*

@Entity(tableName = "user")
data class UserResponseItem(
    @Embedded
    val address: Address,
    @Embedded
    val company: Company,
    val email: String,
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val id: Int,
    @ColumnInfo(name = "user_resp_name")
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)