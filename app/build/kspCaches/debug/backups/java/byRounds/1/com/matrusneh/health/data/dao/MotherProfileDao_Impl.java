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
import com.matrusneh.health.data.entity.MotherProfile;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MotherProfileDao_Impl implements MotherProfileDao {
  private final RoomDatabase __db;

  private final EntityUpsertionAdapter<MotherProfile> __upsertionAdapterOfMotherProfile;

  public MotherProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__upsertionAdapterOfMotherProfile = new EntityUpsertionAdapter<MotherProfile>(new EntityInsertionAdapter<MotherProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `mother_profile` (`id`,`name`,`pregnancyStartDateMs`,`ashaWorkerPhone`,`nearestPhcName`,`nearestPhcPhone`,`createdAt`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MotherProfile entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getPregnancyStartDateMs());
        statement.bindString(4, entity.getAshaWorkerPhone());
        statement.bindString(5, entity.getNearestPhcName());
        statement.bindString(6, entity.getNearestPhcPhone());
        statement.bindLong(7, entity.getCreatedAt());
      }
    }, new EntityDeletionOrUpdateAdapter<MotherProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `mother_profile` SET `id` = ?,`name` = ?,`pregnancyStartDateMs` = ?,`ashaWorkerPhone` = ?,`nearestPhcName` = ?,`nearestPhcPhone` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MotherProfile entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getPregnancyStartDateMs());
        statement.bindString(4, entity.getAshaWorkerPhone());
        statement.bindString(5, entity.getNearestPhcName());
        statement.bindString(6, entity.getNearestPhcPhone());
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getId());
      }
    });
  }

  @Override
  public Object upsert(final MotherProfile profile, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __upsertionAdapterOfMotherProfile.upsert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<MotherProfile> getProfile() {
    final String _sql = "SELECT * FROM mother_profile WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"mother_profile"}, false, new Callable<MotherProfile>() {
      @Override
      @Nullable
      public MotherProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPregnancyStartDateMs = CursorUtil.getColumnIndexOrThrow(_cursor, "pregnancyStartDateMs");
          final int _cursorIndexOfAshaWorkerPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "ashaWorkerPhone");
          final int _cursorIndexOfNearestPhcName = CursorUtil.getColumnIndexOrThrow(_cursor, "nearestPhcName");
          final int _cursorIndexOfNearestPhcPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "nearestPhcPhone");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final MotherProfile _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpPregnancyStartDateMs;
            _tmpPregnancyStartDateMs = _cursor.getLong(_cursorIndexOfPregnancyStartDateMs);
            final String _tmpAshaWorkerPhone;
            _tmpAshaWorkerPhone = _cursor.getString(_cursorIndexOfAshaWorkerPhone);
            final String _tmpNearestPhcName;
            _tmpNearestPhcName = _cursor.getString(_cursorIndexOfNearestPhcName);
            final String _tmpNearestPhcPhone;
            _tmpNearestPhcPhone = _cursor.getString(_cursorIndexOfNearestPhcPhone);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new MotherProfile(_tmpId,_tmpName,_tmpPregnancyStartDateMs,_tmpAshaWorkerPhone,_tmpNearestPhcName,_tmpNearestPhcPhone,_tmpCreatedAt);
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
  public Object getProfileSync(final Continuation<? super MotherProfile> $completion) {
    final String _sql = "SELECT * FROM mother_profile WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MotherProfile>() {
      @Override
      @Nullable
      public MotherProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPregnancyStartDateMs = CursorUtil.getColumnIndexOrThrow(_cursor, "pregnancyStartDateMs");
          final int _cursorIndexOfAshaWorkerPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "ashaWorkerPhone");
          final int _cursorIndexOfNearestPhcName = CursorUtil.getColumnIndexOrThrow(_cursor, "nearestPhcName");
          final int _cursorIndexOfNearestPhcPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "nearestPhcPhone");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final MotherProfile _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpPregnancyStartDateMs;
            _tmpPregnancyStartDateMs = _cursor.getLong(_cursorIndexOfPregnancyStartDateMs);
            final String _tmpAshaWorkerPhone;
            _tmpAshaWorkerPhone = _cursor.getString(_cursorIndexOfAshaWorkerPhone);
            final String _tmpNearestPhcName;
            _tmpNearestPhcName = _cursor.getString(_cursorIndexOfNearestPhcName);
            final String _tmpNearestPhcPhone;
            _tmpNearestPhcPhone = _cursor.getString(_cursorIndexOfNearestPhcPhone);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new MotherProfile(_tmpId,_tmpName,_tmpPregnancyStartDateMs,_tmpAshaWorkerPhone,_tmpNearestPhcName,_tmpNearestPhcPhone,_tmpCreatedAt);
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
