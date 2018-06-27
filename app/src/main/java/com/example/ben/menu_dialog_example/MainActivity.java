package com.example.ben.menu_dialog_example;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Context context;
    EditText ogrenci;
    EditText universite;
    EditText fakulte;

    String textogrenci;
    String textuniversite;
    String textfakulte;

    AlertDialog.Builder builderSingle;

    String screen = "baslangic";
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        ogrenci = (EditText) findViewById(R.id.ogrenci);
        universite = (EditText) findViewById(R.id.universite);
        fakulte = (EditText) findViewById(R.id.fakulte);

        textogrenci = ogrenci.getText().toString();
        textuniversite = universite.getText().toString();
        textfakulte = fakulte.getText().toString();

        updateLanguage("tr");
        setHint();

        builderSingle = new AlertDialog.Builder(MainActivity.this);

        // hangi texte uzun süre basılırsa ilgili dialog açılacak
        ogrenci.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OpenOgrenciDialog();
                return true;
            }
        });
        universite.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OpenUniversiteDialog();
                return true;
            }
        });
        fakulte.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OpenFakulteEnstituDialog();
                return true;
            }
        });
    }

    // ekran yatay - dikey tutulduğunda dil default değere dönmemesi için gerekli işlemler yapıldı.
    // uygulamama açıldığında screen değişkeni başlangıç değerine atandı.
    // menüden türkçe seçilmişse "turkce", ingilizce seçilmişse "ingilizce" değerine atandı.
    // buna göre gerekli kontroller yapıldı.
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if (screen == "turkce"){
            updateLanguage("tr");
            setHint();
        }
        else if (screen == "ingilizce"){
            updateLanguage("en");
            setHint();
        }
    }

    // fonksiyona "tr" ya da "en" olarak parametre gönderiyorum ve buna göre gerekli yerlerde çevirme işlemi yapılıyor.
    public void updateLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    // setHint değerine atanan değerleri string.xml den çektim.
    public void setHint(){
        universite.setHint(R.string.hintuniversite);
        fakulte.setHint(R.string.hintfakulte);
        ogrenci.setHint(R.string.hintogrenci);
    }

    // openOgrenciDialog ve OpenUniversiteDialog'ta list oluşturup bunu adapte ettim ve listten seçilen elemanı texte yazdırdım.
    // ilk olarak öğrenci seçileceği için üniversite ve fakulte textlerini dolu olma ihtimaline karşı temizledim.
    public void OpenOgrenciDialog(){

        final ArrayAdapter<String> Ogrenciler = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        Ogrenciler.addAll("Ahmet ÇALIK","Buse SAĞLAM","Caner KORKMAZ","Demet AYDIN");
        builderSingle.setAdapter(Ogrenciler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textogrenci = Ogrenciler.getItem(which);
                ogrenci.setText(textogrenci);
                universite.setText("");
                fakulte.setText("");
                textuniversite = "";
                textfakulte = "";
            }
        });
        builderSingle.show();
    }

    public void OpenUniversiteDialog(){

        final ArrayAdapter<String> Universiteler = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        Universiteler.addAll("Anadolu Üniversitesi","Orta Doğu Teknik Üniversitesi","İstanbul Üniversitesi","Marmara Üniversitesi","Yıldız Teknik Üniversitesi");
        builderSingle.setAdapter(Universiteler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textuniversite = Universiteler.getItem(which);
                if(!textogrenci.equals("")){
                    universite.setText(textuniversite);
                }
                else {
                    Toast.makeText(MainActivity.this, "İlk olarak öğrenciyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builderSingle.show();
    }

    // OpenFakulteDialog ve OpenEnstituDialog'ta da listlerdeki elemanları adapte ettim ve seçilen elemanı texte yazdırdım.
    public void OpenFakulteDialog(){

        final ArrayAdapter<String> Fakulteler = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        Fakulteler.addAll("İİBF","Güzel Sanatlar","Mühendislik","Tıp");
        builderSingle.setAdapter(Fakulteler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textfakulte = Fakulteler.getItem(which);
                fakulte.setText(textfakulte);
            }
        });
        builderSingle.show();
    }

    public void OpenEnstituDialog(){

        final ArrayAdapter<String> Enstitüler = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        Enstitüler.addAll("Eğitim Bilimleri","Fen Bilimleri","Sosyal Bilimler");
        builderSingle.setAdapter(Enstitüler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textfakulte = Enstitüler.getItem(which);
                fakulte.setText(textfakulte);
            }
        });
        builderSingle.show();
    }

    // fakulte dialog mu enstitü dialog mu açılacak sorununu bir dialoga elemanları fakülte ve enstitü olan bir list atadım.
    // fakülte elemanı seçilirse fakülte dialogu, enstitü seçilirse enstitü dialogu görüntülenecek.
    public void OpenFakulteEnstituDialog(){

        final ArrayAdapter<String> FakulteEnstitü = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        FakulteEnstitü.addAll("Fakülte","Enstitü");
        builderSingle.setAdapter(FakulteEnstitü, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = FakulteEnstitü.getItem(which);
                if(!textuniversite.equals("") && !textogrenci.equals("")){
                    if (strName == "Fakülte"){
                        OpenFakulteDialog();
                    }
                    else {
                        OpenEnstituDialog();
                    }                }
                else if(textogrenci.equals("")){
                    Toast.makeText(MainActivity.this,"İlk olarak öğrenciyi ardından üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builderSingle.show();
    }

    // menü oluşturma
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // dil seçeneği değiştiğinde screen değişkeni seçilen dile göre değer alacak ve buna göre menü itemleri yenilenecek.
    private void updateMenuItems(Menu menu){
        if (screen == "turkce") {
            menu.findItem(R.id.ogrencimenu).setTitle(R.string.titleogrenci);
            menu.findItem(R.id.universitemenu).setTitle(R.string.titleuniversite);
            menu.findItem(R.id.fakultemenu).setTitle(R.string.titlefakulte);
        }
        else if(screen == "ingilizce"){
            menu.findItem(R.id.ogrencimenu).setTitle(R.string.titleogrenci);
            menu.findItem(R.id.universitemenu).setTitle(R.string.titleuniversite);
            menu.findItem(R.id.fakultemenu).setTitle(R.string.titlefakulte);
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        updateMenuItems(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    // menüden itemler seçildikçe seçili iteme göre gerekenler yapılacak.
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.itemTurkish:
                updateLanguage("tr");
                setHint();
                screen = "turkce";
                updateMenuItems(menu);
                return true;

            case R.id.itemEnglish:
                updateLanguage("en");
                setHint();
                screen = "ingilizce";
                updateMenuItems(menu);
                return true;

            case R.id.ogrenci1:
                textogrenci = item.getTitle().toString();
                ogrenci.setText(textogrenci);
                universite.setText("");
                fakulte.setText("");
                textuniversite = "";
                textfakulte = "";
                return true;

            case R.id.ogrenci2:
                textogrenci = item.getTitle().toString();
                ogrenci.setText(textogrenci);
                universite.setText("");
                fakulte.setText("");
                textuniversite = "";
                textfakulte = "";
                return true;

            case R.id.ogrenci3:
                textogrenci = item.getTitle().toString();
                ogrenci.setText(textogrenci);
                universite.setText("");
                fakulte.setText("");
                textuniversite = "";
                textfakulte = "";
                return true;

            case R.id.ogrenci4:
                textogrenci = item.getTitle().toString();
                ogrenci.setText(textogrenci);
                universite.setText("");
                fakulte.setText("");
                textuniversite = "";
                textfakulte = "";
                return true;

            case R.id.universite1:
                if(!textogrenci.equals("")){
                    textuniversite = item.getTitle().toString();
                    universite.setText(textuniversite);
                }
                else {
                    Toast.makeText(MainActivity.this, "İlk olarak öğrenciyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.universite2:
                if(!textogrenci.equals("")){
                    textuniversite = item.getTitle().toString();
                    universite.setText(textuniversite);
                }
                else {
                    Toast.makeText(MainActivity.this, "İlk olarak öğrenciyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.universite3:
                if(!textogrenci.equals("")){
                    textuniversite = item.getTitle().toString();
                    universite.setText(textuniversite);
                }
                else {
                    Toast.makeText(MainActivity.this, "İlk olarak öğrenciyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.universite4:
                if(!textogrenci.equals("")){
                    textuniversite = item.getTitle().toString();
                    universite.setText(textuniversite);
                }
                else {
                    Toast.makeText(MainActivity.this, "İlk olarak öğrenciyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.universite5:
                if(!textogrenci.equals("")){
                    textuniversite = item.getTitle().toString();
                    universite.setText(textuniversite);
                }
                else {
                    Toast.makeText(MainActivity.this, "İlk olarak öğrenciyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.fakulte1:
                if(!textogrenci.equals("") && !textuniversite.equals("")){
                    fakulte.setText(item.getTitle());
                }
                else if(textogrenci.equals("")){
                    Toast.makeText(MainActivity.this,"İlk olarak öğrenciyi ardından üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.fakulte2:
                if(!textogrenci.equals("") && !textuniversite.equals("")){
                    fakulte.setText(item.getTitle());
                }
                else if(textogrenci.equals("")){
                    Toast.makeText(MainActivity.this,"İlk olarak öğrenciyi ardından üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.fakulte3:
                if(!textogrenci.equals("") && !textuniversite.equals("")){
                    fakulte.setText(item.getTitle());
                }
                else if(textogrenci.equals("")){
                    Toast.makeText(MainActivity.this,"İlk olarak öğrenciyi ardından üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.fakulte4:
                if(!textogrenci.equals("") && !textuniversite.equals("")){
                    fakulte.setText(item.getTitle());
                }
                else if(textogrenci.equals("")){
                    Toast.makeText(MainActivity.this,"İlk olarak öğrenciyi ardından üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.enstitu1:
                if(!textogrenci.equals("") && !textuniversite.equals("")){
                    fakulte.setText(item.getTitle());
                }
                else if(textogrenci.equals("")){
                    Toast.makeText(MainActivity.this,"İlk olarak öğrenciyi ardından üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.enstitu2:
                if(!textogrenci.equals("") && !textuniversite.equals("")){
                    fakulte.setText(item.getTitle());
                }
                else if(textogrenci.equals("")){
                    Toast.makeText(MainActivity.this,"İlk olarak öğrenciyi ardından üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.enstitu3:
                if(!textogrenci.equals("") && !textuniversite.equals("")){
                    fakulte.setText(item.getTitle());
                }
                else if(textogrenci.equals("")){
                    Toast.makeText(MainActivity.this,"İlk olarak öğrenciyi ardından üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Üniversiteyi seçiniz!", Toast.LENGTH_SHORT).show();
                }
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

