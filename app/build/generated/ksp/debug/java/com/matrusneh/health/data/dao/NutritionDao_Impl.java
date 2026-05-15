package com.matrusneh.health.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.matrusneh.health.data.entity.NutritionRecord;
import java.lang.Class;
import java.lang.Exception;
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
public final class NutritionDao_Impl implements NutritionDao {
  private final RoomDatabase __db;

  private final EntityDeletionOrUpdateAdapter<NutritionRecord> __deletionAdapterOfNutritionRecord;

  private final EntityUpsertionAdapter<NutritionRecord> __upsertionAdapterOfNutritionRecord;

  public NutritionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__deletionAdapterOfNutritionRecord = new EntityDeletionOrUpdateAdapter<NutritionRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `nutrition_records` WHERE `dateKey` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NutritionRecord entity) {
        statement.bindString(1, entity.getDateKey());
      }
    };
    this.__upsertionAdapterOfNutritionRecord = new EntityUpsertionAdapter<NutritionRecord>(new EntityInsertionAdapter<NutritionRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `nutrition_records` (`dateKey`,`completedItems`,`totalItems`,`completedCount`,`updatedAt`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NutritionRecord entity) {
        statement.bindString(1, entity.getDateKey());
        statement.bindString(2, entity.getCompletedItems());
        statement.bindLong(3, entity.getTotalItems());
        statement.bindLong(4, entity.getCompletedCount());
        statement.bindLong(5, entity.getUpdatedAt());
      }
    }, new EntityDeletionOrUpdateAdapter<NutritionRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `nutrition_records` SET `dateKey` = ?,`completedItems` = ?,`totalItems` = ?,`completedCount` = ?,`updatedAt` = ? WHERE `dateKey` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NutritionRecord entity) {
        statement.bindString(1, entity.getDateKey());
        statement.bindString(2, entity.getCompletedItems());
        statement.bindLong(3, entity.getTotalItems());
        statement.bindLong(4, entity.getCompletedCount());
        statement.bindLong(5, entity.getUpdatedAt());
        statement.bindString(6, entity.getDateKey());
      }
    });
  }

  @Override
  public Object delete(final NutritionRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfNutritionRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsert(final NutritionRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __upsertionAdapterOfNutritionRecord.upsert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<NutritionRecord> getRecordForDate(final String dateKey) {
    final String _sql = "SELECT * FROM nutrition_records WHERE dateKey = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, dateKey);
    return __db.getInvalidationTracker().createLiveData(new String[] {"nutrition_records"}, false, new Callable<NutritionRecord>() {
      @Override
      @Nullable
      public NutritionRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDateKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dateKey");
          final int _cursorIndexOfCompletedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "completedItems");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "totalItems");
          final int _cursorIndexOfCompletedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "completedCount");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final NutritionRecord _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDateKey;
            _tmpDateKey = _cursor.getString(_cursorIndexOfDateKey);
            final String _tmpCompletedItems;
            _tmpCompletedItems = _cursor.getString(_cursorIndexOfCompletedItems);
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpCompletedCount;
            _tmpCompletedCount = _cursor.getInt(_cursorIndexOfCompletedCount);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new NutritionRecord(_tmpDateKey,_tmpCompletedItems,_tmpTotalItems,_tmpCompletedCount,_tmpUpdatedAt);
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
  public Object getRecordForDateSync(final String dateKey,
      final Continuation<? super NutritionRecord> $completion) {
    final String _sql = "SELECT * FROM nutrition_records WHERE dateKey = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, dateKey);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NutritionRecord>() {
      @Override
      @Nullable
      public NutritionRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDateKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dateKey");
          final int _cursorIndexOfCompletedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "completedItems");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "totalItems");
          final int _cursorIndexOfCompletedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "completedCount");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final NutritionRecord _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDateKey;
            _tmpDateKey = _cursor.getString(_cursorIndexOfDateKey);
            final String _tmpCompletedItems;
            _tmpCompletedItems = _cursor.getString(_cursorIndexOfCompletedItems);
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpCompletedCount;
            _tmpCompletedCount = _cursor.getInt(_cursorIndexOfCompletedCount);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new NutritionRecord(_tmpDateKey,_tmpCompletedItems,_tmpTotalItems,_tmpCompletedCount,_tmpUpdatedAt);
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

  @Override
  public LiveData<List<NutritionRecord>> getRecentRecords(final String fromDate) {
    final String _sql = "SELECT * FROM nutrition_records WHERE dateKey >= ? ORDER BY dateKey DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, fromDate);
    return __db.getInvalidationTracker().createLiveData(new String[] {"nutrition_records"}, false, new Callable<List<NutritionRecord>>() {
      @Override
      @Nullable
      public List<NutritionRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDateKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dateKey");
          final int _cursorIndexOfCompletedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "completedItems");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "totalItems");
          final int _cursorIndexOfCompletedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "completedCount");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<NutritionRecord> _result = new ArrayList<NutritionRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NutritionRecord _item;
            final String _tmpDateKey;
            _tmpDateKey = _cursor.getString(_cursorIndexOfDateKey);
            final String _tmpCompletedItems;
            _tmpCompletedItems = _cursor.getString(_cursorIndexOfCompletedItems);
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpCompletedCount;
            _tmpCompletedCount = _cursor.getInt(_cursorIndexOfCompletedCount);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new NutritionRecord(_tmpDateKey,_tmpCompletedItems,_tmpTotalItems,_tmpCompletedCount,_tmpUpdatedAt);
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
