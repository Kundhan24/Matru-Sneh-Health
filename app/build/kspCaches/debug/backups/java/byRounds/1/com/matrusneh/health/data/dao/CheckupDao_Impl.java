package com.matrusneh.health.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
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
import com.matrusneh.health.data.entity.CheckupEvent;
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
public final class CheckupDao_Impl implements CheckupDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CheckupEvent> __insertionAdapterOfCheckupEvent;

  private final EntityDeletionOrUpdateAdapter<CheckupEvent> __deletionAdapterOfCheckupEvent;

  private final EntityDeletionOrUpdateAdapter<CheckupEvent> __updateAdapterOfCheckupEvent;

  public CheckupDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCheckupEvent = new EntityInsertionAdapter<CheckupEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `checkup_events` (`id`,`title`,`checkupDateMs`,`notes`,`isCompleted`,`workManagerTag`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CheckupEvent entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getCheckupDateMs());
        statement.bindString(4, entity.getNotes());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getWorkManagerTag());
      }
    };
    this.__deletionAdapterOfCheckupEvent = new EntityDeletionOrUpdateAdapter<CheckupEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `checkup_events` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CheckupEvent entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCheckupEvent = new EntityDeletionOrUpdateAdapter<CheckupEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `checkup_events` SET `id` = ?,`title` = ?,`checkupDateMs` = ?,`notes` = ?,`isCompleted` = ?,`workManagerTag` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CheckupEvent entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getCheckupDateMs());
        statement.bindString(4, entity.getNotes());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getWorkManagerTag());
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final CheckupEvent event, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCheckupEvent.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final CheckupEvent event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCheckupEvent.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final CheckupEvent event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCheckupEvent.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<CheckupEvent>> getAllCheckups() {
    final String _sql = "SELECT * FROM checkup_events ORDER BY checkupDateMs ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"checkup_events"}, false, new Callable<List<CheckupEvent>>() {
      @Override
      @Nullable
      public List<CheckupEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCheckupDateMs = CursorUtil.getColumnIndexOrThrow(_cursor, "checkupDateMs");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfWorkManagerTag = CursorUtil.getColumnIndexOrThrow(_cursor, "workManagerTag");
          final List<CheckupEvent> _result = new ArrayList<CheckupEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CheckupEvent _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpCheckupDateMs;
            _tmpCheckupDateMs = _cursor.getLong(_cursorIndexOfCheckupDateMs);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final String _tmpWorkManagerTag;
            _tmpWorkManagerTag = _cursor.getString(_cursorIndexOfWorkManagerTag);
            _item = new CheckupEvent(_tmpId,_tmpTitle,_tmpCheckupDateMs,_tmpNotes,_tmpIsCompleted,_tmpWorkManagerTag);
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
  public LiveData<CheckupEvent> getNextCheckup(final long nowMs) {
    final String _sql = "SELECT * FROM checkup_events WHERE isCompleted = 0 AND checkupDateMs >= ? ORDER BY checkupDateMs ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, nowMs);
    return __db.getInvalidationTracker().createLiveData(new String[] {"checkup_events"}, false, new Callable<CheckupEvent>() {
      @Override
      @Nullable
      public CheckupEvent call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCheckupDateMs = CursorUtil.getColumnIndexOrThrow(_cursor, "checkupDateMs");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfWorkManagerTag = CursorUtil.getColumnIndexOrThrow(_cursor, "workManagerTag");
          final CheckupEvent _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpCheckupDateMs;
            _tmpCheckupDateMs = _cursor.getLong(_cursorIndexOfCheckupDateMs);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final String _tmpWorkManagerTag;
            _tmpWorkManagerTag = _cursor.getString(_cursorIndexOfWorkManagerTag);
            _result = new CheckupEvent(_tmpId,_tmpTitle,_tmpCheckupDateMs,_tmpNotes,_tmpIsCompleted,_tmpWorkManagerTag);
          } else {
            _result = null;
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
  public Object getById(final long id, final Continuation<? super CheckupEvent> $completion) {
    final String _sql = "SELECT * FROM checkup_events WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CheckupEvent>() {
      @Override
      @Nullable
      public CheckupEvent call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCheckupDateMs = CursorUtil.getColumnIndexOrThrow(_cursor, "checkupDateMs");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfWorkManagerTag = CursorUtil.getColumnIndexOrThrow(_cursor, "workManagerTag");
          final CheckupEvent _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpCheckupDateMs;
            _tmpCheckupDateMs = _cursor.getLong(_cursorIndexOfCheckupDateMs);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final String _tmpWorkManagerTag;
            _tmpWorkManagerTag = _cursor.getString(_cursorIndexOfWorkManagerTag);
            _result = new CheckupEvent(_tmpId,_tmpTitle,_tmpCheckupDateMs,_tmpNotes,_tmpIsCompleted,_tmpWorkManagerTag);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
