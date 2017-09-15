package com.example.WanFragment.AddCard;

import com.example.mainsurface.MainActivity;
import com.example.mainsurface.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class CardForRead extends LinearLayout {
	private Context context;
	public CardForRead(Context context) {
		super(context);
		this.context = context;
	}
	private void AddView(){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view =inflater.inflate(R.layout.add_card, null);
		this.addView(view, MainActivity.getScreenWidth(), MainActivity.getScreenWidth());
	}
	
}
