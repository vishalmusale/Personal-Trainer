package fitness.apps.musale.vishal.personaltrainer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import static fitness.apps.musale.vishal.personaltrainer.StepDetector.registerListener;

public class Home extends AppCompatActivity implements SensorEventListener{

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private int steps_Count=0;
    private int total_Steps_Count=0;
    private TextView tv;
    private SensorManager sensor_Manager;
    private Sensor sensor_sCounter;
    private Sensor sensor_sDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tv = (TextView) findViewById(R.id.steps);
        sensor_Manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor_sCounter = sensor_Manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensor_sDetector = sensor_Manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensor_Manager.registerListener(this, sensor_sCounter, SensorManager.SENSOR_DELAY_FASTEST);
        sensor_Manager.registerListener(this, sensor_sDetector, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensor_Manager.unregisterListener(this, sensor_sCounter);
        sensor_Manager.unregisterListener(this, sensor_sDetector);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensor_Manager.unregisterListener(this, sensor_sCounter);
        sensor_Manager.unregisterListener(this, sensor_sDetector);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        //float[] values = event.values;

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steps_Count+= event.values.length;
            tv.setText("Step Counter Detected : " + steps_Count);
            Toast.makeText(this, steps_Count,
                    Toast.LENGTH_LONG).show();
        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            if (total_Steps_Count < 1) {
                // initial value
                total_Steps_Count = (int) event.values[0];
            }

            // Calculate steps taken based on first counter value received.
            steps_Count = (int) event.values[0] - total_Steps_Count;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
