package com.gm.circley.control.manager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gm.circley.db.DBPhoto;
import com.gm.circley.db.dao.DBPhotoDao;
import com.gm.circley.db.dao.DaoMaster;
import com.gm.circley.db.dao.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by lgm on 2016/8/13.
 */
public class DBManager {

    private static final String DB_NAME = "circley_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context){
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context,DB_NAME,null);
    }

    public static DBManager getInstance(Context context){
        if (mInstance == null){
            synchronized (DBManager.class){
                if (mInstance == null){
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    private SQLiteDatabase getReadableDatabase(){
        if (openHelper == null){
            openHelper = new DaoMaster.DevOpenHelper(context,DB_NAME,null);
        }

        SQLiteDatabase readableDatabase = openHelper.getReadableDatabase();
        return readableDatabase;
    }

    private SQLiteDatabase getWritableDatabase(){
        if (openHelper == null){
            openHelper = new DaoMaster.DevOpenHelper(context,DB_NAME,null);
        }

        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
        return writableDatabase;
    }

    public void insertPhotoList(List<DBPhoto> list){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DBPhotoDao dbPhotoDao = daoSession.getDBPhotoDao();
        dbPhotoDao.insertInTx(list);
    }

    public List<DBPhoto> getLimitPhotoList(int offset,int limit){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DBPhotoDao dbPhotoDao = daoSession.getDBPhotoDao();
        QueryBuilder<DBPhoto> qb = dbPhotoDao.queryBuilder();
        qb.offset(offset).limit(limit).orderDesc(DBPhotoDao.Properties.Id);
        List<DBPhoto> list = qb.list();
        return list;
    }

    public List<DBPhoto> getPhotoList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DBPhotoDao dbPhotoDao = daoSession.getDBPhotoDao();
        QueryBuilder<DBPhoto> qb = dbPhotoDao.queryBuilder();
        List<DBPhoto> list = qb.list();
        return list;
    }

    public long getPhotoListSize(){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DBPhotoDao dbPhotoDao = daoSession.getDBPhotoDao();
        QueryBuilder<DBPhoto> qb = dbPhotoDao.queryBuilder();
        long count = qb.count();
        return count;
    }

    public DBPhoto getPhotoById(long rowId){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DBPhotoDao dbPhotoDao = daoSession.getDBPhotoDao();
        DBPhoto dbPhoto = dbPhotoDao.loadByRowId(rowId);
        return dbPhoto;
    }
}
