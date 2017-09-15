package com.example.EditSchedule;

import com.example.Alarm.AlarmforSch;
import com.example.DataBase.MyTodoScheduleDB;
import com.example.mainsurface.R;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author LSHM
 *���๦�ܣ���װ���ճ����ݿ��ɾ��������ֻҪ����context��Ҫɾ����id.
 */
public class DialogForDelete {
	private Context context;
	private String id;
	private Dialog dialog;
	private MyTodoScheduleDB helper =null;
	public DialogForDelete(Context context,String id) {
		this.context =context;
		this.id =id;
		helper =new MyTodoScheduleDB(context);
	}
	public void showDialog(){
		//��dialog��ʵ�ֲ����������ļ�
		dialog = new Dialog(context);
		dialog.setTitle("ȷ��ɾ�����ճ�?");
		dialog.setContentView(R.layout.delete_view_dialog);
		TextView tv =(TextView)dialog.findViewById(R.id.sure_tv);
		tv.setOnClickListener(new MyTvOnClick());
		tv =(TextView)dialog.findViewById(R.id.cancel_tv);
		tv.setOnClickListener(new MyTvOnClick());
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	class MyTvOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.sure_tv:
				SQLiteDatabase db =helper.getWritableDatabase();
				db.delete("Schedule", "id= ?", new String[]{id});
				dialog.dismiss();
				((EditScheduleActivity)context).getFinish("delete");
				//ȡ���������ӣ�id��Ϊ���������룬һ������һ�������𣬱���Ψһ��
				new AlarmforSch(context, Integer.parseInt(id)).cancelAlarm();
				Toast.makeText(context, "ɾ���ɹ�!", Toast.LENGTH_SHORT).show();
				break;
			case R.id.cancel_tv:
				dialog.dismiss();
				//Toast.makeText(context, "ȡ��", Toast.LENGTH_SHORT).show();
				break;
			}
			
		}
	}
	
}
