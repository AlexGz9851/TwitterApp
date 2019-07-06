package com.codepath.apps.restclienttemplate.models;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class TweetDao_Impl implements TweetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTweet;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTweet;

  public TweetDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTweet = new EntityInsertionAdapter<Tweet>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Tweet`(`uid`,`body`,`createdDate`,`user`,`relativeTime`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tweet value) {
        stmt.bindLong(1, value.uid);
        if (value.body == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.body);
        }
        if (value.createdDate == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.createdDate);
        }
        if (value.relativeTime == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.relativeTime);
        }
      }
    };
    this.__deletionAdapterOfTweet = new EntityDeletionOrUpdateAdapter<Tweet>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Tweet` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tweet value) {
        stmt.bindLong(1, value.uid);
      }
    };
  }

  @Override
  public void insertTweet(Tweet... tweets) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTweet.insert(tweets);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTweet(Tweet tweet) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfTweet.handle(tweet);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Tweet byId(long id) {
    final String _sql = "SELECT * FROM Tweet WHERE uid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfBody = _cursor.getColumnIndexOrThrow("body");
      final int _cursorIndexOfCreatedDate = _cursor.getColumnIndexOrThrow("createdDate");
      final int _cursorIndexOfUser = _cursor.getColumnIndexOrThrow("user");
      final int _cursorIndexOfRelativeTime = _cursor.getColumnIndexOrThrow("relativeTime");
      final Tweet _result;
      if(_cursor.moveToFirst()) {
        _result = new Tweet();
        _result.uid = _cursor.getLong(_cursorIndexOfUid);
        _result.body = _cursor.getString(_cursorIndexOfBody);
        _result.createdDate = _cursor.getString(_cursorIndexOfCreatedDate);
        _result.relativeTime = _cursor.getString(_cursorIndexOfRelativeTime);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Tweet> recentTweets() {
    final String _sql = "SELECT * FROM Tweet ORDER BY uid DESC LIMIT 300";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfBody = _cursor.getColumnIndexOrThrow("body");
      final int _cursorIndexOfCreatedDate = _cursor.getColumnIndexOrThrow("createdDate");
      final int _cursorIndexOfUser = _cursor.getColumnIndexOrThrow("user");
      final int _cursorIndexOfRelativeTime = _cursor.getColumnIndexOrThrow("relativeTime");
      final List<Tweet> _result = new ArrayList<Tweet>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Tweet _item;
        _item = new Tweet();
        _item.uid = _cursor.getLong(_cursorIndexOfUid);
        _item.body = _cursor.getString(_cursorIndexOfBody);
        _item.createdDate = _cursor.getString(_cursorIndexOfCreatedDate);
        _item.relativeTime = _cursor.getString(_cursorIndexOfRelativeTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
