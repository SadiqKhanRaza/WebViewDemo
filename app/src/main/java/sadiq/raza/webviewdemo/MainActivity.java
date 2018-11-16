package sadiq.raza.webviewdemo;

import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar pb;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView=findViewById(R.id.webView);
        iv=findViewById(R.id.iv);
        if(savedInstanceState==null)
        webView.loadUrl("https://www.google.com");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebviewClient());
        webView.setWebChromeClient(new MyChromeClient());
        pb=findViewById(R.id.pb);

    }
    class MyWebviewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url=request.getUrl().toString();
            view.loadUrl(url);
            return true;
        }
    }

    class MyChromeClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //super.onProgressChanged(view, newProgress);
            pb.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            getSupportActionBar().setTitle(title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            iv.setImageBitmap(icon);
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.backword:
                if(webView.canGoBack())
                    webView.goBack();
                else
                    Toast.makeText(this, "Already on first page", Toast.LENGTH_SHORT).show();
                break;

            case R.id.farword:
                if(webView.canGoBackOrForward(1))
                    webView.goForward();
                else
                    Toast.makeText(this, "Cannot go farword", Toast.LENGTH_SHORT).show();
                break;
            case R.id.refresh:
                webView.reload();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
