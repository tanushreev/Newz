package com.example.tanushree.newz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tanushree on 06/01/17.
 */

public class NewzListFragment extends Fragment
{
    public interface OnNewzItemSelectedInterface
    {
        void onNewzItemSelected(NewzItem newzItem);
    }

    public static final String TAG = NewzListFragment.class.getSimpleName();

    private List<NewzItem> mItems = new ArrayList<>();
    private RecyclerView mNewzRecyclerView;
    private NewzAdapter mNewzAdapter;
    private SQLiteDatabase mArticlesDatabase;
    private boolean db_flag = true;
    private OnNewzItemSelectedInterface listener;
    private NewzArticleDataSource mDataSource;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (OnNewzItemSelectedInterface) context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_newz_list, container, false);

        mNewzRecyclerView = (RecyclerView) view.findViewById(R.id.rvFragmentNewzList);
        mNewzRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mNewzAdapter = new NewzAdapter(mItems, listener);
        mNewzRecyclerView.setAdapter(mNewzAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mNewzRecyclerView.setLayoutManager(layoutManager);

        // Database
        mDataSource = new NewzArticleDataSource(getActivity());

        // The data in the database will be displayed to the user.
        updateRecyclerView();

        String hackernewsUrl = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";

        OkHttpClient client = new OkHttpClient();

        // Builds a request that the client will sent to the server Hacker-News.

        Request request = new Request.Builder()
                .url(hackernewsUrl)
                .build();

        // Wraps the request inside a call object.

        Call call = client.newCall(request);

        // The enqueue method executes the call asynchronously.
        // The call is executed in the background.
        // Callback is a communication bridge between the background worker thread and the main thread.

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.i(TAG, "Response failed.");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                try
                {
                    if(response.isSuccessful())
                    {
                        Log.i(TAG, "Response successful.");
                        String jsonDataFromServer = response.body().string();
                        Log.i(TAG, jsonDataFromServer);
                        parseNews(jsonDataFromServer);

                       if (getActivity() == null)
                            return;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                updateRecyclerView();

                                Log.d(TAG, "Done");
                            }
                        });
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
                catch (JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
            }
        });


        return view;
    }

    // parse the initial JSON data from the Hacker-News server.

    private void parseNews(String jsonDataFromServer) throws JSONException
    {
        JSONArray listOfIds = new JSONArray(jsonDataFromServer);

        int numberOfItems = 16;

        if(listOfIds.length() < 16)
            numberOfItems = listOfIds.length();

        ArrayList<NewzItem> newzItemList = new ArrayList<>();

        for (int i = 0; i < numberOfItems; i++)
        {
            String articleId = listOfIds.getString(i);
            Log.i("ArticleId", articleId);
            String url = "https://hacker-news.firebaseio.com/v0/item/" + articleId + ".json?print=pretty";
            String articleJson = getArticleJson(url);
            if (articleJson != null)
            {
                JSONObject news = new JSONObject(articleJson);
                if (!news.isNull("title") && !news.isNull("url"))
                {
                    String articleHeadline = news.getString("title");
                    String articleUrl = news.getString("url");
                    Log.i("Headline", articleHeadline);
                    //Log.i("Url", articleUrl);
                    String articleHtmlContent = getArticleHtmlContent(articleUrl);
                    if (articleHtmlContent != null)
                    {
                        //Log.i("Article Content", articleHtmlContent);
                        NewzItem newzItem = new NewzItem();
                        newzItem.setArticleId(Integer.parseInt(articleId));
                        newzItem.setHeadline(articleHeadline);
                        newzItem.setArticle(articleHtmlContent);

                        newzItemList.add(newzItem);
                    }
                }
            }
        }

        // Delete old data from the database.
        int rowsDeleted = mDataSource.delete();

        Log.i("Rows Deleted: ", "" + rowsDeleted);

        // Insert into database.
        mDataSource.create(newzItemList);
    }

    private String getArticleJson(String url)
    {
        OkHttpClient client = new OkHttpClient();

        // Builds a request that the client will sent to the server.

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Wraps the request inside a call object.

        Call call = client.newCall(request);

        // Synchronous get
        try {
            Response response = call.execute();
            String articleJson = response.body().string();
            // Log.i("Article JSON", articleJson);
            return articleJson;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getArticleHtmlContent(String url)
    {
        OkHttpClient client = new OkHttpClient();

        // Builds a request that the client will sent to the server.

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Wraps the request inside a call object.

        Call call = client.newCall(request);

        // Synchronous get
        try {
            Response response = call.execute();
            String content = response.body().string();
            return content;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getNewzArticleContent(String url)
    {
        OkHttpClient client = new OkHttpClient();

        // Builds a request that the client will sent to the server.

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Wraps the request inside a call object.

        Call call = client.newCall(request);

        // Synchronous get
        try {
            Response response = call.execute();
            String content = response.body().string();
            //Log.i("HTML", content);
            return content;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRecyclerView()
    {
        // Log.d("Update", "Calling updateRecyclerView() method");

        mItems.clear();

        mItems.addAll(mDataSource.read());

        mNewzAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}