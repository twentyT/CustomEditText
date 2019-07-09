package com.example.danmaohua.socketproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ProjectName: SmartMuseumControl$
 * @Package: com.chuanghai.smartmuseumcontrol.tool$
 * @ClassName: SQLiteHelper$
 * @Description: java类作用描述
 * @Author: DanMaoHua
 * @CreateDate: 2019/5/21$ 17:17$
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    //建表

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY,"
                + " BookName VARCHAR(30)  NOT NULL,"
                + " Author VARCHAR(20),"
                + " Publisher VARCHAR(30))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    private final static String DATABASE_NAME = "Library";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "Book";

    //构造函数，创建数据库
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //获取游标
    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //插入一条记录
    public long insert(String bookName, String author, String publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("BookName", bookName);
        cv.put("Author", author);
        cv.put("Publisher", publisher);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    //根据条件查询
    public Cursor query(String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE BookName LIKE ?", args);
        return cursor;
    }
    //查询所有数据
    public Cursor queryAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME , null, null, null, null, null, "_id desc");
        return cursor;
    }

    //删除记录
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = {Integer.toString(id)};
        db.delete(TABLE_NAME, where, whereValue);
    }

    //更新记录
    public void update(int id, String bookName, String author, String publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = {Integer.toString(id)};
        ContentValues cv = new ContentValues();
        cv.put("BookName", bookName);
        cv.put("Author", author);
        cv.put("Publisher", publisher);
        db.update(TABLE_NAME, cv, where, whereValue);
    }

}
