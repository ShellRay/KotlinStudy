package com.kotlin.study.greendaogenlib;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.kotlin.study.greendaogenlib.utils.DBHelper;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "ACCOUNT".
 */
public class AccountDao extends AbstractDao<Account, String> {

    public static final String TABLENAME = "ACCOUNT";

    /**
     * Properties of entity Account.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Avatarlink = new Property(2, String.class, "avatarlink", false, "AVATARLINK");
    }


    public AccountDao(DaoConfig config) {
        super(config);
    }

    public AccountDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCOUNT\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"AVATARLINK\" TEXT);"); // 2: avatarlink
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCOUNT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Account entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
        stmt.bindString(2, entity.getName());

        String avatarlink = entity.getAvatarlink();
        if (avatarlink != null) {
            stmt.bindString(3, avatarlink);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Account entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
        stmt.bindString(2, entity.getName());

        String avatarlink = entity.getAvatarlink();
        if (avatarlink != null) {
            stmt.bindString(3, avatarlink);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }

    @Override
    public Account readEntity(Cursor cursor, int offset) {
        Account entity = new Account( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
                cursor.getString(offset + 1), // name
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // avatarlink
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, Account entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setAvatarlink(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
    }

    @Override
    protected final String updateKeyAfterInsert(Account entity, long rowId) {
        return entity.getId();
    }

    @Override
    public String getKey(Account entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Account entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }


    public boolean add(Context context, Account account) {
        try {
            DBHelper.Companion.getInstance(context).getDaoSession().getAccountDao().insert(account);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteData(Context context, String tid, String name) {
        try {
//            DBHelper.Companion.getInstance(context).getDaoSession().getAccountDao().d
            String sql = "DELETE FROM ";
            sql += AccountDao.TABLENAME;
            sql += " where " + Properties.Id.columnName + "=? and " + Properties.Name.columnName + "=?";
            String args[] = {tid, name};
            DBHelper.Companion.getInstance(context).getWritableDatabase().execSQL(sql, args);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteAllData(Context context) {
        try {
            DBHelper.Companion.getInstance(context).getDaoSession().getAccountDao().deleteAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean changeData(Context context, String tid, String name, String avatar) {
        try {
//            String sql = "UPDATE ";
//            sql += AccountDao.TABLENAME;
//            sql += " SET " + Properties.Avatarlink.columnName + " =?";
//            sql += " where " + Properties.Id.columnName + "=? and " + Properties.Name.columnName + "=?";
//
//            String args[] = {avatar, tid, name};
//            DBHelper.Companion.getInstance(context).getWritableDatabase().execSQL(sql, args);//回调速度有点慢

            List<Account> accounts = DBHelper.Companion.getInstance(context).getDaoSession().getAccountDao().loadAll();
            for (int i=0;i<accounts.size();i++){
                Account account = accounts.get(i);
                if(account.getId().equals(tid)){
                    account.setAvatarlink(avatar);
                    DBHelper.Companion.getInstance(context).getDaoSession().getAccountDao().update(account);
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
