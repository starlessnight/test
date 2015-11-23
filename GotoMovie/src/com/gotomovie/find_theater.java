package com.gotomovie;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

public class find_theater extends Activity {
	static LatLng MyLocation;
	public GoogleMap map;
	public String startLocation, L1, L2;
	public String endLocation, e1, e2, e3, e4;
	private Handler handler = new Handler();
	private LocationManager locationManager;
	private SensorManager mSensorManager = null;
	private Sensor mSensor = null;
	private GeoPoint currentGeoPoint;
	float azimuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_theater);

		/* 建立LocationManager物件取得系統LOCATION服務 */
		Bundle b = this.getIntent().getExtras();
		e1 = b.getString("address1");
		e2 = b.getString("address2");
		e3 = b.getString("theater");
		e4 = b.getString("theater_name");
		setTitle("導航到:" + e4);
		System.out.println("@1@1@1@1@" + e3);
		endLocation = e3;
		init();
		setLocationManager();
		startDirection();
	}

	private void init() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// �]�w��������v�����A(�ɯ�i�椤���e�{����v���A)
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		// ��o��V�P����
		mSensorManager.registerListener(mSensorEventListener, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		// ��ť��V�P���󪺪��A�A�I�smSensorEventListener ��ť�ƥ�
		MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		map = mf.getMap();
		// �]�wGoogle Maps ��������
		map.setMyLocationEnabled(true);
		// �ҥΩw���I(��e��m)���Ŧ���
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Location mLocation = getLocation(this);
		if (mLocation != null) {
			processLocationUpdated(mLocation);
		}

		startLocation = L2 + "," + L1;

		// ��o�w���������A�è�o��e�g�n�׳]�w���_�l�I��m(startLocation)
	}

	private Location getLocation(Context context) {
		LocationManager locMan = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = locMan
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (location == null) {
			location = locMan
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			return location;
		}
		return location;
	}

	// �^�Ƿ�e�w�쪫�󪺤�k
	private void setLocationManager() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// �����Ǧ�m
		criteria.setCostAllowed(true);
		// ���\���ͭp�O
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		// ��Ӥj���ܡA����W�v��
		criteria.setSpeedRequired(true);
		// �]�w�O�_�ݭn���ѳt�ת���T
		criteria.setAltitudeRequired(true);
		// �]�w�O�_�ݭn���Ѯ�ު���T
		String bestProvider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(bestProvider, 1000, 1,
				locationListener);
		// ��o�̨α��Provider�A�è�o�ثe��m��MyLocation
	}

	private final SensorEventListener mSensorEventListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
				azimuth = event.values[0];
				// ��쨤(0��359) 0=�_, 90=�F, 180=�n, 270=��
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				MyLocation = new LatLng(location.getLatitude(),
						location.getLongitude());
				// ��a�I���ܮɨ�o�g�n�צs��MyLocation�ܼƤ�
				map.moveCamera(CameraUpdateFactory
						.newCameraPosition(new CameraPosition.Builder()
								.target(MyLocation).zoom(19).bearing(azimuth)
								.tilt(60).build()));
				/*
				 * target: �ؼ�(��v�������I) bearing: �w�쨤(����V�P���X�����׭�)
				 * zoom: ��j19 Level tilt: �ɱ׵�
				 */
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// ��GPS�κ����w��\��������
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// ��GPS�κ����w��\��}��
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// �w�쪬�A����
		}
	};

	// �]�w�a�Ϭ�������
	private void startDirection() {
		Thread theard = new Thread() {
			@Override
			public void run() {
				GetDirection(startLocation, endLocation);
			}
		};
		theard.start();
	}

	// �Ұʰ���creatMap(�]�w�a��)�PstartDirection(�}�l�W�����u)�����function
	public void GetDirection(String startLocation, String endLocation) {
		String mapAPI = "http://maps.google.com/maps/api/directions/json?"
				+ "origin={0}&destination={1}&language=zh-TW&sensor=true";
		String url = MessageFormat.format(mapAPI, startLocation, endLocation);
		HttpGet get = new HttpGet(url);
		// �]�wGoogle Directions APIs ���}�P�����ѼơA�øm�J��l��HttpGet����
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(get);
			// ��Google Directions APIs �A�ȵo�X���|�W���ШD
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				// ��o Google Directions APIs �Ҧ��W�����|���������(JSON�榡)
				JSONObject jsonObject = new JSONObject(strResult);
				// �N���JSON�榡����Ƹm�J��l�ƪ�JSON����
				JSONArray routeObject = jsonObject.getJSONArray("routes");
				// ��X�Ĥ@�h���}�C���(routes)
				String polyline = routeObject.getJSONObject(0)
						.getJSONObject("overview_polyline").getString("points");
				// ��Xroutes�}�C���U��overview_polyline ���󤺪�points
				// (�g�s�X�L�᪺��T���e)
				if (polyline.length() > 0) {
					drawPath(decodePoly(polyline));
					/*
					 * �����decodePoly �N�s�X�����|��m�ഫ���� ArrayList<LatLng>
					 * �æ^�ǵ� drawPath function�A�ӵ��w�Ҧ����|�I�ù�{ø�s���u���\��
					 */
				}
			}
		} catch (Exception e) {
			Log.e("map", "MapRoute:" + e.toString());
		}
	}

	private void drawPath(final ArrayList<LatLng> points) {
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				map.addPolyline(new PolylineOptions().addAll(points).width(5)
						.color(Color.BLUE));
				// �N�Ҧ��}�C���|�I(points) �m�J��uø�Ϫ��\�त
				/*for (int i = 0; i < points.size(); i++) {
					map.addCircle(new CircleOptions().center(points.get(i))
							.radius(20).strokeWidth(5).strokeColor(Color.RED));
				}*/
				// �N�Ҧ��}�C���|�I(points) �Хܶ��A�H�Q�M���[��Ҧ��^�Ǫ��W�����|�I
				map.addMarker(new MarkerOptions().position(points.get(0))
						.title("�ڬO�_�I").snippet("�ڬO�_�I"));
				// �ϥ�Marker�ӼХܸ��|���_�I�Apoints.get(0) ->
				// ��Ҧ��}�C���|�I�Ĥ@��(�_�I)��T
				map.addMarker(new MarkerOptions()
						.position(points.get(points.size() - 1))
						.title("�ڬO���I").snippet("�ڬO���I"));
				// �ϥ�Marker�ӼХܸ��|�����I�A
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(points.get(0)).zoom(19).bearing(270).tilt(60)
						.build();
				map.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				// ����Camera�����ʮĪG�A�åH�_�I�������I�A�Y����15.5�A����ɶ���׬�2000
				// ms
			}
		};
		handler.post(r1);
	}

	private ArrayList<LatLng> decodePoly(String encoded) {
		{
			ArrayList<LatLng> poly = new ArrayList<LatLng>();
			int index = 0, len = encoded.length();
			int lat = 0, lng = 0;
			while (index < len) {
				int b, shift = 0, result = 0;
				do {
					b = encoded.charAt(index++) - 63;
					result |= (b & 0x1f) << shift;
					shift += 5;
				} while (b >= 0x20);
				int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
				lat += dlat;
				shift = 0;
				result = 0;
				do {
					b = encoded.charAt(index++) - 63;
					result |= (b & 0x1f) << shift;
					shift += 5;
				} while (b >= 0x20);
				int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
				lng += dlng;
				LatLng p = new LatLng(((lat / 1E5)), ((lng / 1E5)));
				poly.add(p);
			}
			return poly;
		}
	}

	private void processLocationUpdated(Location location) {
		/* 傳入Location物件，取得GeoPoint地理座標 */
		currentGeoPoint = getGeoByLocation(location);

		L1 = String.valueOf(currentGeoPoint.getLongitudeE6() / 1E6);
		L2 = String.valueOf(currentGeoPoint.getLatitudeE6() / 1E6);

	}

	private GeoPoint getGeoByLocation(Location location) {
		GeoPoint gp = null;
		try {
			/* 當Location存在 */
			if (location != null) {
				double geoLatitude = location.getLatitude() * 1E6;
				double geoLongitude = location.getLongitude() * 1E6;
				gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gp;
	}

}
