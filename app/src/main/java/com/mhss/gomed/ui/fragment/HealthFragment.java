package com.mhss.gomed.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mhss.gomed.R;

public class HealthFragment extends Fragment {

    private ProgressDialog progress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_health_tips, container, false);
        final WebView mWebView = root.findViewById(R.id.w_health);

        mWebView.loadUrl("https://www.gkfacts.in/search/label/Health%20Tips");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        progress = ProgressDialog.show(root.getContext(), "Please Wait...",
                "Data is loading...", true);
        mWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                if (progress != null)
                    progress.dismiss();
            }
        });

        return root;
    }
}