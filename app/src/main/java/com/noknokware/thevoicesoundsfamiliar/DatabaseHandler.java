package com.noknokware.thevoicesoundsfamiliar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "thevoicesoundsfamiliar";

    private static final String TABLE_PLAYER = "player";
    private static final String TABLE_SETTINGS = "settings";
    private static final String TABLE_SONG = "song";

    // Player Table Columns names
    private static final String PLAYER_ID = "id";
    private static final String PLAYER_NAME = "player_name";
    private static final String GENDER = "gender";
    private static final String FINAL_SCORE = "final_score";

    // Settings Table Columns names
    private static final String SETTINGS_ID = "id";
    private static final String SOUND_LEVEL = "sound_level";
    private static final String THEME = "theme";

    // Song Table Columns names
    private static final String SONG_PLAYER_ID = "id";
    private static final String SONG_NAME = "song_name";
    private static final String SONG_INDEX = "song_index";
    private static final String SONG_LEVEL = "song_level";
    private static final String SONG_IS_CORRECT = "song_is_correct";
    private static final String SONG_USED_HINT = "song_used_hint";
    private static final String SONG_SCORE = "song_score";
    private static final String SONG_IS_FINISHED = "song_is_finished";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYER + "("
                + PLAYER_ID + " INTEGER PRIMARY KEY,"
                + PLAYER_NAME + " TEXT,"
                + GENDER + " TEXT,"
                + FINAL_SCORE + " INTEGER"
                + ")";
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS + "("
                + SETTINGS_ID + " INTEGER PRIMARY KEY,"
                + SOUND_LEVEL + " INTEGER,"
                + THEME + " TEXT"
                + ")";
        String CREATE_SONG_TABLE = "CREATE TABLE " + TABLE_SONG + "("
                + SONG_PLAYER_ID + " INTEGER,"
                + SONG_NAME + " TEXT,"
                + SONG_INDEX + " INTEGER,"
                + SONG_LEVEL + " TEXT,"
                + SONG_IS_CORRECT + " INTEGER,"
                + SONG_USED_HINT + " INTEGER,"
                + SONG_SCORE + " INTEGER,"
                + SONG_IS_FINISHED + " INTEGER"
                + ")";
        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_SETTINGS_TABLE);
        db.execSQL(CREATE_SONG_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
        db.close();
    }

    /* PLAYER START */

    void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME, player.getPlayerName());
        values.put(GENDER, player.getGender());
        values.put(FINAL_SCORE, player.getFinalScore());
        db.insert(TABLE_PLAYER, null, values);
        db.close();
        Log.v("New", "Added New Player");
    }

    public Player getPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PLAYER, new String[] {
                        PLAYER_ID,
                        PLAYER_NAME,
                        GENDER,
                        FINAL_SCORE
                },
                PLAYER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Player player = new Player(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3))
        );
        db.close();
        return player;
    }

    public List<Player> getAllPlayer() {
        List<Player> playerList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setPlayerId(Integer.parseInt(cursor.getString(0)));
                player.setPlayerName(cursor.getString(1));
                player.setGender(cursor.getString(2));
                player.setFinalScore(Integer.parseInt(cursor.getString(3)));

                playerList.add(player);
            } while (cursor.moveToNext());
        }
        Log.v("Get All", "Show all Players");
        db.close();
        return playerList;
    }

    public int updatePlayerNameGender(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME, player.getPlayerName());
        values.put(GENDER, player.getGender());
        //values.put(FINAL_SCORE, player.getFinalScore());
        int ret = db.update(TABLE_PLAYER, values, PLAYER_ID + " = ?",
                new String[] { String.valueOf(player.getPlayerId()) });
        db.close();
        return ret;

    }

    public int updatePlayerScore(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(PLAYER_NAME, player.getPlayerName());
//        values.put(GENDER, player.getGender());
        values.put(FINAL_SCORE, player.getFinalScore());
        int ret = db.update(TABLE_PLAYER, values, PLAYER_ID + " = ?",
                new String[] { String.valueOf(player.getPlayerId()) });
        db.close();
        return ret;

    }


    public void deletePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER, PLAYER_ID + " = ?",
                new String[] { String.valueOf(player.getPlayerId()) });
        db.close();
    }

    public int getPlayerCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int ret = cursor.getCount();
        cursor.close();
        db.close();
        return ret;
    }

    /* PLAYER END */

    /* SETTINGS START */

    void addSettings(Settings settings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SOUND_LEVEL, settings.getSoundLevel());
        values.put(THEME, settings.getTheme());
        db.insert(TABLE_SETTINGS, null, values);
        db.close();
    }

    Settings getSettings(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SETTINGS, new String[] {
                        SETTINGS_ID,
                        SOUND_LEVEL,
                        THEME
                },
                SETTINGS_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Settings settings = new Settings(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                cursor.getString(2)
        );
        db.close();
        return settings;
    }

    public List<Settings> getAllSettings() {
        List<Settings> settingsList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_SETTINGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Settings settings = new Settings();
                settings.setSettingsId(Integer.parseInt(cursor.getString(0)));
                settings.setSoundLevel(Integer.parseInt(cursor.getString(1)));
                settings.setTheme(cursor.getString(2));

                settingsList.add(settings);
            } while (cursor.moveToNext());
        }
        db.close();
        return settingsList;
    }

    public int updateSettings(Settings settings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SOUND_LEVEL, settings.getSoundLevel());
        values.put(THEME, settings.getTheme());
        int ret =  db.update(TABLE_SETTINGS, values, SETTINGS_ID + " = ?",
                new String[] { String.valueOf(settings.getSettingsId()) });
        db.close();
        return ret;
    }

    public void deleteSettings(Settings settings) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SETTINGS, SETTINGS_ID + " = ?",
                new String[] { String.valueOf(settings.getSettingsId()) });
        db.close();
    }

    public int getSettingsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SETTINGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int ret = cursor.getCount();
        cursor.close();
        db.close();
        return ret;
    }

    /* SETTINGS END */

    /* SONGS START */

    void addSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SONG_PLAYER_ID, song.getSongPlayerId());
        values.put(SONG_NAME, song.getSongName());
        values.put(SONG_INDEX, song.getSongIndex());
        values.put(SONG_LEVEL, song.getSongLevel());
        values.put(SONG_IS_CORRECT, song.getSongIsCorrect());
        values.put(SONG_USED_HINT, song.getSongUsedHint());
        values.put(SONG_SCORE, song.getSongScore());
        values.put(SONG_IS_FINISHED, song.getSongIsFinished());
        db.insert(TABLE_SONG, null, values);
        db.close();
    }

    public List<Song> getSongs(int id, String level) {
        List<Song> songList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONG, new String[] {
                        SONG_PLAYER_ID,
                        SONG_NAME,
                        SONG_INDEX,
                        SONG_LEVEL,
                        SONG_IS_CORRECT,
                        SONG_USED_HINT,
                        SONG_SCORE,
                        SONG_IS_FINISHED
                },
                SONG_PLAYER_ID + "=? AND " + SONG_LEVEL + "=?",
                new String[] { String.valueOf(id), String.valueOf(level) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setSongPlayerId(Integer.parseInt(cursor.getString(0)));
                song.setSongName(cursor.getString(1));
                song.setSongIndex(Integer.parseInt(cursor.getString(2)));
                song.setSongLevel(cursor.getString(3));
                song.setSongIsCorrect(Integer.parseInt(cursor.getString(4)));
                song.setSongUsedHint(Integer.parseInt(cursor.getString(5)));
                song.setSongScore(Integer.parseInt(cursor.getString(6)));
                song.setSongIsFinished(Integer.parseInt(cursor.getString(7)));
                songList.add(song);
            } while (cursor.moveToNext());
        }
        db.close();

        return songList;
    }


    public Song getSongByIdLevelIndex(int id, String level, int index) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONG, new String[] {
                        SONG_PLAYER_ID,
                        SONG_NAME,
                        SONG_INDEX,
                        SONG_LEVEL,
                        SONG_IS_CORRECT,
                        SONG_USED_HINT,
                        SONG_SCORE,
                        SONG_IS_FINISHED
                },
                SONG_PLAYER_ID + "=? AND " + SONG_LEVEL + "=? AND " + SONG_INDEX + "=?",
                new String[] { String.valueOf(id), String.valueOf(level), String.valueOf(index) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Song song = new Song(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7))
        );
        db.close();
        return song;
    }

    public List<Song> getAllSong() {
        List<Song> songList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setSongPlayerId(Integer.parseInt(cursor.getString(0)));
                song.setSongName(cursor.getString(1));
                song.setSongIndex(Integer.parseInt(cursor.getString(2)));
                song.setSongLevel(cursor.getString(3));
                song.setSongIsCorrect(Integer.parseInt(cursor.getString(4)));
                song.setSongUsedHint(Integer.parseInt(cursor.getString(5)));
                song.setSongScore(Integer.parseInt(cursor.getString(6)));
                song.setSongIsFinished(Integer.parseInt(cursor.getString(7)));
                songList.add(song);
            } while (cursor.moveToNext());
        }
        db.close();
        return songList;
    }

    public int updateSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SONG_PLAYER_ID, song.getSongPlayerId());
        values.put(SONG_NAME, song.getSongName());
        values.put(SONG_INDEX, song.getSongIndex());
        values.put(SONG_LEVEL, song.getSongLevel());
        values.put(SONG_IS_CORRECT, song.getSongIsCorrect());
        values.put(SONG_USED_HINT, song.getSongUsedHint());
        values.put(SONG_SCORE, song.getSongScore());
        values.put(SONG_IS_FINISHED, song.getSongIsFinished());
        int ret = db.update(TABLE_SONG, values, SONG_PLAYER_ID + " = ?",
                new String[] { String.valueOf(song.getSongPlayerId()) });
        db.close();
        return ret;
    }

    public int updateSongByIdLevelIndex(int id, String level, int index, Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(SONG_PLAYER_ID, song.getSongPlayerId());
        //values.put(SONG_NAME, song.getSongName());
        //values.put(SONG_INDEX, song.getSongIndex());
        //values.put(SONG_LEVEL, song.getSongLevel());
        values.put(SONG_IS_CORRECT, song.getSongIsCorrect());
        values.put(SONG_USED_HINT, song.getSongUsedHint());
        values.put(SONG_SCORE, song.getSongScore());
        values.put(SONG_IS_FINISHED, song.getSongIsFinished());
        int ret = db.update(TABLE_SONG, values, SONG_PLAYER_ID + "=? AND " + SONG_LEVEL + "=? AND " + SONG_INDEX + "=?",
                new String[] { String.valueOf(id), String.valueOf(level), String.valueOf(index) });
        db.close();
        return ret;
    }

    public List<Song> getFinishedSongsByIdLevel(int id, String level) {
        List<Song> songList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONG, new String[] {
                        SONG_PLAYER_ID,
                        SONG_NAME,
                        SONG_INDEX,
                        SONG_LEVEL,
                        SONG_IS_CORRECT,
                        SONG_USED_HINT,
                        SONG_SCORE,
                        SONG_IS_FINISHED
                },
                SONG_PLAYER_ID + "=? AND " + SONG_LEVEL + "=? AND " + SONG_IS_FINISHED + "=?",
                new String[] { String.valueOf(id), String.valueOf(level), String.valueOf(1) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setSongPlayerId(Integer.parseInt(cursor.getString(0)));
                song.setSongName(cursor.getString(1));
                song.setSongIndex(Integer.parseInt(cursor.getString(2)));
                song.setSongLevel(cursor.getString(3));
                song.setSongIsCorrect(Integer.parseInt(cursor.getString(4)));
                song.setSongUsedHint(Integer.parseInt(cursor.getString(5)));
                song.setSongScore(Integer.parseInt(cursor.getString(6)));
                song.setSongIsFinished(Integer.parseInt(cursor.getString(7)));
                songList.add(song);
            } while (cursor.moveToNext());
        }
        db.close();

        return songList;
    }

    public void deleteSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SONG, SONG_PLAYER_ID + " = ?",
                new String[] { String.valueOf(song.getSongPlayerId()) });
        db.close();
    }

    public int getSongCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SONG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int ret = cursor.getCount();
        cursor.close();
        db.close();
        return ret;
    }

    /* SONGS END */

}
