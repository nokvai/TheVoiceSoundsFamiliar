 var sound_path = "file:///android_asset/www/sound/";
 var music_beginner_path = "file:///android_asset/www/music/beginner/";
 var music_advance_path = "file:///android_asset/www/music/advance/";
 var music_expert_path = "file:///android_asset/www/music/expert/";
 var music_reward_path = "file:///android_asset/www/music/reward/";

 function clickSound() {
    setAudio(sound_path + "buttonpush.mp3");
    setAudioVolume(getVol());
    playAudio();
 }

 function volNum(n) {
    if (n == 0) { return 0.0; }
    else if (n == 1) { return 0.1; }
    else if (n == 2) { return 0.2; }
    else if (n == 3) { return 0.3; }
    else if (n == 4) { return 0.4; }
    else if (n == 5) { return 0.5; }
    else if (n == 6) { return 0.6; }
    else if (n == 7) { return 0.7; }
    else if (n == 8) { return 0.8; }
    else if (n == 9) { return 0.9; }
    else if (n == 10) { return 1.0; }
 }

 function getVol() {
    return volNum( mygame.getAllSettingsVolume() );
 }

 function playBeginnerMusic(music_name) {
    setAudio(music_beginner_path + music_name);

    setAudioVolume(getVol());
    playAudio();
 }

 function playAdvanceMusic(music_name) {
    setAudio(music_advance_path + music_name);
    setAudioVolume(getVol());
    playAudio();
 }

 function playExpertMusic(music_name) {
    setAudio(music_expert_path + music_name);
    setAudioVolume(getVol());
    playAudio();
 }

 function playRewardMusic(music_name) {
    setAudio(music_reward_path + music_name);
    setAudioVolume(getVol());
    playAudio();
 }
