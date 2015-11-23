package com.example.asd;

import com.example.asd.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.SeekBar.OnSeekBarChangeListener;
import at.abraxas.amarino.Amarino;

public class control extends Activity implements
		OnSeekBarChangeListener {

	private static final String TAG = "NTL_Bluetooth_Car";

	/*
	 * TODO: change the address to the address of your Bluetooth module and
	 * ensure your device is added to Amarino 
	 * �n�N�o�̧אּ�ۤv�ؼ��Ť��Ϊ�mac��m�I
	 */

	private String DEVICE_ADDRESS = "00:13:03:13:80:22"; // �o�O310����

	// private static final String DEVICE_ADDRESS = "00:12:01:30:02:69";
	// //�o�Oxbee BT

	final int DELAY = 150;
	int power_Val;
	long lastChange;
	SeekBar power_SB;
	TextView powerval_Text;
	EditText setmac_ET;
	int red, green, blue,B;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control);

		Amarino.connect(this, DEVICE_ADDRESS);
		power_SB = (SeekBar) findViewById(R.id.seekBar1);
		power_SB.setOnSeekBarChangeListener(this);

		powerval_Text = (TextView) findViewById(R.id.textView1);
		setmac_ET = (EditText) findViewById(R.id.editText1);
		
		// �H�U�Obutton�ŧi��listener
		Button btnSetmac = (Button) findViewById(R.id.close);
		btnSetmac.setTag(0);
		btnSetmac.setOnClickListener(btnOnClick);

		Button close = (Button) findViewById(R.id.close);
		close.setTag(1);
		close.setOnClickListener(btnOnClick);
		
		Button btnForward = (Button) findViewById(R.id.opean);
		btnForward.setTag(2);
		btnForward.setOnClickListener(btnOnClick);

		Button btnBack = (Button) findViewById(R.id.Sp1);
		btnBack.setTag(3);
		btnBack.setOnClickListener(btnOnClick);

		Button btnLeft = (Button) findViewById(R.id.Sp2);
		btnLeft.setTag(4);
		btnLeft.setOnClickListener(btnOnClick);

		Button btnRight = (Button) findViewById(R.id.start);
		btnRight.setTag(5);
		btnRight.setOnClickListener(btnOnClick);

		Button btnStop = (Button) findViewById(R.id.shotdown);
		btnStop.setTag(6);
		btnStop.setOnClickListener(btnOnClick);
		
		Button add = (Button) findViewById(R.id.upC);
		add.setTag(7);
		add.setOnClickListener(btnOnClick);

		Button mid = (Button) findViewById(R.id.dnC);
		mid.setTag(8);
		mid.setOnClickListener(btnOnClick);

		Button ClBack = (Button) findViewById(R.id.ClBack);
		ClBack.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				control.this.finish();
			}
		});
	}

	private Button.OnClickListener btnOnClick = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch ((Integer) v.getTag()) {
			case 0:
				SetMacOnClick();
				break;
			case 1:
				close();
				break;
			case 2:
				opean();
				break;
			case 3:
				sp1();
				break;
			case 4:
				sp2();
				break;
			case 5:
				start1();
				break;
			case 6:
				shotdown();
				break;
			case 7:
				add();
				break;
			case 8:
				mid();
				break;
			}

		}
	};

	public void SetMacOnClick() { // �]�wMAC���s���U���᪺�ʧ@
		Amarino.disconnect(this, DEVICE_ADDRESS);
		DEVICE_ADDRESS = setmac_ET.toString();
		setmac_ET.setHint(DEVICE_ADDRESS);
		Amarino.connect(this, DEVICE_ADDRESS);
	}

	// ����AlertDialog
	/*
	 * private void ShowAlertDialog() { Builder MyAlertDialog = new
	 * AlertDialog.Builder(this); MyAlertDialog.setTitle("���D");
	 * MyAlertDialog.setMessage("�ڬO���e"); MyAlertDialog.show(); }
	 */

	@Override
	protected void onStart() {
		super.onStart();

		// load last state
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		power_Val = prefs.getInt("power_Val", 0);
		red = prefs.getInt("red", 0);
        green = prefs.getInt("green", 0);
        blue = prefs.getInt("blue", 0);
        B= prefs.getInt("B", 0);
		// set seekbars and feedback color according to last state
		/*
		 * redSB.setProgress(red); greenSB.setProgress(green);
		 * blueSB.setProgress(blue);
		 

		power_SB.setProgress(power_Val);

		// colorIndicator.setBackgroundColor(Color.rgb(red, green, blue));

		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
				}
				Log.d(TAG, "update data");
				updateAlldata();
			}
		}.start();
*/
	}

	@Override
	protected void onStop() {
		super.onStop();
		// save state
		PreferenceManager.getDefaultSharedPreferences(this).edit()
		/*
		 * .putInt("red", red) .putInt("green", green) .putInt("blue", blue)
		 */
		.putInt("power_Val", power_Val).commit();

		// stop Amarino's background service, we don't need it any more
		Amarino.disconnect(this, DEVICE_ADDRESS);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// do not send to many updates, Arduino can't handle so much
		if (System.currentTimeMillis() - lastChange > DELAY) {
			updateState(seekBar);
			lastChange = System.currentTimeMillis();
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		lastChange = System.currentTimeMillis();
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		updateState(seekBar);
	}

	private void updateState(final SeekBar seekBar) {

		switch (seekBar.getId()) {
		
		 //case R.id.SeekBarRed: red = seekBar.getProgress(); updateRed();
		  //break;
		 
		case R.id.seekBar1:
			power_Val = seekBar.getProgress();

			powerval_Text.setText(Integer.toString(power_Val)); // �Npower_Val�૬���textbox�̭�

			update_PowerVal();

			break;
		
		 // case R.id.SeekBarBlue: blue = seekBar.getProgress(); updateBlue();
		 //break;
		 
		}

		update_PowerVal();
		// provide user feedback
		// colorIndicator.setBackgroundColor(Color.rgb(red, green, blue));
	}

	private void updateAlldata() { // �o��ΨӰe�X���O��arduino
		// send state to Arduino
		// updateRed();
		update_PowerVal();

		// updateBlue();
	}

	private void update_PowerVal() {
		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'A', power_Val);
	}

	private void close() {
		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'A', red);
	}
	private void opean() {
		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'B', red);
	}

	private void sp1() {
		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'C', B);
	}

	private void sp2() {
		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'D', green);
	}

	private void start1() {
		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'E', power_Val);
	}

	private void shotdown() {
		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'F', blue);
	}
	private void add() {
		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'G', power_Val);
	}

	private void mid() {
		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'H', blue);
	}


}
