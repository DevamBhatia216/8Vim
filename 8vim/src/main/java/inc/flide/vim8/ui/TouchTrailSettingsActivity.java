package inc.flide.vim8.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import inc.flide.vim8.R;
import top.defaults.colorpicker.ColorPickerView;

public class TouchTrailSettingsActivity extends AppCompatActivity {


    SeekBar seekBar_thickness_position;
    TextView trail_thickness_textview_value;

    SeekBar seekbar_opacity_position;
    TextView trail_opacity_textview_value;

    ImageView back_arrow;

    ColorPickerView colorPickerView;
    View pickedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchtrailsettings_layout);

        back_arrow = findViewById(R.id.back_arrow_button);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TouchTrailSettingsActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });


        colorPickerView = findViewById(R.id.colorPicker);
        pickedColor = findViewById(R.id.pickedColor);

        colorPickerView.subscribe((color, fromUser, shouldPropagate) -> {

            pickedColor.setBackgroundColor(color);
            //set color for trail
            SharedPreferences sp_color_selection = getSharedPreferences(getString(R.string.basic_preference_file_name), Activity.MODE_PRIVATE);
            SharedPreferences.Editor sharedPreferencesEditor_color_selection = sp_color_selection.edit();
            sharedPreferencesEditor_color_selection.putInt(getString(R.string.storing_colorCode_value_in_sharedPreference),color);
            sharedPreferencesEditor_color_selection.apply();
        });

        SharedPreferences sharedPreferences_colorbar_position = getSharedPreferences(getString(R.string.basic_preference_file_name), Activity.MODE_PRIVATE);
        int colorCode = sharedPreferences_colorbar_position.getInt(getString(R.string.storing_colorCode_value_in_sharedPreference),0);

        colorPickerView.setInitialColor(colorCode);


//         For Trail Thickness
        trail_thickness_textview_value = findViewById(R.id.set_thickness_value);
        seekBar_thickness_position = findViewById(R.id.seekbar_set_thickness);

        SharedPreferences sharedPreferences_thickness = getSharedPreferences(getString(R.string.basic_preference_file_name), Activity.MODE_PRIVATE);
        int max_trail_radius = sharedPreferences_thickness.getInt(getString(R.string.storing_thickness_seekbar_value_in_sharedPreference),0);

        Log.d("devam",max_trail_radius + "");
        int seekbar_current_value = max_trail_radius - 10;

        trail_thickness_textview_value.setText(String.format(Locale.US, "%d", seekbar_current_value));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar_thickness_position.setMin(1);
        }

        seekBar_thickness_position.setProgress(seekbar_current_value);
        seekBar_thickness_position.setMax(20);


        seekBar_thickness_position.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                trail_thickness_textview_value.setText(String.format(Locale.US, "%d", progress));
                seekBar.setProgress(progress);

                int i = 10;
                int thickness_value = i + progress;

                SharedPreferences sp_thickness_value = getSharedPreferences(getString(R.string.basic_preference_file_name), Activity.MODE_PRIVATE);
                SharedPreferences.Editor sharedPreferencesEditor_thickness_value = sp_thickness_value.edit();
                sharedPreferencesEditor_thickness_value.putInt(getString(R.string.storing_thickness_seekbar_value_in_sharedPreference),thickness_value);
                sharedPreferencesEditor_thickness_value.apply();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//       For Trail Opacity
        trail_opacity_textview_value = findViewById(R.id.set_opacity_value_textview);
        seekbar_opacity_position = findViewById(R.id.seekBar_set_opacity);

        SharedPreferences sp_opacity_value = getSharedPreferences(getString(R.string.basic_preference_file_name), Activity.MODE_PRIVATE);
        int current_opacity_value = sp_opacity_value.getInt(getString(R.string.storing_opacity_seekbar_value_in_sharedPreference),0);
        Log.d("bhatia",current_opacity_value + "");
        int current_seekbar_text_value = (current_opacity_value - 40)/40;
        trail_opacity_textview_value.setText(String.format(Locale.US, "%d", current_seekbar_text_value));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekbar_opacity_position.setMin(1);
        }
        seekbar_opacity_position.setProgress(current_seekbar_text_value);

        seekbar_opacity_position.setMax(20);
        seekbar_opacity_position.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                seekbar_opacity_position.setProgress(progress);
                trail_opacity_textview_value.setText(String.format(Locale.US, "%d", progress));
                int j = progress * 40;
                int opacity_value = 40 + j;
                SharedPreferences sp_opacity_value = getSharedPreferences(getString(R.string.basic_preference_file_name), Activity.MODE_PRIVATE);
                SharedPreferences.Editor sharedPreferencesEditor_opacity_value = sp_opacity_value.edit();
                sharedPreferencesEditor_opacity_value.putInt(getString(R.string.storing_opacity_seekbar_value_in_sharedPreference),opacity_value);
                sharedPreferencesEditor_opacity_value.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}