package com.example.Weather.City;

import com.example.mainsurface.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class CityActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_city);

		ProvinceFragment pFragment = new ProvinceFragment();
		CityFragment cFragment =new CityFragment();
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.add(R.id.prov_fragment,pFragment, "prov_frag");
		transaction.add(R.id.city_fragment,cFragment, "city_frag");
		transaction.commit();
	}

}
