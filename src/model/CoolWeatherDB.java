package model;

import java.util.ArrayList;
import java.util.List;
import model.City;

import db.WeatherOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	public static final String DB_NAME = "WeatherRain";
	public static final int VERSION = 1;
	public static CoolWeatherDB coolweatherdb;
	private SQLiteDatabase db;

	private CoolWeatherDB(Context context) {
		WeatherOpenHelper dbhelper = new WeatherOpenHelper(context, DB_NAME,
				null, VERSION);
		db = dbhelper.getWritableDatabase();
	}

	public synchronized static CoolWeatherDB getInstance(Context context) {
		if (coolweatherdb == null) {
			coolweatherdb = new CoolWeatherDB(context);
		}
		return coolweatherdb;
	}

	/** ��provinceʵ���洢�����ݿ� **/
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/* �����ݿ��ȡȫ������ʡ�ݵ���Ϣ */
	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province1 = new Province();
				province1.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province1.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province1.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				list.add(province1);
			} while (cursor.moveToNext());
		}
		return list;

	}

	/* ��������Ϣ��ŵ����ݿ��� */
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}

	/* �����ݿ��ȡĳʡ�����еĳ�����Ϣ�� */
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?",
		new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst()) {
		do {
		City city = new City();
		city.setId(cursor.getInt(cursor.getColumnIndex("id")));
		city.setCityName(cursor.getString(cursor
		.getColumnIndex("city_name")));
		city.setCityCode(cursor.getString(cursor
		.getColumnIndex("city_code")));
		city.setProvinceId(provinceId);
		list.add(city);
		} while (cursor.moveToNext());
		}
		
		
		return list;
		
	}
	/**
	* ��Countyʵ���洢�����ݿ⡣
	*/
	public void savecountry(Country country)
	{
		if(country!=null)
		{
			ContentValues values=new ContentValues();
			values.put("Country_name", country.getCountyName());
			values.put("county_code", country.getCountyCode());
			values.put("city_id", country.getCityId());
			db.insert("Country", null, values);
		}
	}
	/**
	* �����ݿ��ȡĳ���������е�����Ϣ��
	*/
	public List<Country> loadCounties(int cityId)
	{
		List<Country> list=new ArrayList<Country>();
		Cursor cursor = db.query("County", null, "city_id = ?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
			Country country = new Country();
			country.setId(cursor.getInt(cursor.getColumnIndex("id")));
			country.setCountyName(cursor.getString(cursor
			.getColumnIndex("county_name")));
			country.setCountyCode(cursor.getString(cursor
			.getColumnIndex("county_code")));
			country.setCityId(cityId);
			list.add(country);
	
		
	}while (cursor.moveToNext());
		}
			return list;
}
}
