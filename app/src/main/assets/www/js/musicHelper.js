
    var audio = new Audio();

    function setAudio(source_path) {
       delete audio;
       audio.src = source_path;
    }

    function setAudioVolume(sound_level) {
        audio.volume = sound_level;
    }

    function playAudio() {
       stopAudio();
        if (audio.paused) {
            audio.play();
        }else{
            audio.currentTime = 0
        }
    }
	
	function stopAudio() {
		 audio.pause();
         audio.currentTime = 0;
	}