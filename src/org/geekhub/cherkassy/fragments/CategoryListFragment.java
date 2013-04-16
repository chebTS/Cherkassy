package org.geekhub.cherkassy.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.activity.ItemListActivity;
import org.geekhub.cherkassy.helpers.HomeAdapter;
import org.geekhub.cherkassy.helpers.JSONDataLoading;
import org.geekhub.cherkassy.helpers.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class CategoryListFragment extends SherlockFragment{
	private ViewPager pager;
	//private  ArrayAdapter<String> adapter;
    private boolean pagerMoved = false;
    private static final long ANIM_VIEWPAGER_DELAY = 3000;
    private Handler h = new Handler();
    
    private Runnable animateViewPager = new Runnable() {
        public void run() {
            if (!pagerMoved) {
            	if (pager.getCurrentItem() != pager.getChildCount()){
            		pager.setCurrentItem(pager.getCurrentItem()+1, true);
            	}else{
            		pager.setCurrentItem(0, true);
            	}
                h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
            }
        }
    };
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_list_frag,container,false);
    }

    

    @Override
	public void onPause() {
		super.onPause();
		if (h != null) {
            h.removeCallbacks(animateViewPager);
        }
	}



//	@Override
//	public void onStop() {
//		super.onStop();
//		if (h != null) {
//            h.removeCallbacks(animateViewPager);
//        }
//	}



	@Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
        ParseToDB();
        
        pager = (ViewPager)getView().findViewById(R.id.pager);
        List<Integer> drawList = new ArrayList<Integer>();
        drawList.add(R.drawable.ck1);
        drawList.add(R.drawable.ck2);
        drawList.add(R.drawable.ck3);
        HomeAdapter adapter = new HomeAdapter(getSherlockActivity().getSupportFragmentManager(), drawList);
        try{
        	pager.setAdapter(adapter);
        }catch (IllegalStateException e){
        	e.printStackTrace();
        }
        
        h.removeCallbacks(animateViewPager);
        h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
        
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
            if (h != null) {
                h.removeCallbacks(animateViewPager);
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
