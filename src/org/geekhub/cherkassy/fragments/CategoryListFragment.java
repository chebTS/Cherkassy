package org.geekhub.cherkassy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        //Only for 1:
        TextView textView = (TextView) getView().findViewById(R.id.fastfoods);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ItemListActivity.class);
                intent.putExtra("category","fast-food");
                startActivity(intent);
            }
        });
    }

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
