package com.mobiotics.demo.mobioticsapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mobiotics.demo.mobioticsapp.models.RetroVideo


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val TABLE_NAME = "videos"

    val COLUMN_ID = "id"
    val COLUMN_DESCRIPTION = "desc"
    val COLUMN_THUMB = "thumb"
    val COLUMN_TITLE = "title"
    val COLUMN_URL = "url"
    val COLUMN_STATUS = "status"
    val COLUMN_RESUME_FROM = "resume"
    val COLUMN_TIMESTAMP = "timestamp"

    // Create table SQL query
    val CREATE_TABLE = (
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY ,"
                    + COLUMN_DESCRIPTION + " TEXT,"

                    + COLUMN_THUMB + " TEXT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_URL + " TEXT,"
                    + COLUMN_STATUS + " TEXT DEFAULT '0',"
                    + COLUMN_RESUME_FROM + " TEXT DEFAULT '0',"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")")

    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {
        // create notes table
        db.execSQL(CREATE_TABLE)
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        // Create tables again
        onCreate(db)
    }

    companion object {
        // Database Version
        private val DATABASE_VERSION = 1
        // Database Name
        private val DATABASE_NAME = "videos_db"
    }

    fun getAllVideos(): ArrayList<RetroVideo> {
        val videos: ArrayList<RetroVideo> = ArrayList()

        // Select All Query
        val selectQuery = "SELECT  * FROM " + TABLE_NAME
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                var retroVideo = RetroVideo(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_THUMB)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_URL)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESUME_FROM))
                )
                videos.add(retroVideo)
            } while (cursor.moveToNext())
        }
        // close db ccursoronnection
        db.close()
        // return notes list
        return videos
    }

    fun getVideo(id: Int): RetroVideo {
        // get readable database as we are not inserting anything
        val selectQuery = "SELECT  * FROM $TABLE_NAME where $COLUMN_ID='$id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        var retroVideo = RetroVideo(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_THUMB)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_URL)),
                cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)),
                cursor.getString(cursor.getColumnIndex(COLUMN_RESUME_FROM))
        )
        // close the db connection
        cursor.close()

        return retroVideo
    }


    fun insertVideos(retroVideo: RetroVideo, update: Boolean): Long {
        // get writable database as we want to write data
        val db = this.writableDatabase

        val values = ContentValues()
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_ID, retroVideo.id)
        values.put(COLUMN_DESCRIPTION, retroVideo.description)
        values.put(COLUMN_THUMB, retroVideo.thumb)
        values.put(COLUMN_TITLE, retroVideo.title)
        values.put(COLUMN_URL, retroVideo.url)
        values.put(COLUMN_URL, retroVideo.url)
        //need to do
        // if (update) {
        if (retroVideo.status != null)
            values.put(COLUMN_STATUS, retroVideo.status)
        if (retroVideo.resumeFrom != null)
            values.put(COLUMN_RESUME_FROM, retroVideo.resumeFrom)
        // }
        // insert row
        val id = db.replace(TABLE_NAME, null, values)
        // close db connection
        db.close()
        // return newly inserted row id
        return id
    }

    fun deleteAll() {
        val db = this.writableDatabase
        // db.delete(TABLE_NAME,null,null);
        db.execSQL("delete * from" + TABLE_NAME);
        //db.execSQL("TRUNCATE table$TABLE_NAME")
        db.close()
    }

}