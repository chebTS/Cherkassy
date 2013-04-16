package org.geekhub.cherkassy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.activity.ItemListActivity;
import org.geekhub.cherkassy.helpers.JSONDataLoading;
import org.geekhub.cherkassy.helpers.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CategoryListFragment extends SherlockFragment{
	ViewPager pager;
    ArrayAdapter<String> adapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_list_frag,container,false);
    }


    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
        ParseToDB();
        
        pager = (ViewPager)getView().findViewById(R.id.pager);
//        ImageView img = new ImageView(getSherlockActivity());
//        img.setBackgroundDrawable(getResources().getDrawable(R.drawable.ck1));
//        pager.addView(img);
//        img = new ImageView(getSherlockActivity());
//        img.setBackgroundDrawable(getResources().getDrawable(R.drawable.ck2));
//        pager.addView(img);
//        img = new ImageView(getSherlockActivity());
//        img.setBackgroundDrawable(getResources().getDrawable(R.drawable.ck3));
//        pager.addView(img);
        
        //It needs refactoring
        TextView textView1 = (TextView) getView().findViewById(R.id.fastfoods);
        textView1.setOnClickListener(onButtonClick);

        TextView textView2 = (TextView) getView().findViewById(R.id.others);
        textView2.setOnClickListener(onButtonClick);

        TextView textView3 = (TextView) getView().findViewById(R.id.hotels);
        textView3.setOnClickListener(onButtonClick);

        TextView textView4 = (TextView) getView().findViewById(R.id.cinemas);
        textView4.setOnClickListener(onButtonClick);

        TextView textView5 = (TextView) getView().findViewById(R.id.restaurants);
        textView5.setOnClickListener(onButtonClick);

        TextView textView6 = (TextView) getView().findViewById(R.id.universities);
        textView6.setOnClickListener(onButtonClick);
    }

    private View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), ItemListActivity.class);
            switch (view.getId()){
                case R.id.fastfoods:
                    intent.putExtra("category","fast-food");
                    break;
                case R.id.restaurants:
                    intent.putExtra("category","restorans");
                    break;
                case R.id.cinemas:
                    intent.putExtra("category","movies");
                    break;
                case R.id.hotels:
                    intent.putExtra("category","hotels");
                    break;
                case R.id.universities:
                    intent.putExtra("category","universities");
                    break;
                case R.id.others:
                    intent.putExtra("category","other");
                    break;
            }
            startActivity(intent);
        }
    };

    private void ParseToDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONParser.Parse(new JSONObject(JSONDataLoading.getJSONData()),getActivity());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
