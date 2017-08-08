package com.tedla.amanuel.eagleapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class About extends Fragment {
    private ListView aboutList;


    public About() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("About");
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        setHasOptionsMenu(true);
        aboutList = (ListView) root.findViewById(R.id.aboutList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.aboutList));
        aboutList.setAdapter(adapter);
        aboutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                openCompanyDetail(position);
            }

        });

        return root;
    }

    private void openCompanyDetail(int position) {
        if(position == 0)
        {
            Intent intent = new Intent(getActivity(), CompanyInfo.class);
            this.startActivity(intent);
        }
        else if(position == 1){
            Intent intent = new Intent(getActivity(), ContactUs.class);
            this.startActivity(intent);
        }

    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_narration).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

}
