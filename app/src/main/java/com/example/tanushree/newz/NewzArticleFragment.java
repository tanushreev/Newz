package com.example.tanushree.newz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewzArticleFragment extends Fragment {

    public static final String KEY_ARTICLE = "key_article";

    public NewzArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        String article = getArguments().getString(KEY_ARTICLE);
        View view = inflater.inflate(R.layout.fragment_newz_article, container, false);

        //Toolbar tbFragmentNewzArticle = (Toolbar) view.findViewById(R.id.tbFragmentNewzArticle);
        //getActivity().setActionBar(tbFragmentNewzArticle);

        WebView wvArticle = (WebView) view.findViewById(R.id.wvArticle);
        wvArticle.getSettings().setJavaScriptEnabled(true);
        wvArticle.setWebViewClient(new WebViewClient());
        wvArticle.loadData(article, "text/html", "UTF-8");
        return view;
    }

}
