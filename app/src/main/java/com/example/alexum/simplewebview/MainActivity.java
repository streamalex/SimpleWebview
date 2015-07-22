package com.example.alexum.simplewebview;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends ActionBarActivity {

    WebView webview;
    EditText editText;
    Button btnGo, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.webView);
        webview.setWebViewClient(new WebViewClient());
        WebSettings set = webview.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        webview.loadUrl(getResources().getString(R.string.default_url));

        editText = (EditText) findViewById(R.id.editText);
        btnGo = (Button) findViewById(R.id.button);
        btnReset = (Button) findViewById(R.id.button2);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateURL();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("http://");
                editText.setSelection(editText.getText().length());
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    updateURL();
                    return true;
                }
                return false;
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    void updateURL()
    {
        String urlStr = editText.getText().toString();
        if (!urlStr.startsWith("http://") && !urlStr.startsWith("https://")) {
            urlStr = "http://" + urlStr;
            editText.setText(urlStr);
            editText.setSelection(editText.getText().length());
        }
        webview.loadUrl(urlStr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
