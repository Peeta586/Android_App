package com.example.util;

import java.util.Calendar;


public class JYUtil {
//	private final static String[] Rokuyou = {"��ʤ","����","��ؓ","����","��","���"};
	private final static int[] solarMonth1 = {31,28,31,30,31,30,31,31,30,31,30,31};
	private final static String[] Gan = {"��","��","��","��","��","��","��","��","��","��"};
	private final static String[] Zhi = {"��","��","��","î","��","��","��","δ","��","��","��","��"};
	private final static String[] jcName0 = {"��","��","��","ƽ","��","ִ","��","Σ","��","��","��","��"};
	private final static String[] jcName1 ={"��","��","��","��","ƽ","��","ִ","��","Σ","��","��","��"};
	private final static String[] jcName2 = {"��","��","��","��","��","ƽ","��","ִ","��","Σ","��","��"};
	private final static String[] jcName3 ={"��","��","��","��","��","��","ƽ","��","ִ","��","Σ","��"};
	private final static String[] jcName4 ={"��","��","��","��","��","��","��","ƽ","��","ִ","��","Σ"};
	private final static String[] jcName5 ={"Σ","��","��","��","��","��","��","��","ƽ","��","ִ","��"};
	private final static String[] jcName6 ={"��","Σ","��","��","��","��","��","��","��","ƽ","��","ִ"};
	private final static String[] jcName7 = {"ִ","��","Σ","��","��","��","��","��","��","��","ƽ","��"};
	private final static String[] jcName8 = {"��","ִ","��","Σ","��","��","��","��","��","��","��","ƽ"};
	private final static String[] jcName9 = {"ƽ","��","ִ","��","Σ","��","��","��","��","��","��","��"};
	private final static String[] jcName10 ={"��","ƽ","��","ִ","��","Σ","��","��","��","��","��","��"};
	private final static String[] jcName11 ={"��","��","ƽ","��","ִ","��","Σ","��","��","��","��","��"};
//	private final static String[] Zhi2 = {"�Ӳ��ʲ�","�󲻹ڴ�","��������","î������","��������","�Ȳ�Զ��","�粻ɻ��","δ����ҩ","�겻����","�ϲ����","�粻��Ȯ","������Ȣ"};
	
	
//	private final static int[] sTermInfo ={0,21208,42467,63836,85337,107014,128867,150921,173149,195551,218072,240693,263343,285989,308563,
//		331033,353350,375494,397447,419210,440795,462224,483532,504758};
	//private final static String[] nStr1={"��","һ","��","��","��","��","��","��","��","��","ʮ"}; 
	
	private String y;
	private String j;
	private Calendar calendar = Calendar.getInstance();
	private Lunar lunar = new Lunar();
	public JYUtil(){
		
	}
	private void jcr(String d){
		if(d=="��")
		{
			y="����.����.����.����.����"; 
			j ="����.����.��Ȣ.�ɲ�";
		}
		if(d=="��") {y="����.�Ʋ�.����.��ж.��լ";j="���.����.����.���.̽��";}
		if(d=="��") {y="��.����.����.����.����";j="��ҩ.��ҽ.����.����.Ǩ��";}
		if(d=="ƽ") {y="����.�޷�.Ϳ��.������ȡ";j="����.��լ.��Ȣ.����.����";}
		if(d=="��") {y="����.��ȯ.����.ǩԼ.����";j="��ֲ.��ҵ.����.��.�촬";}
		if(d=="ִ") {y="��.����.����.���.��Լ";j="����.����.���.Զ��";}
		if(d=="��") {y="��ҽ.����.����.������ȡ";j="����.����.����.����.����";}
		if(d=="Σ") {y="��Ӫ.����.���.����.����";j="�Ǹ�.�д�.����.��լ.����";}
		if(d=="��") {y="��.��ѧ.����.��ҽ.�ɷ�";j="����.����.����";}
		if(d=="��") {y="����.���.ǩԼ.��Ȣ.����";j="����.����.����.��լ.����";}
		if(d=="��") {y="�Ʋ�.���.����.���.��ְ"; j="����.����.���";}
		if(d=="��") {y="����.����.�ղ�.����";j="���.����.����.��Ȣ.����";}
	}
	private String CalConv2(int yy,int mm,int dd,int y,int d,int m,int dt,int nm,int nd){
		String dy = d+""+dd;
		if((yy==0 && dd==6)||(yy==6 && dd==0)||(yy==1 && dd==7)||(yy==7 && dd==1)||(yy==2 && dd==8)||(yy==8 && dd==2)||(yy==3 && dd==9)
				||(yy==9 && dd==3)||(yy==4 && dd==10)||(yy==10 && dd==4)||(yy==5 && dd==11)||(yy==11 && dd==5)){
			return "��ֵ���� ���²���";
		}else if((mm==0 && dd==6)||(mm==6 && dd==0)||(mm==1 && dd==7)||(mm==7 && dd==1)||(mm==2 && dd==8)||(mm==8 && dd==2)||(mm==3 && dd==9)
				||(mm==9 && dd==3)||(mm==4 && dd==10)||(mm==10 && dd==4)||(mm==5 && dd==11)||(mm==11 && dd==5)) {
			return "��ֵ���� ���²���";
			
		}else if((y==0 && dy=="911")||(y==1 && dy=="55")||(y==2 && dy=="111")||(y==3 && dy=="75")||(y==4 && dy=="311")||(y==5 && dy=="95")
				||(y==6 && dy=="511")||(y==7 && dy=="15")||(y==8 && dy=="711")||(y==9 && dy=="35")) {
			return "��ֵ��˷ ���²���";
			
		}else if((m==1 && dt==13)||(m==2 && dt==11)||(m==3 && dt==9)||(m==4 && dt==7)||(m==5 && dt==5)||(m==6 && dt==3)||(m==7 && dt==1)||(m==7 && dt==29)||(m==8 && dt==27)
				||(m==9 && dt==25)||(m==10 && dt==23)||(m==11 && dt==21)||(m==12 && dt==19)) {
			return "��ֵ�ʮ���� ���²���";
		}else{
			return "";
		}
	}	
	private int solarDays(int y,int m){
		if(m == 1){
			return (((y%4 == 0) && (y%100 != 0) || (y%400 == 0))? 29: 28);
		}else{
			return (solarMonth1[m]);
		}
	}
	private String cyclical(int num){
		return Gan[num%10]+Zhi[num%12];
	}
	private String cyclical6(int num,int num2){
		if (num==0) return (jcName0[num2]);
		if (num==1) return(jcName1[num2]);
		if (num==2) return(jcName2[num2]);
		if (num==3) return(jcName3[num2]);
		if (num==4) return(jcName4[num2]);
		if (num==5) return(jcName5[num2]);
		if (num==6) return(jcName6[num2]);
		if (num==7) return(jcName7[num2]);
		if (num==8) return(jcName8[num2]);
		if (num==9) return(jcName9[num2]);
		if (num==10) return(jcName10[num2]);
		if (num==11) return(jcName11[num2]);
		return "";
	}
	private int length;
	private int firstWeek;
	private String jy;
	public void calendar(int y,int m,int d){
		Calendar sDbj;
		Lunar lDObj;
		int lY,lM = 1,lY2,lM2,term2,firstNode,dayCyclical,lD=1,lX=0,lD2,cs1;
		boolean lL;
		String cY,cM;
		calendar.set(y, m, 1, 0, 0, 0);
		lunar.setDate(calendar.getTime());
		sDbj =calendar;
		this.length = solarDays(y, m);
		this.firstWeek = sDbj.get(Calendar.DAY_OF_MONTH);
		if(m<2){
			cY = cyclical(y-1900+36-1);
			lY2 = (y-1900+36-1);
		}else{
			cY = cyclical(y-1900+36);
			lY2 = (y-1900+36);
		}
		term2 = Lunar.getSolarTermDay(y, 2);
		firstNode = Lunar.getSolarTermDay(y, m*2);
		
		cM =cyclical((y-1900)*12 +m +12);
		lM2 = (y-1900)*12 +m +12;
		dayCyclical = (int)(Lunar
				.UTC(y, m, 1, 0, 0, 0) / 86400000 + 25567 + 10);
		for(int i =0 ; i<this.length;i++){
			if(lD>lX){
				calendar.set(y, m, i+1);
				lDObj = new Lunar(calendar.getTime());
				lY = lDObj.getLunarYear();
				lM = lDObj.getLunarMonth();
				lD = lDObj.getLunarDay();
				lL = lDObj.isLeap();
				lX = lL ? Lunar.getLunarLeapMonth(lY):Lunar.getLunarMonthDays(lY,lM);	
			}
			if(m == 1 && (i+1) == term2){
				lY2 = (y-1900+36);
			}
			if((i+1)== firstNode){
				lM2 = (y-1900)*12+m+13;
			}
			lD2 = (dayCyclical+i);
			cs1 =i+1;
			String value1 = CalConv2(lY2%12, lM2%12, (lD2)%12, lY2%10, (lD2)%10, lM, lD-1, m+1, cs1);
			String value2 = cyclical6(lM2%12, (lD2)%12);
			jy ="";
			if(!value1.equals("")){
				jy = value1;
			}else{
				jcr(value2);
			}
			if(i == d)
				break;
		}
	}
	public String getY() {
		return y;
	}
	public String getJ() {
		return j;
	}
	public String getJy() {
		return jy;
	}
}
