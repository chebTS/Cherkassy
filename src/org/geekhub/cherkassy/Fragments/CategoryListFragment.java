package org.geekhub.cherkassy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import org.geekhub.cherkassy.R;

/**
 * Created with IntelliJ IDEA.
 * User: shu
 * Date: 13.03.13
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */
public class CategoryListFragment extends SherlockFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_list_frag,container,false);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.category_list_menu,menu);
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
    }
}
