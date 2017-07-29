package com.tedla.amanuel.eagleapp;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTab extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public HomeTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Vacancy");
        View root = inflater.inflate(R.layout.fragment_home_tab, container, false);
        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        viewPager = (ViewPager) root.findViewById(R.id.view_pager);
        viewPager.setAdapter(new CustomFragmentPageAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(TTS.myTTS !=null){
            TTS.myTTS.stop();
        }
        AppSingleton.getInstance(getActivity()).cancelPendingRequests(FreeVacancy.OPEN_VACANCY_LIST);
        AppSingleton.getInstance(getActivity()).cancelPendingRequests(PaidVacancy.PAID_VACANCY_LIST);




    }
}
