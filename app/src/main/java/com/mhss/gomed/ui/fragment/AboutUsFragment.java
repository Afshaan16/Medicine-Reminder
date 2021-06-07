package com.mhss.gomed.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mhss.gomed.R;

public class AboutUsFragment extends Fragment {

    private ProgressDialog progress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_us, container, false);
        final WebView mWebView = root.findViewById(R.id.w_health);

        mWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + "https://growthcrate.in/GoMed%20Synopsis-1.pdf");

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