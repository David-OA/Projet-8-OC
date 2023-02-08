package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import androidx.core.net.toUri
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.data.database.HouseRoomDatabase
import com.openclassrooms.realestatemanager.provider.PropertyProvider
import com.openclassrooms.realestatemanager.utils.idGeneratedProperty
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContentProviderTest {

    private lateinit var db: HouseRoomDatabase
    private lateinit var contentResolver: ContentResolver

    private val propertyId = idGeneratedProperty
    private val uriProperty = "${PropertyProvider.URI_MENU}/$propertyId".toUri()

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, HouseRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        contentResolver = context.contentResolver
    }

    @Test
    fun getPropertyWhenNoItemInserted() {
        val uriProperty = "${PropertyProvider.URI_MENU}/$propertyId".toUri()
        val cursor = contentResolver.query(uriProperty, null,null,null,null)
        assertNotNull(cursor)
        assertEquals(0, cursor?.count)
        cursor?.close()
    }

    @Test
    fun insertAndGetNewProperty() {
        contentResolver.insert(uriProperty, generatePropertyAndDataContentValue(propertyId))
        val cursorProperty = contentResolver.query(uriProperty, null,null,null,null)
        assertNotNull(cursorProperty)
        assertEquals(1, cursorProperty?.count)
        assertTrue(cursorProperty!!.moveToFirst())
        assertEquals(propertyId, cursorProperty.getString(cursorProperty.getColumnIndexOrThrow("house_id")))
    }
}