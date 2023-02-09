package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.core.net.toUri
import com.openclassrooms.realestatemanager.data.database.HouseRoomDatabase
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.runBlocking

class PropertyProvider : ContentProvider(){

    companion object {
        // The authority of this content provider
        const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"

        // The URI for the house_database table
        val URI_MENU = Uri.parse("content://$AUTHORITY/house")!!

        // The match code for some items in the house_database table
        private const val CODE_HOUSE_ITEM = 1
        private const val CODE_HOUSE_DIR = 2

        // The URI matcher
        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY,"house/*", CODE_HOUSE_ITEM)
            MATCHER.addURI(AUTHORITY,"house", CODE_HOUSE_DIR)
        }


    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val id = uri.lastPathSegment
        if (id != null && context != null) {
            val database = HouseRoomDatabase.getDatabase(context!!)
            return when (MATCHER.match(uri)) {
                CODE_HOUSE_ITEM -> database.houseDao().getPropertyWithCursor(id)
                CODE_HOUSE_DIR -> database.houseDao().getAllPropertiesWithCursor()
                else -> throw IllegalArgumentException("Query doesn't exist $uri")
            }
        }
        throw Exception("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String? = when(MATCHER.match(uri)) {
        CODE_HOUSE_DIR -> "vnd.android.cursor.dir/$AUTHORITY.house"
        CODE_HOUSE_ITEM -> "vnd.android.cursor.item/$AUTHORITY.house"
        else -> throw IllegalArgumentException("Unknown URI: $uri")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (context != null && values != null) {
            val database = HouseRoomDatabase.getDatabase(context!!)
            when (MATCHER.match(uri)) {
                CODE_HOUSE_ITEM -> {
                    val property = propertyFromContentValues(values)
                    runBlocking {
                        database.houseDao().addHouse(property)
                        context!!.contentResolver.notifyChange(uri, null)
                    }
                    return "$uri/${property.houseId}".toUri()
                }
                CODE_HOUSE_DIR -> throw IllegalArgumentException("Invalid URI cannot insert without an ID $uri")
                else -> throw IllegalArgumentException("Unknown URI $uri")
            }
        }
        throw  Exception("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw  IllegalArgumentException("Impossible to delete data")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw  IllegalArgumentException("Impossible to update $uri")
    }
}