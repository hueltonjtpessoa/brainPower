package com.aegis.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.aegis.models.AegisPlaceModel

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "AegisDatabase"
        private const val TABLE_HAPPY_PLACE = "AegisTable"

        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_LOCATION = "location"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_HAPPY_PLACE_TABLE = ("CREATE TABLE " + TABLE_HAPPY_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT)")
        db?.execSQL(CREATE_HAPPY_PLACE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_HAPPY_PLACE")
        onCreate(db)
    }

    fun addAegis(Aegis: AegisPlaceModel): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, Aegis.title)
        contentValues.put(KEY_IMAGE, Aegis.image)
        contentValues.put(
            KEY_DESCRIPTION,
            Aegis.description
        )
        contentValues.put(KEY_DATE, Aegis.date)
        contentValues.put(KEY_LOCATION, Aegis.location)
        contentValues.put(KEY_LATITUDE, Aegis.latitude)
        contentValues.put(KEY_LONGITUDE, Aegis.longitude)

        val result = db.insert(TABLE_HAPPY_PLACE, null, contentValues)

        db.close()
        return result
    }

    fun getAegisList(): ArrayList<AegisPlaceModel> {

        val AegisList: ArrayList<AegisPlaceModel> = ArrayList()

        val selectQuery = "SELECT  * FROM $TABLE_HAPPY_PLACE"

        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val place = AegisPlaceModel(
                            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                            cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE))
                    )
                    AegisList.add(place)

                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return AegisList
    }

    fun updateAegis(Aegis: AegisPlaceModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, Aegis.title)
        contentValues.put(KEY_IMAGE, Aegis.image)
        contentValues.put(
            KEY_DESCRIPTION,
            Aegis.description
        )
        contentValues.put(KEY_DATE, Aegis.date)
        contentValues.put(KEY_LOCATION, Aegis.location)
        contentValues.put(KEY_LATITUDE, Aegis.latitude)
        contentValues.put(KEY_LONGITUDE, Aegis.longitude)

        val success =
            db.update(TABLE_HAPPY_PLACE, contentValues, KEY_ID + "=" + Aegis.id, null)

        db.close()
        return success
    }

    fun deleteAegis(Aegis: AegisPlaceModel): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_HAPPY_PLACE, KEY_ID + "=" + Aegis.id, null)
        db.close()
        return success
    }
}
