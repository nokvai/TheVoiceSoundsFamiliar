package com.noknokware.thevoicesoundsfamiliar;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class java2JSAgent {

    Context mContext;

    java2JSAgent(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void showInterstitialAd() {

    }

    //SAMPLE TO HTML JAVASCRIPT mygame.addPlayer();
    @JavascriptInterface
    public String getNewRandomSongs(String level) {
        String [] songs = getAllSongs(level);
        Collections.shuffle(Arrays.asList(songs)); // first shuffle
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 30; i++) {
            sb.append(songs[i] + ",");
        }
        return sb.toString();
    }

    //TEST
    @JavascriptInterface
    public void setAllSongToFinish(int player_id, String level) {

        for (int i=0;i < 30;i++) {
            updateSongByIdLevelIndex(player_id, level, i, 0, 0, 0, 1);
        }

    }

    @JavascriptInterface
    public String getLastOptionSongs(int player_id, String level) {
        String [] existingSongs = getSongs(player_id, level).split(",");
        String [] freshSong = new String[20];
        String [] songs = getAllSongs( level );
        int z = 0;
        for (int k = 0; k < existingSongs.length; k++) {
            String finishedSong = existingSongs[k].split("&")[1];
            //Log.v("existingSongs: "  + (k+1) , finishedSong);
            for (int j = 0; j < songs.length; j++) {
                if (!finishedSong.equalsIgnoreCase(songs[j])) {
                    if (z < 20) {
                        freshSong[z] = songs[j];
                        z++;
                    }
                }
            }
        }

        Collections.shuffle(Arrays.asList(freshSong)); // first shuffle
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 20; i++) {
            sb.append(freshSong[i] + ",");
        }
        return sb.toString();
    }


    @JavascriptInterface
    public String getRewardSongs() {
        String [] songs = getAllRewardSongs();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < songs.length; i++) {
            sb.append(songs[i] + ",");
        }
        return sb.toString();
    }

    private String[] getAllRewardSongs() {
        AssetManager assetManager = mContext.getAssets();
        String [] files = {};
        try { files = assetManager.list("www/music/reward"); } catch (IOException e) {  e.printStackTrace();  }
        return files;
    }

    @JavascriptInterface
    public String getBeginnerHintRemoveAnswer(String song1, String song2, String song3) {
        String [] songs = {song1, song2, song3};
        Collections.shuffle(Arrays.asList(songs)); // first shuffle
        return songs[0];
    }

    @JavascriptInterface
    public String getBeginnerHintGiveOneLetter(String songAnswer) {
        int randomLetter = new Random().nextInt(songAnswer.length());
        String alpha = "" + songAnswer.charAt(randomLetter);
        if (alpha == " ") { alpha = "" + songAnswer.charAt(new Random().nextInt(songAnswer.length())); }
        return alpha;
    }

    @JavascriptInterface
    public String getAdvanceHintGiveOneLetter(String songAnswer) {
        int randomLetter = new Random().nextInt(songAnswer.length());
        String alpha = "" + songAnswer.charAt(randomLetter);
        if (alpha == " ") { alpha = "" + songAnswer.charAt(new Random().nextInt(songAnswer.length())); }
        return alpha;
    }

    @JavascriptInterface
    public String getAdvanceHintGiveTwoLetter(String songtitle, int falseCount, String fio) {
        String ans = "";
        String [] falseInorderStr = fio.split(",");
        int [] falseInorder = new int[falseCount];
        for (int y = 0; y < ( falseInorderStr.length - 1) ; y++) {
            falseInorder[y] = Integer.parseInt( falseInorderStr[y] );
        }

        for (int i = 0; i < falseCount ; i++) {
            ans += songtitle.charAt( falseInorder[i]);
        }
        int randomLetter1 = new Random().nextInt(ans.length());
        int randomLetter2 = new Random().nextInt(ans.length());
        String alpha = "" + ans.charAt(randomLetter1);
        String beta = "" + ans.charAt(randomLetter2);
        if (alpha == " ") { alpha = "" + ans.charAt(new Random().nextInt(ans.length())); }
        if (beta == " ") { beta = "" + ans.charAt(new Random().nextInt(beta.length())); }
        return "Has: " + alpha.toUpperCase() + " and " + beta.toUpperCase();

    }

    @JavascriptInterface
    public static String replaceCharAt(String s, int pos, String c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }


    @JavascriptInterface
    public String getAdvanceQuestionSong(String songtitle) {
        boolean[] titleLengthRandom = {};
        int [] falseInorder;
        int falseCount = 0;
        int charCount = 0;
        String finalTitle = "";

        Random rand = new Random();
        titleLengthRandom = new boolean[songtitle.length()];
        finalTitle = "";
        falseCount = 0;
        charCount = 0;
        for (int i = 0; i <  songtitle.length(); i++) {
            titleLengthRandom[i] = rand.nextBoolean();
            if(songtitle.charAt(i) == '.') {
                titleLengthRandom[i] = true;
            }
            else if(songtitle.charAt(i) == '\'') {
                titleLengthRandom[i] = true;
            }
            if (titleLengthRandom[i] == true) {
                if (songtitle.charAt(i) == ' ') {
                    finalTitle += ' ';
                } else {
                    finalTitle += songtitle.charAt(i);
                }
            } else if (titleLengthRandom[i] == false) {
                if (songtitle.charAt(i) == ' ') {
                    finalTitle += ' ';
                    titleLengthRandom[i] = true;
                } else {
                    finalTitle += '_';
                }
            }
        }
        for (int i = 0; i < songtitle.length(); i++) {
            if (titleLengthRandom[i] == false) {
                falseCount++;
            }
        }
        int cnt = 0;
        falseInorder = new int[falseCount];
        for (int j = 0; j < songtitle.length(); j++) {
            if (titleLengthRandom[j] == false) {
                falseInorder[cnt] = j;
                cnt++;
            }
        }
        StringBuffer sbmain = new StringBuffer();
        StringBuffer p1 = new StringBuffer();
        StringBuffer p2 = new StringBuffer();

        for (int i = 0; i < titleLengthRandom.length; i++) {
            p1.append(titleLengthRandom[i] + "@");
        }

        for (int j = 0; j < falseInorder.length; j++) {
            p2.append(falseInorder[j]+ "$");
        }

            sbmain.append(p1 + ",");
            sbmain.append(p2 + ",");
            sbmain.append(falseCount + ",");
            sbmain.append(finalTitle + ",");

        return sbmain.toString();
    }


    @JavascriptInterface
    public String getExpertQuestionSong(String songtitle) {
        boolean[] titleLengthRandom = {};
        int [] falseInorder;
        int falseCount = 0;
        int charCount = 0;
        String finalTitle = "";

        Random rand = new Random();
        titleLengthRandom = new boolean[songtitle.length()];
        finalTitle = "";
        falseCount = 0;
        charCount = 0;
        for (int i = 0; i <  songtitle.length(); i++) {
            titleLengthRandom[i] = false;//rand.nextBoolean();
            if(songtitle.charAt(i) == '.') {
                titleLengthRandom[i] = true;
            }
            else if(songtitle.charAt(i) == '\'') {
                titleLengthRandom[i] = true;
            }
            if (titleLengthRandom[i] == true) {
                if (songtitle.charAt(i) == ' ') {
                    finalTitle += ' ';
                } else {
                    finalTitle += songtitle.charAt(i);
                }
            } else if (titleLengthRandom[i] == false) {
                if (songtitle.charAt(i) == ' ') {
                    finalTitle += ' ';
                    titleLengthRandom[i] = true;
                } else {
                    finalTitle += '_';
                }
            }
        }
        for (int i = 0; i < songtitle.length(); i++) {
            if (titleLengthRandom[i] == false) {
                falseCount++;
            }
        }
        int cnt = 0;
        falseInorder = new int[falseCount];
        for (int j = 0; j < songtitle.length(); j++) {
            if (titleLengthRandom[j] == false) {
                falseInorder[cnt] = j;
                cnt++;
            }
        }
        StringBuffer sbmain = new StringBuffer();
        StringBuffer p1 = new StringBuffer();
        StringBuffer p2 = new StringBuffer();

        for (int i = 0; i < titleLengthRandom.length; i++) {
            p1.append(titleLengthRandom[i] + "@");
        }

        for (int j = 0; j < falseInorder.length; j++) {
            p2.append(falseInorder[j]+ "$");
        }

        sbmain.append(p1 + ",");
        sbmain.append(p2 + ",");
        sbmain.append(falseCount + ",");
        sbmain.append(finalTitle + ",");

        return sbmain.toString();
    }



    @JavascriptInterface
    public String getBeginnerSongChoices(String correctSong) {
        String [] songs = getAllSongs("b");
        Collections.shuffle(Arrays.asList(songs)); // first shuffle
        StringBuffer sb = new StringBuffer();
        String [] fourSongs = {};
        fourSongs = new String [] { correctSong, songs[0], songs[1], songs[2]};
        Collections.shuffle(Arrays.asList(fourSongs)); // second shuffle

        if (songs[0].equalsIgnoreCase(correctSong)) {
            Collections.shuffle(Arrays.asList(songs));
            fourSongs = new String [] { correctSong, songs[0], songs[1], songs[2]};
            Collections.shuffle(Arrays.asList(fourSongs)); // third shuffle
        } else if (songs[1].equalsIgnoreCase(correctSong)) {
            Collections.shuffle(Arrays.asList(songs));
            fourSongs = new String [] { correctSong, songs[0], songs[1], songs[2]};
            Collections.shuffle(Arrays.asList(fourSongs)); // third shuffle
        } else if (songs[2].equalsIgnoreCase(correctSong)) {
            Collections.shuffle(Arrays.asList(songs));
            fourSongs = new String [] { correctSong, songs[0], songs[1], songs[2]};
            Collections.shuffle(Arrays.asList(fourSongs)); // third shuffle
        }

        for (int i = 0; i < 4; i++) {
            sb.append(fourSongs[i] + ",");
        }
        return sb.toString();
    }


    private String[] getAllSongs(String level) {
        AssetManager assetManager = mContext.getAssets();
        String [] files = {};
        if (level.equalsIgnoreCase("b")) {
            try { files = assetManager.list("www/music/beginner"); } catch (IOException e) {  e.printStackTrace();  }
        } else if (level.equalsIgnoreCase("a")) {
            try { files = assetManager.list("www/music/advance"); } catch (IOException e) {  e.printStackTrace();  }
        } else if (level.equalsIgnoreCase("e")) {
            try { files = assetManager.list("www/music/expert"); } catch (IOException e) {  e.printStackTrace();  }
        }

        return files;
    }




    @JavascriptInterface
    public void msg(String getString) {
        Toast.makeText(mContext, getString, Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void addPlayer(String playerName, String gender, int finalScore) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.addPlayer(new Player(0, playerName, gender, finalScore));
    }

    @JavascriptInterface
    public void updatePlayerNameGender(int playerId, String playerName, String gender) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.updatePlayerNameGender(new Player(playerId, playerName, gender, 0));
    }

    @JavascriptInterface
    public void updatePlayerScore(int playerId, int finalScore) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.updatePlayerScore(new Player(playerId, "", "", finalScore));
    }

    @JavascriptInterface
    public void deletePlayer(int playerId) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.deletePlayer(new Player(playerId, "", "", 0));
    }

    @JavascriptInterface
    public String getPlayer(int playerId) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        Player player = db.getPlayer(playerId);
        StringBuffer str = new StringBuffer();
        str.append(
            player.getPlayerId() + "," +
            player.getPlayerName() + "," +
            player.getGender() + "," +
            player.getFinalScore() +
        ",");
        return str.toString();
    }

    @JavascriptInterface
    public String getAllPlayer() {
        DatabaseHandler db = new DatabaseHandler(mContext);
        List<Player> playerList =  db.getAllPlayer();
        StringBuffer players = new StringBuffer();
        for (int i = 0; i < playerList.size(); i++) {
            players.append(
                    playerList.get(i).getPlayerId() + "&" +
                    playerList.get(i).getPlayerName() + "&" +
                    playerList.get(i).getGender() + "&" +
                    playerList.get(i).getFinalScore() +
                    ",");
        }
        return players.toString();

    }


    @JavascriptInterface
    public String getSongs(int playerId, String level) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        List<Song> songList =  db.getSongs(playerId, level);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < songList.size(); i++) {
            str.append(
                songList.get(i).getSongPlayerId() + "&" +
                songList.get(i).getSongName() + "&" +
                songList.get(i).getSongIndex() + "&" +
                songList.get(i).getSongLevel() + "&" +
                songList.get(i).getSongIsCorrect() + "&" +
                songList.get(i).getSongUsedHint() + "&" +
                songList.get(i).getSongScore() +
                ",");
        }
        return str.toString();

    }

    @JavascriptInterface
    public String getSongByIdLevelIndex(int playerId, String level, int index) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        Song song = db.getSongByIdLevelIndex(playerId, level, index);
        StringBuffer str = new StringBuffer();
        str.append(
                song.getSongPlayerId() + "," +
                song.getSongName() + "," +
                song.getSongIndex() + "," +
                song.getSongLevel() + "," +
                song.getSongIsCorrect() + "," +
                song.getSongUsedHint() + "," +
                song.getSongScore() +
                ",");
        return str.toString();
    }

    @JavascriptInterface
    public void addSong(int songPlayerId, String songName, int songIndex, String songLevel, int songIsCorrect, int songUsedHint, int songScore, int songIsFinished) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.addSong(new Song(songPlayerId, songName, songIndex, songLevel, songIsCorrect, songUsedHint, songScore, songIsFinished));
    }

    @JavascriptInterface
    public void updateSongByIdLevelIndex(int playerId, String level, int index, int songIsCorrect, int songUsedHint, int songScore, int songIsFinished) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.updateSongByIdLevelIndex(playerId, level, index, new Song(0, "", index, level, songIsCorrect, songUsedHint, songScore, songIsFinished));
    }

    @JavascriptInterface
    public String getFinishedSongsByIdLevelIndex(int playerId, String level) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        List<Song> songList =  db.getFinishedSongsByIdLevel(playerId, level);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < songList.size(); i++) {
            str.append(
                    songList.get(i).getSongPlayerId() + "&" +
                            songList.get(i).getSongName() + "&" +
                            songList.get(i).getSongIndex() + "&" +
                            songList.get(i).getSongLevel() + "&" +
                            songList.get(i).getSongIsCorrect() + "&" +
                            songList.get(i).getSongUsedHint() + "&" +
                            songList.get(i).getSongScore() +
                            ",");
        }
        return str.toString();
    }

    @JavascriptInterface
    public void deleteSong(int playerId) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.deleteSong(new Song(playerId, "", 0, "", 0, 0, 0, 0));
    }

    @JavascriptInterface
    public void addSettings(int soundLevel, String theme) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.addSettings(new Settings(0, soundLevel, theme));
    }


    @JavascriptInterface
    public String getAllSettings() {
        DatabaseHandler db = new DatabaseHandler(mContext);
        List<Settings> settingsList =  db.getAllSettings();
        StringBuffer settings = new StringBuffer();
        for (int i = 0; i < settingsList.size(); i++) {
            settings.append(
                    settingsList.get(i).getSettingsId() + "&" +
                            settingsList.get(i).getSoundLevel() + "&" +
                            settingsList.get(i).getTheme() +
                            ",");
        }
        return settings.toString();

    }

    @JavascriptInterface
    public int getAllSettingsVolume() {
        DatabaseHandler db = new DatabaseHandler(mContext);
        List<Settings> settingsList =  db.getAllSettings();
        int ret = 0;
        for (int i = 0; i < settingsList.size(); i++) {
            ret = settingsList.get(i).getSoundLevel();
        }
        return ret;

    }

    @JavascriptInterface
    public int getSettingsCount() {
        DatabaseHandler db = new DatabaseHandler(mContext);
        return db.getSettingsCount();
    }

    @JavascriptInterface
    public void updateSettings(int settingsId, int soundLevel, String theme) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.updateSettings(new Settings(settingsId, soundLevel, theme));
    }

    @JavascriptInterface
    public String getSettings(int id) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        Settings settings = db.getSettings(id);
        StringBuffer str = new StringBuffer();
        str.append(
                settings.getSettingsId() + "," +
                        settings.getSoundLevel() + "," +
                        settings.getTheme() +
                        ",");
        return str.toString();
    }

    private String[] getAllThemes(String level) {
        AssetManager assetManager = mContext.getAssets();
        String[] files = {};
        if (level.equalsIgnoreCase("b")) {
            try { files = assetManager.list("www/image/beginner"); Log.v("Get All Theme", "5"); } catch (IOException e) {  e.printStackTrace(); }
        } else if (level.equalsIgnoreCase("a")) {
            try {  files = assetManager.list("www/image/advance"); Log.v("Get All Theme", "6");  } catch (IOException e) { e.printStackTrace(); }
        } else if (level.equalsIgnoreCase("e")) {
            try { files = assetManager.list("www/image/expert"); Log.v("Get All Theme", "7"); } catch (IOException e) { e.printStackTrace(); }
        }
        return files;
    }

    @JavascriptInterface
    public String getChangeTheme(int player_id) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        String [] current_theme = {};
        String path = "";
        String level_theme = db.getSettings( player_id ).getTheme();
        if (level_theme.toLowerCase().equalsIgnoreCase("beginner")) {
            current_theme = getAllThemes("b");
            path = "file:///android_asset/www/image/beginner/";
        } else if (level_theme.toLowerCase().equalsIgnoreCase("advance")) {
            current_theme = getAllThemes("a");
            path = "file:///android_asset/www/image/advance/";
        } else if (level_theme.toLowerCase().equalsIgnoreCase("expert")) {
            current_theme = getAllThemes("e");
            path = "file:///android_asset/www/image/expert/";
        }
        Collections.shuffle(Arrays.asList(current_theme));
        String theme = "";
        try {

            theme = path + current_theme[0];
        } catch (Exception e){
            theme = path + "None";
        }

        return theme;
    }

}



