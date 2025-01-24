import librosa
import IPython.display as ipd

x, sr = librosa.load(r"C:\Users\Guest Account\Documents\Rhythm game\music4.wav")
ipd.Audio(x, rate=sr)

beat_frames = librosa.beat.beat_track(y=x, sr=sr, units='frames')[1]

tempo, beat_frames = librosa.beat.beat_track(y=x, sr=sr, bpm=80, units='frames')

beat_times = librosa.frames_to_time(beat_frames, sr=sr)

print(beat_times)







