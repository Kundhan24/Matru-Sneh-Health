package com.matrusneh.health.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.matrusneh.health.data.dao.CheckupDao;
import com.matrusneh.health.data.dao.CheckupDao_Impl;
import com.matrusneh.health.data.dao.DangerSignDao;
import com.matrusneh.health.data.dao.DangerSignDao_Impl;
import com.matrusneh.health.data.dao.KickDao;
import com.matrusneh.health.data.dao.KickDao_Impl;
import com.matrusneh.health.data.dao.MotherProfileDao;
import com.matrusneh.health.data.dao.MotherProfileDao_Impl;
import com.matrusneh.health.data.dao.NutritionDao;
import com.matrusneh.health.data.dao.NutritionDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MatruSnehDatabase_Impl extends MatruSnehDatabase {
  private volatile KickDao _kickDao;

  private volatile NutritionDao _nutritionDao;

  private volatile CheckupDao _checkupDao;

  private volatile DangerSignDao _dangerSignDao;

  private volatile MotherProfileDao _motherProfileDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `kick_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `sessionId` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `nutrition_records` (`dateKey` TEXT NOT NULL, `completedItems` TEXT NOT NULL, `totalItems` INTEGER NOT NULL, `completedCount` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`dateKey`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `checkup_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `checkupDateMs` INTEGER NOT NULL, `notes` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, `workManagerTag` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `danger_sign_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `signId` TEXT NOT NULL, `signName` TEXT NOT NULL, `severity` TEXT NOT NULL, `loggedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `mother_profile` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `pregnancyStartDateMs` INTEGER NOT NULL, `ashaWorkerPhone` TEXT NOT NULL, `nearestPhcName` TEXT NOT NULL, `nearestPhcPhone` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '26f73339213bf50291524bc356d1759e')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `kick_events`");
        db.execSQL("DROP TABLE IF EXISTS `nutrition_records`");
        db.execSQL("DROP TABLE IF EXISTS `checkup_events`");
        db.execSQL("DROP TABLE IF EXISTS `danger_sign_logs`");
        db.execSQL("DROP TABLE IF EXISTS `mother_profile`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsKickEvents = new HashMap<String, TableInfo.Column>(3);
        _columnsKickEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKickEvents.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKickEvents.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysKickEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesKickEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoKickEvents = new TableInfo("kick_events", _columnsKickEvents, _foreignKeysKickEvents, _indicesKickEvents);
        final TableInfo _existingKickEvents = TableInfo.read(db, "kick_events");
        if (!_infoKickEvents.equals(_existingKickEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "kick_events(com.matrusneh.health.data.entity.KickEvent).\n"
                  + " Expected:\n" + _infoKickEvents + "\n"
                  + " Found:\n" + _existingKickEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsNutritionRecords = new HashMap<String, TableInfo.Column>(5);
        _columnsNutritionRecords.put("dateKey", new TableInfo.Column("dateKey", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNutritionRecords.put("completedItems", new TableInfo.Column("completedItems", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNutritionRecords.put("totalItems", new TableInfo.Column("totalItems", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNutritionRecords.put("completedCount", new TableInfo.Column("completedCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNutritionRecords.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNutritionRecords = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNutritionRecords = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNutritionRecords = new TableInfo("nutrition_records", _columnsNutritionRecords, _foreignKeysNutritionRecords, _indicesNutritionRecords);
        final TableInfo _existingNutritionRecords = TableInfo.read(db, "nutrition_records");
        if (!_infoNutritionRecords.equals(_existingNutritionRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "nutrition_records(com.matrusneh.health.data.entity.NutritionRecord).\n"
                  + " Expected:\n" + _infoNutritionRecords + "\n"
                  + " Found:\n" + _existingNutritionRecords);
        }
        final HashMap<String, TableInfo.Column> _columnsCheckupEvents = new HashMap<String, TableInfo.Column>(6);
        _columnsCheckupEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckupEvents.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckupEvents.put("checkupDateMs", new TableInfo.Column("checkupDateMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckupEvents.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckupEvents.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckupEvents.put("workManagerTag", new TableInfo.Column("workManagerTag", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCheckupEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCheckupEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCheckupEvents = new TableInfo("checkup_events", _columnsCheckupEvents, _foreignKeysCheckupEvents, _indicesCheckupEvents);
        final TableInfo _existingCheckupEvents = TableInfo.read(db, "checkup_events");
        if (!_infoCheckupEvents.equals(_existingCheckupEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "checkup_events(com.matrusneh.health.data.entity.CheckupEvent).\n"
                  + " Expected:\n" + _infoCheckupEvents + "\n"
                  + " Found:\n" + _existingCheckupEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsDangerSignLogs = new HashMap<String, TableInfo.Column>(5);
        _columnsDangerSignLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDangerSignLogs.put("signId", new TableInfo.Column("signId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDangerSignLogs.put("signName", new TableInfo.Column("signName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDangerSignLogs.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDangerSignLogs.put("loggedAt", new TableInfo.Column("loggedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDangerSignLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDangerSignLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDangerSignLogs = new TableInfo("danger_sign_logs", _columnsDangerSignLogs, _foreignKeysDangerSignLogs, _indicesDangerSignLogs);
        final TableInfo _existingDangerSignLogs = TableInfo.read(db, "danger_sign_logs");
        if (!_infoDangerSignLogs.equals(_existingDangerSignLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "danger_sign_logs(com.matrusneh.health.data.entity.DangerSignLog).\n"
                  + " Expected:\n" + _infoDangerSignLogs + "\n"
                  + " Found:\n" + _existingDangerSignLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsMotherProfile = new HashMap<String, TableInfo.Column>(7);
        _columnsMotherProfile.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMotherProfile.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMotherProfile.put("pregnancyStartDateMs", new TableInfo.Column("pregnancyStartDateMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMotherProfile.put("ashaWorkerPhone", new TableInfo.Column("ashaWorkerPhone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMotherProfile.put("nearestPhcName", new TableInfo.Column("nearestPhcName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMotherProfile.put("nearestPhcPhone", new TableInfo.Column("nearestPhcPhone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMotherProfile.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMotherProfile = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMotherProfile = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMotherProfile = new TableInfo("mother_profile", _columnsMotherProfile, _foreignKeysMotherProfile, _indicesMotherProfile);
        final TableInfo _existingMotherProfile = TableInfo.read(db, "mother_profile");
        if (!_infoMotherProfile.equals(_existingMotherProfile)) {
          return new RoomOpenHelper.ValidationResult(false, "mother_profile(com.matrusneh.health.data.entity.MotherProfile).\n"
                  + " Expected:\n" + _infoMotherProfile + "\n"
                  + " Found:\n" + _existingMotherProfile);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "26f73339213bf50291524bc356d1759e", "02781fb2e9c42b2f798dd57820904d4a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "kick_events","nutrition_records","checkup_events","danger_sign_logs","mother_profile");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `kick_events`");
      _db.execSQL("DELETE FROM `nutrition_records`");
      _db.execSQL("DELETE FROM `checkup_events`");
      _db.execSQL("DELETE FROM `danger_sign_logs`");
      _db.execSQL("DELETE FROM `mother_profile`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(KickDao.class, KickDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NutritionDao.class, NutritionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CheckupDao.class, CheckupDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DangerSignDao.class, DangerSignDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MotherProfileDao.class, MotherProfileDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public KickDao kickDao() {
    if (_kickDao != null) {
      return _kickDao;
    } else {
      synchronized(this) {
        if(_kickDao == null) {
          _kickDao = new KickDao_Impl(this);
        }
        return _kickDao;
      }
    }
  }

  @Override
  public NutritionDao nutritionDao() {
    if (_nutritionDao != null) {
      return _nutritionDao;
    } else {
      synchronized(this) {
        if(_nutritionDao == null) {
          _nutritionDao = new NutritionDao_Impl(this);
        }
        return _nutritionDao;
      }
    }
  }

  @Override
  public CheckupDao checkupDao() {
    if (_checkupDao != null) {
      return _checkupDao;
    } else {
      synchronized(this) {
        if(_checkupDao == null) {
          _checkupDao = new CheckupDao_Impl(this);
        }
        return _checkupDao;
      }
    }
  }

  @Override
  public DangerSignDao dangerSignDao() {
    if (_dangerSignDao != null) {
      return _dangerSignDao;
    } else {
      synchronized(this) {
        if(_dangerSignDao == null) {
          _dangerSignDao = new DangerSignDao_Impl(this);
        }
        return _dangerSignDao;
      }
    }
  }

  @Override
  public MotherProfileDao motherProfileDao() {
    if (_motherProfileDao != null) {
      return _motherProfileDao;
    } else {
      synchronized(this) {
        if(_motherProfileDao == null) {
          _motherProfileDao = new MotherProfileDao_Impl(this);
        }
        return _motherProfileDao;
      }
    }
  }
}
