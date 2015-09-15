package com.example.smap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.example.smap.MyOrientationListener.OnOrientationListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	private boolean isFirst = true;
	private ImageButton myZoneButton;
	private ImageButton myRadarButton;
	private MapView mMapView = null;
	private BaiduMap mBaiduMap;
	private BitmapDescriptor myIconLocation;// �Զ���λ��ͼƬ
	private LocationMode mCurrentMode = LocationMode.NORMAL;// ��ǰ��λ��ģʽ

	// ��λ���
	private LocationClient mlLocationClient;
	private MyLocationListener mlMyLocationListener;
	private double mLatitude;
	private double mLongtitude;
	private int mXDirection;

	// ���������
	private MyOrientationListener myOrientationListener;

	// ���������
	private BitmapDescriptor myMarkers;

	// �ܱ��״����
	private RadarSearchManager mRadarSearchManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		initButton();
		initView();
		initLocation();
		// initMarker();
		// initRadarSearch();

	}

	/**
	 * ��ʼ����ť
	 */
	private void initButton() {
		myZoneButton = (ImageButton) findViewById(R.id.btn_my_zone);
		myZoneButton.setOnClickListener(new MyButtonListener());
		myRadarButton=(ImageButton) findViewById(R.id.btn_around);
		myRadarButton.setOnClickListener(new MyButtonListener());
	}

	/**
	 * ��ʼ���ܱ���Ϣ
	 */
	private void initRadarSearch() {
		mRadarSearchManager = RadarSearchManager.getInstance();
		// ע�����
		MyRadarNearByListener mRadarNearByListener = new MyRadarNearByListener();
		mRadarSearchManager.addNearbyInfoListener(mRadarNearByListener);
		// �����������
		RadarNearbySearchOption option = new RadarNearbySearchOption()//
				.centerPt(new LatLng(mLatitude, mLongtitude));//
		// .radius(arg0);
		// ���������ѯ

	}

	/**
	 * ��ʼ��������
	 */
	private void initMarker() {

		myMarkers = BitmapDescriptorFactory//
				.fromResource(R.drawable.icon_marka);

	}

	/**
	 * ��ʼ��λ��
	 */
	private void initLocation() {
		mlLocationClient = new LocationClient(this);
		mlMyLocationListener = new MyLocationListener();
		mlLocationClient.registerLocationListener(mlMyLocationListener);

		myIconLocation = BitmapDescriptorFactory
				.fromResource(R.drawable.navi_loc);

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mlLocationClient.setLocOption(option);

		// ��ʼ�����򴫸���
		myOrientationListener = new MyOrientationListener(
				getApplicationContext());
		myOrientationListener
				.setOnOrientationListener(new OnOrientationListener() {
					@Override
					public void onOrientationChanged(float x) {
						mXDirection = (int) x;
					}
				});

	}

	/**
	 * ��ʼ����ͼ��ͼ
	 */
	private void initView() {

		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		mMapView.showZoomControls(false);// �������ſؼ�
		mMapView.removeViewAt(1); // ���ذٶȵ�ͼlogo
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);// ���ñ�����
		mBaiduMap.setMapStatus(msu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.map_common:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			break;
		case R.id.map_satellite:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.map_traffic:
			if (mBaiduMap.isTrafficEnabled()) {
				mBaiduMap.setTrafficEnabled(false);
				item.setTitle("ʵʱ��ͨ(off)");

			} else {
				mBaiduMap.setTrafficEnabled(true);
				item.setTitle("ʵʱ��ͨ(on)");

			}
			break;
		case R.id.map_location: {
			LatLng latLng = new LatLng(mLatitude, mLongtitude);
			MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
			mBaiduMap.animateMapStatus(msu);
		}

			break;
		case R.id.map_loc_normal:
			mCurrentMode = LocationMode.NORMAL;
			break;
		case R.id.map_loc_compass:
			mCurrentMode = LocationMode.COMPASS;
			break;

		case R.id.map_loc_following:
			mCurrentMode = LocationMode.FOLLOWING;
			break;
		case R.id.map_add_marker:
			addOverlay();
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void addOverlay() {
		MarkerOptions options = null;
		// options =
	}

	@Override
	protected void onStart() {
		super.onStart();
		// ������λ
		mBaiduMap.setMyLocationEnabled(true);
		if (!mlLocationClient.isStarted())
			mlLocationClient.start();
		// �������򴫸���
		myOrientationListener.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// �رն�λ
		mBaiduMap.setMyLocationEnabled(false);
		mlLocationClient.stop();
		// �رշ��򴫸���
		myOrientationListener.stop();

	}

	@Override
	protected void onResume() {

		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onPause() {

		super.onPause();
		mMapView.onPause();
	}

	private class MyButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if (v.getId()==R.id.btn_my_zone) {
				startActivity(new Intent(MainActivity.this,LoginActivity.class));
				overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
			}
			else if (v.getId()==R.id.btn_around) {
				startActivity(new Intent(MainActivity.this,RadarActivity.class));
				overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
			}
			
		}

	}

	private class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);

			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

				sb.append(location.getAddrStr());
			}
			MyLocationData data = new MyLocationData.Builder()//
					.direction(mXDirection)//
					.accuracy(location.getRadius())//
					.latitude(location.getLatitude())//
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(data);

			// �����Զ���ͼ��
			MyLocationConfiguration config = new MyLocationConfiguration(
					mCurrentMode, true, myIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);

			// ���¾�γ��
			mLatitude = location.getLatitude();
			mLongtitude = location.getLongitude();

			if (isFirst) {
				LatLng latLng = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(msu);

				isFirst = false;

			}

		}

	}

	private class MyRadarNearByListener implements RadarSearchListener {

		@Override
		public void onGetClearInfoState(RadarSearchError error) {

		}

		@Override
		public void onGetNearbyInfoList(RadarNearbyResult arg0,
				RadarSearchError arg1) {

		}

		@Override
		public void onGetUploadState(RadarSearchError arg0) {

		}

	}
}
