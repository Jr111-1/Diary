package com.example.diaryclf;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static String DB_NAME = "mydb";


    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleAdapter listAdapter;
    //private Adapter adapter;

    private ArrayList<Map<String, Object>> data;//用来存储单词信息
    private ArrayList<Map<String, Object>> data2;
    private Map<String, Object> item;//用来存储ListView信息
    ;

    private EditText wordEdit;
    private EditText meanEdit;
    private EditText exampleEdit;
    private EditText selectContent;
    private Button add, del, edit, query,Add, sel, diary;
    private ListView listView1;

    private ListView listView2;

    private TextView meanText;
    private TextView exampleText;
    private PopupWindow popupWindow;

    private String  delWord;








    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpenHelper = new DBOpenHelper(this,DB_NAME, null, 1);
        db = dbOpenHelper.getWritableDatabase();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //如果是横屏
            setContentView(R.layout.activity_main_land);


            listView2 = findViewById(R.id.listView2);
            meanText = findViewById(R.id.meanText);
            exampleText = findViewById(R.id.exampleText);

            data = new ArrayList<Map<String, Object>>();
            dbFindAll2();
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Map<String, Object> listItem = (Map<String, Object>) listView2.getItemAtPosition(i);


                    meanText.setText("释义：" +(String) listItem.get("meaning"));
                    exampleText.setText("例句："+(String) listItem.get("example"));



                }
            });

        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main_port);

            diary = (Button) findViewById(R.id.bt_diary);
            diary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbFindAll();
                }
            });


            query = (Button) findViewById(R.id.bt_query);

            query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSelectPopuptWindow();

                }
            });


            listView1 = findViewById(R.id.listview1);
            Add = (Button) findViewById(R.id.Addbtn);
            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddPopuptWindow();
                }
            });

            data = new ArrayList<Map<String, Object>>();
            dbFindAll();

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    Map<String, Object> listItem = (Map<String, Object>) listView1.getItemAtPosition(i);
                    View popupWindow_view = getLayoutInflater().inflate(R.layout.updatepopupwindow, null,
                            false);
                    popupWindow_view.setBackgroundResource(R.drawable.popwindow_shape);
                    // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
                    popupWindow = new PopupWindow(popupWindow_view,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    popupWindow.setClippingEnabled(true);//
                    popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow_shape));
                    wordEdit = (EditText) popupWindow_view.findViewById(R.id.wordEdit);
                    meanEdit = (EditText) popupWindow_view.findViewById(R.id.meanEdit);
                    exampleEdit = (EditText) popupWindow_view.findViewById(R.id.exampleEdit);

                    wordEdit.setText((String) listItem.get("word"));
                    meanEdit.setText((String) listItem.get("meaning"));
                    exampleEdit.setText((String) listItem.get("example"));
                    delWord=wordEdit.getText().toString().trim();

                    edit = (Button) popupWindow_view.findViewById(R.id.bt_edit);
                    del = (Button) popupWindow_view.findViewById(R.id.bt_del);

                    edit.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            dbUpdate();
                            dbFindAll();
                            popupWindow.dismiss();
                        }
                    });

                    del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            delDB();
                            dbFindAll();
                            popupWindow.dismiss();
                        }
                    });

                    View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main_port ,null);
                    popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);


                }
            });



            //打开数据库



            //showList();
            //registerForContextMenu(listView1);
        }















    }



    private void showList() {
        // TODO Auto-generated method stub

        listAdapter = new SimpleAdapter(this, data,
                R.layout.words, new String[]{"word", "meaning"},
                new int[]{R.id.iword, R.id.imean});

        listView1.setAdapter(listAdapter);
    }

    private void showList2() {
        // TODO Auto-generated method stub

        listAdapter = new SimpleAdapter(this, data,
                R.layout.words, new String[]{"word"},
                new int[]{R.id.iword});

        listView2.setAdapter(listAdapter);

    }


    protected void addDB(){

        words word = new words();
        word.setName(wordEdit.getText().toString().trim());
        word.setMeaning(meanEdit.getText().toString().trim());
        word.setExample(exampleEdit.getText().toString().trim());

        ContentValues values = new ContentValues();

        values.put("name", word.getName());
        values.put("meaning", word.getMeaning());
        values.put("example", word.getExample());
        //将信息插入
        db.insert(dbOpenHelper.TB_name, null, values);
//        long rowId = db.insert(dbOpenHelper.TB_name, null, values);
//        if (rowId == -1)
//            Log.i("myDbDemo", "数据插入失败！");
//        else
//            Log.i("myDbDemo", "数据插入成功!" + rowId);

    }
    //读数据库
    protected void dbFindAll() {
        // TODO Auto-generated method stub
        data.clear();
        cursor = db.rawQuery("select * from WordDiary", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String word = cursor.getString(1);
            String meaning = cursor.getString(2);
            String example = cursor.getString(3);
            item = new HashMap<String, Object>();
            item.put("word", word);
            item.put("meaning", meaning);
            item.put("example", example);
            data.add(item);
            cursor.moveToNext();
        }


            showList();

    }


    protected void dbFindAll2() {
        // TODO Auto-generated method stub

        data.clear();
        cursor = db.rawQuery("select * from WordDiary", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String word = cursor.getString(1);
            String meaning = cursor.getString(2);
            String example = cursor.getString(3);
            item = new HashMap<String, Object>();
            item.put("word", word);
            item.put("meaning", meaning);
            item.put("example", example);
            data.add(item);
            cursor.moveToNext();
        }
        showList2();
    }
    //删除方法
    protected void delDB(){

        db.execSQL("DELETE FROM WordDiary WHERE name = '"+delWord+"' ;");

    }


    protected void dbUpdate() {
        ContentValues values = new ContentValues();
        values.put("meaning", meanEdit.getText().toString().trim());
        values.put("example", exampleEdit.getText().toString().trim());
        String[]args={delWord};
        db.update(dbOpenHelper.TB_name, values, "name=?", args);
//        int i = db.update(dbOpenHelper.TB_name, values, "name=?", args);
//        if (i > 0)
//            Log.i("myDbDemo", "数据更新成功！");
//        else
//            Log.i("myDbDemo", "数据未更新");
    }








    protected void showAddPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.addpopwindow, null,
                false);

        popupWindow_view.setBackgroundResource(R.drawable.popwindow_shape);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setClippingEnabled(true);//
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow_shape));
        wordEdit = (EditText) popupWindow_view.findViewById(R.id.wordEdit);
        meanEdit = (EditText) popupWindow_view.findViewById(R.id.meanEdit);
        exampleEdit = (EditText) popupWindow_view.findViewById(R.id.exampleEdit);

        add = popupWindow_view.findViewById(R.id.bt_add);

        add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(wordEdit.getText().length() ==0){
                    showList();
                }else{
                //添加数据
                addDB();
                //更新添加后的数据
                dbFindAll();}
                popupWindow.dismiss();

            }
        });

        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main_port, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

    }

    protected void showSelectPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.selectpopwindow, null,
                false);
        popupWindow_view.setBackgroundResource(R.drawable.popwindow_shape);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setClippingEnabled(true);//
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow_shape));

        selectContent = popupWindow_view.findViewById(R.id.selectContent);

        sel = popupWindow_view.findViewById(R.id.bt_sel);
        sel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               data.clear();
               String current_sql_sel = "SELECT  * FROM WordDiary" +" where name like '%"+selectContent.getText().toString()+"%'";
               cursor = db.rawQuery(current_sql_sel,null);
               cursor.moveToNext();
               while (!cursor.isAfterLast()) {

                   String word = cursor.getString(1);
                   String meaning = cursor.getString(2);
                   String example = cursor.getString(3);
                   item = new HashMap<String, Object>();
                   item.put("word", word);
                   item.put("meaning", meaning);
                   item.put("example", example);
                   data.add(item);
                   cursor.moveToNext();

               }
               showList();
               popupWindow.dismiss();
           }
       });


        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main_port, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

    }

    public  ArrayList<Map<String, Object>> getData(){

        return data;
    }


}