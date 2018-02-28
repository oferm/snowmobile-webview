package com.snowmobile;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = new WebView(this);
        setContentView(webView);

        // Enable Javascript
        webView.getSettings().setJavaScriptEnabled(true);

        // Add a WebViewClient
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                // Inject CSS when page is done loading
                injectCSS(url);
                super.onPageFinished(view, url);
            }
        });

        // Load a webpage
        webView.loadUrl("http://www.snowheads.com");
    }

    /**
     * Inject CSS method: read style.css from assets folder
     * Append stylesheet to document head
     * @param url The url that is being rendered
     */
    private void injectCSS(String url) {
        try {
            String fileName = getCssFile(url);
            InputStream inputStream = getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    protected String getCssFile(String url) {
        String result = null;
        Pattern viewTopicPattern = Pattern.compile("http:\\/\\/(www\\.)?snowheads.com\\/ski-forum\\/(viewtopic.php(\\?.*)?)");
        Pattern skiFormIndexPattern = Pattern.compile("http:\\/\\/(www\\.)?snowheads.com\\/ski-forum\\/(index.php(\\?.*)?)?");
        Pattern viewForumAndSnowReportsPattern = Pattern.compile("http:\\/\\/(www\\.)?snowheads.com\\/ski-forum\\/((viewforum.php|snow_reports.php)(\\?.*)?)?");
        Pattern indexPattern = Pattern.compile("http:\\/\\/(www\\.)?snowheads.com\\/(mx\\/index.php)?((\\?.*)?)?");

        if (viewTopicPattern.matcher(url).matches()) {
            /* CSS for viewtopic.php */
            result = "css/viewtopic.css";
        }
        else if (skiFormIndexPattern.matcher(url).matches()) {
            /* CSS for ski-forum/index.php */
            result = "css/forumlist.css";
        }
        else if (viewForumAndSnowReportsPattern.matcher(url).matches()) {
            /* CSS for viewforum.php and snow_reports.php */
            result = "css/viewforum.css";
        }
        else if (indexPattern.matcher(url).matches()) {
            /* CSS for index.php and mx/index.php */
            result = "css/start.css";
        }


        return result;
    }
}
