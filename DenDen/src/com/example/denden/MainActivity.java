package com.example.denden;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//表示文字列
		String strContact = null;
		//「ArrayAdapter」クラスを利用しオブジェクトを生成
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			this,android.R.layout.simple_list_item_1);
		//Cursorの作成(コンタクトリストから全件取得)
		Cursor c = managedQuery(
			ContactsContract.Contacts.CONTENT_URI,
			null,null,null,null);
		while (c.moveToNext()) {
			//コンタクトIDを取得
			String id;
			id = c.getString(
				c.getColumnIndex(
					ContactsContract.Contacts._ID));
			String[] where_args = { id };
			//名前を取得
			String name = c.getString(c.getColumnIndex("display_name"));
			//取得したIDと名前をstrContactに追記
			strContact = id + " " + name;
			//電話番号を取得(WHERE条件にCONTACT_IDを指定)
			Cursor phones = managedQuery(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ",
				where_args,null);
				while (phones.moveToNext()) {
					//電話番号を取得
					String phoneNumber = phones.getString(
						phones.getColumnIndex(
						ContactsContract.CommonDataKinds.Phone.NUMBER));
					//取得した電話番号をstrContactに追記
					strContact = strContact + "＼n "+ phoneNumber;
					
					}
					phones.close();
					
					
					//strContactをアダプタにデータセット
					adapter.add(strContact);
				}
				//カーソルを閉じる
				c.close();
				//リストビュー
				ListView listView = (ListView) findViewById(R.id.listview);
				
				//リストビューのアダプタを設定する
				listView.setAdapter(adapter);
				
	}
				
}

