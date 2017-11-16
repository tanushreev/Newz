package com.example.tanushree.newz;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

// Created on 31/12/16.

// Important: Regular Fragments should be paired with the Activity class and support Fragments
// should be paired with AppCompatActivity class.

public class MainActivity extends AppCompatActivity implements
        NewzListFragment.OnNewzItemSelectedInterface
{
    public static final String NEWZ_LIST_FRAGMENT = "newz_list_fragment";
    public static final String NEWZ_ARTICLE_FRAGMENT = "newz_article_fragment";
    Toolbar tbMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbMainActivity = (Toolbar) findViewById(R.id.tbMainActivity);
        setSupportActionBar(tbMainActivity);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        NewzListFragment savedFragment = (NewzListFragment) fragmentManager
                .findFragmentByTag(NEWZ_LIST_FRAGMENT);

        if (savedFragment == null) {
            NewzListFragment newzListFragment = new NewzListFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // add the NewzListFragment.
            fragmentTransaction.add(R.id.flPaceHolder, newzListFragment, NEWZ_LIST_FRAGMENT);
            //any changes won't be made permanent until there's a call to commit.
            fragmentTransaction.commit();
        }

        NewzArticleFragment savedFragment1 = (NewzArticleFragment) fragmentManager
                .findFragmentByTag(NEWZ_ARTICLE_FRAGMENT);

        if(savedFragment1 != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            tbMainActivity.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            });
        }

        /*fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(fragmentManager.getBackStackEntryCount() > 0)
                {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    tbMainActivity.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                }
                else
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }); */
    }

    @Override
    public void onNewzItemSelected(NewzItem newzItem)
    {
        //Toast.makeText(MainActivity.this, newzItem.getHeadline(), Toast.LENGTH_SHORT).show();
        NewzArticleFragment newzArticleFragment = new NewzArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NewzArticleFragment.KEY_ARTICLE, newzItem.getArticle());
        newzArticleFragment.setArguments(bundle);

        final FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.flPaceHolder, newzArticleFragment, NEWZ_ARTICLE_FRAGMENT);
        // Add this transaction to the backstack.
        // (If needed we can provide a name for the transaction through the argument.)
        ft.addToBackStack(null);
        ft.commit();

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(fm.getBackStackEntryCount() > 0)
                {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    tbMainActivity.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                }
                else
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        });
        // to show the up button on the toolbar.
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

   /* @Override
    public boolean onSupportNavigateUp()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return true;
    }*/


}