package com.example.tanushree.newz;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

// Created on 31/12/16.

// Important: Regular Fragments should be paired with the Activity class and support Fragments
// should be paired with AppCompatActivity class.

public class MainActivity extends AppCompatActivity implements NewzListFragment.OnNewzItemSelectedInterface
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewzListFragment savedFragment = (NewzListFragment) getSupportFragmentManager().findFragmentById(R.id.flPaceHolder);

        if (savedFragment == null) {
            NewzListFragment newzListFragment = new NewzListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // add the NewzListFragment.
            fragmentTransaction.add(R.id.flPaceHolder, newzListFragment);
            //any changes won't be made permanent until there's a call to commit.
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onNewzItemSelected(NewzItem newzItem)
    {
        //Toast.makeText(MainActivity.this, newzItem.getHeadline(), Toast.LENGTH_SHORT).show();
        NewzArticleFragment newzArticleFragment = new NewzArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NewzArticleFragment.KEY_ARTICLE, newzItem.getArticle());
        newzArticleFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.flPaceHolder, newzArticleFragment);
        // Add this transaction to the backstack.
        // (If needed we can provide a name for the transaction through the argument.)
        ft.addToBackStack(null);
        ft.commit();
    }
}
