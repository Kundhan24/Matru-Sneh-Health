package com.matrusneh.health.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.matrusneh.health.data.entity.DangerSignLog;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DangerSignDao_Impl implements DangerSignDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DangerSignLog> __insertionAdapterOfDangerSignLog;

  private final EntityDeletionOrUpdateAdapter<DangerSignLog> __deletionAdapterOfDangerSignLog;

  public DangerSignDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDangerSignLog = new EntityInsertionAdapter<DangerSignLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `danger_sign_logs` (`id`,`signId`,`signName`,`severity`,`loggedAt`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DangerSignLog entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getSignId());
        statement.bindString(3, entity.getSignName());
        statement.bindString(4, entity.getSeverity());
        statement.bindLong(5, entity.getLoggedAt());
      }
    };
    this.__deletionAdapterOfDangerSignLog = new EntityDeletionOrUpdateAdapter<DangerSignLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `danger_sign_logs` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DangerSignLog entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final DangerSignLog log, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDangerSignLog.insertAndReturnId(log);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final DangerSignLog log, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfDangerSignLog.handle(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<DangerSignLog>> getAllLogs() {
    final String _sql = "SELECT * FROM danger_sign_logs ORDER BY loggedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"danger_sign_logs"}, false, new Callable<List<DangerSignLog>>() {
      @Override
      @Nullable
      public List<DangerSignLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSignId = CursorUtil.getColumnIndexOrThrow(_cursor, "signId");
          final int _cursorIndexOfSignName = CursorUtil.getColumnIndexOrThrow(_cursor, "signName");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfLoggedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "loggedAt");
          final List<DangerSignLog> _result = new ArrayList<DangerSignLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DangerSignLog _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSignId;
            _tmpSignId = _cursor.getString(_cursorIndexOfSignId);
            final String _tmpSignName;
            _tmpSignName = _cursor.getString(_cursorIndexOfSignName);
            final String _tmpSeverity;
            _tmpSeverity = _cursor.getString(_cursorIndexOfSeverity);
            final long _tmpLoggedAt;
            _tmpLoggedAt = _cursor.getLong(_cursorIndexOfLoggedAt);
            _item = new DangerSignLog(_tmpId,_tmpSignId,_tmpSignName,_tmpSeverity,_tmpLoggedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<DangerSignLog>> getRecentLogs() {
    final String _sql = "SELECT * FROM danger_sign_logs ORDER BY loggedAt DESC LIMIT 50";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"danger_sign_logs"}, false, new Callable<List<DangerSignLog>>() {
      @Override
      @Nullable
      public List<DangerSignLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSignId = CursorUtil.getColumnIndexOrThrow(_cursor, "signId");
          final int _cursorIndexOfSignName = CursorUtil.getColumnIndexOrThrow(_cursor, "signName");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfLoggedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "loggedAt");
          final List<DangerSignLog> _result = new ArrayList<DangerSignLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DangerSignLog _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSignId;
            _tmpSignId = _cursor.getString(_cursorIndexOfSignId);
            final String _tmpSignName;
            _tmpSignName = _cursor.getString(_cursorIndexOfSignName);
            final String _tmpSeverity;
            _tmpSeverity = _cursor.getString(_cursorIndexOfSeverity);
            final long _tmpLoggedAt;
            _tmpLoggedAt = _cursor.getLong(_cursorIndexOfLoggedAt);
            _item = new DangerSignLog(_tmpId,_tmpSignId,_tmpSignName,_tmpSeverity,_tmpLoggedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
