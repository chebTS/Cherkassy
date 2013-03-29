package org.geekhub.cherkassy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import com.actionbarsherlock.app.SherlockFragment;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.helpers.JSONDataLoading;
import org.geekhub.cherkassy.helpers.JSONParser;
import org.geekhub.cherkassy.objects.Const;
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
        /*adapter = new ArrayAdapter<String>(getActivity(), R.layout.grid_view, R.id.tvText, Const.MENU_LIST);
        GridView gvMain = (GridView) getActivity().findViewById(R.id.listMain);
        gvMain.setAdapter(adapter);

        gvMain.setNumColumns(gvMain.AUTO_FIT);*/
        ParseToDB();
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
