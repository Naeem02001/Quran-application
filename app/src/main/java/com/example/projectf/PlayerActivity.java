package com.example.projectf;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;


public class PlayerActivity extends AppCompatActivity {

    private TextView tvTitle, tvCurrent, tvTotal;
    private SeekBar seekBar;
    private Button btnPlayPause;

    private MediaPlayer mediaPlayer;
    private final android.os.Handler handler = new android.os.Handler();
    private final Runnable progressRunnable = new Runnable() {
        @Override public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int pos = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(pos);
                tvCurrent.setText(format(pos));
                handler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        tvTitle = findViewById(R.id.tvTitle);
        tvCurrent = findViewById(R.id.tvCurrent);
        tvTotal = findViewById(R.id.tvTotal);
        seekBar  = findViewById(R.id.seekBar);
        btnPlayPause = findViewById(R.id.btnPlayPause);

        SurahItem item = getIntent().getParcelableExtra(AudioListActivity.EXTRA_ITEM);
        if (item == null) {
            finish();
            return;
        }

        tvTitle.setText(item.getNumber() + ". " + item.getName() + " - " + item.getAyatCount() + " آيات");

        mediaPlayer = MediaPlayer.create(this, item.getRawResId());
        if (mediaPlayer == null) { finish(); return; }

        int total = mediaPlayer.getDuration();
        seekBar.setMax(total);
        tvTotal.setText(format(total));
        tvCurrent.setText(format(0));

        btnPlayPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlayPause.setText("تشغيل");
                handler.removeCallbacks(progressRunnable);
            } else {
                mediaPlayer.start();
                btnPlayPause.setText("إيقاف مؤقت");
                handler.post(progressRunnable);
            }
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            // Reset UI on completion
            seekBar.setProgress(seekBar.getMax());
            tvCurrent.setText(tvTotal.getText());
            btnPlayPause.setText("تشغيل");
            handler.removeCallbacks(progressRunnable);
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                    tvCurrent.setText(format(progress));
                }
            }
            @Override public void onStartTrackingTouch(SeekBar sb) {}
            @Override public void onStopTrackingTouch(SeekBar sb) {}
        });
    }

    private String format(int millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                (TimeUnit.MILLISECONDS.toSeconds(millis) % 60));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlayPause.setText("تشغيل");
        }
        handler.removeCallbacks(progressRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(progressRunnable);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
