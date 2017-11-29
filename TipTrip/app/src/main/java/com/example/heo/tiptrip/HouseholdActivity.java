package com.example.heo.tiptrip;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.inputmethodservice.InputMethodService.InputMethodImpl;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.toString;

import java.lang.Exception;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;


public class HouseholdActivity extends AppCompatActivity {

    float count = 0;
    float currentMoney = 0;
    float startMoney = 0;
    String country = "일본";
    String currentAlphabet = null;
    float exchangeMoneyRate = 0;
    private final static String address = "http://fx.kebhana.com/fxportal/jsp/RS/DEPLOY_EXRATE/fxrate_B_v2.html";
    static String USD = null;
    static String Yen = null;
    static String EUR = null;
    static String CNY = null;
    static void exchange() {
        Document doc = null;
        try {
            doc = Jsoup.connect(address).parser(Parser.htmlParser()).timeout(10000).get();
        }catch(Exception e) {
            e.printStackTrace();
        }
        if(USD == null) {
            USD = doc.select("td").eq(1).text();
        }
        else
            return;

        if(Yen == null) {
            Yen = doc.select("td").eq(4).text();
        }
        else
            return;

        if(EUR == null)
            EUR = doc.select("td").eq(7).text();
        else
            return;

        if(CNY == null)
            CNY = doc.select("td").eq(10).text();
        else
            return;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household);

        //Intent intent = getIntent();
        //country = intent.getExtras().get("country").toString();

        final ArrayList<String> item = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,item);

        final ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        new Thread(){
            public void run(){
                exchange();
            }
        }.start();

         final TextView spendMoney = (TextView)findViewById(R.id.spend_Money);
        final TextView RestMoney = (TextView)findViewById(R.id.Rest_Money);

        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage("총예산을 먼저 입력해주세요");
        alert_confirm.setPositiveButton("확인",null);
        AlertDialog alert = alert_confirm.create();
        alert.setIcon(R.drawable.icon);
        alert.setTitle("Tiptrip");
        if(currentMoney == 0 && startMoney == 0)
            alert.show();



        Button addButton =(Button)findViewById(R.id.AddButton);
        addButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                EditText subject = (EditText)findViewById(R.id.subject);
                EditText number = (EditText)findViewById(R.id.number);
                CheckBox minus = (CheckBox)findViewById(R.id.Plus_MinuscheckBox);
                String subjectitem = subject.getText().toString();
                String numberitem = number.getText().toString();
                String minusitem = "-";

                if(minus.isChecked() == false)
                    minusitem = "+";

                if(subjectitem!=null || subjectitem.trim().length()>0){
                    if(numberitem!= null || numberitem.trim().length()>0) {
                        item.add(subjectitem.trim()+ "\n" + minusitem.concat(numberitem));
                        adapter.notifyDataSetChanged();
                        subject.setText("");
                    }
                }

                if(minusitem == "+")
                    startMoney += Float.parseFloat(numberitem);
                else
                    currentMoney += Float.parseFloat(numberitem);


                if(country.equals("홍콩")) {
                    exchangeMoneyRate = Float.parseFloat(CNY);
                    currentAlphabet = "HK$";
                }
                else if(country.equals("일본")) {
                    exchangeMoneyRate = Float.parseFloat(Yen);
                    currentAlphabet = "￥";
                }
                else if(country.equals("미국")) {
                    exchangeMoneyRate = Float.parseFloat(USD);
                    currentAlphabet = "$";
                }
                else if(country.equals("한국")) {
                    exchangeMoneyRate = 1;
                    currentAlphabet = "\\";
                }
                else {
                    exchangeMoneyRate = Float.parseFloat(EUR);
                    currentAlphabet = "€";
                }


                    spendMoney.setText("쓴돈" + "\n" + currentMoney + currentAlphabet + "\n"
                            + exchangeMoneyRate * currentMoney + "원");
                    RestMoney.setText("남은돈" + "\n" + (startMoney - currentMoney) + currentAlphabet + "\n"
                            + exchangeMoneyRate * (startMoney - currentMoney) + "원");

            }
        });

    }


}

