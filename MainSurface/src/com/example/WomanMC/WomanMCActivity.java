package com.example.WomanMC;

import com.example.MainFragment.WanFragment.Month.DatePickerDialogFrag;
import com.example.MainFragment.WanFragment.Month.TabFragmentWan;
import com.example.mainsurface.R;
import com.example.mainsurface.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class WomanMCActivity extends Activity {
	private Button search, Reset, Btnback, mc_browse;
	private Spinner zhouqi;
	private Spinner xingjing;
	private TextView yearText;
	private TextView monthText;
	private TextView dayText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mc);
		Init();
	}

	private void Init() {
		yearText = (TextView) findViewById(R.id.mc_y);
		monthText = (TextView) findViewById(R.id.mc_m);
		dayText = (TextView) findViewById(R.id.mc_d);
		search = (Button) findViewById(R.id.mc_search);
		Reset = (Button) findViewById(R.id.mc_reset);
		Btnback = (Button) findViewById(R.id.back_mc);
		mc_browse = (Button) findViewById(R.id.mc_browse);
		Btnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		search.setOnClickListener(new MyOnclick());
		Reset.setOnClickListener(new MyOnclick());
		mc_browse.setOnClickListener(new MyOnclick());
		/**
		 * …Ë÷√÷‹∆⁄
		 */
		zhouqi = (Spinner) findViewById(R.id.mc_spinner1);
		xingjing = (Spinner) findViewById(R.id.mc_spinner2);
		zhouqi.setOnItemSelectedListener(new MyOnItemSelected());
		xingjing.setOnItemSelectedListener(new MyOnItemSelected());
	}

	class MyOnclick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mc_search:
				break;
			case R.id.mc_reset:
				break;
			case R.id.mc_browse:
				
				break;
			}
		}

	}

	class MyOnItemSelected implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			switch (parent.getId()) {
			case R.id.mc_spinner1:

				break;
			case R.id.mc_spinner2:
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	}

}
