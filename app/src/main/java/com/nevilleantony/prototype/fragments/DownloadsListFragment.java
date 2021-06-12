package com.nevilleantony.prototype.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nevilleantony.prototype.R;
import com.nevilleantony.prototype.adapters.DownloadsViewAdapter;
import com.nevilleantony.prototype.downloadmanager.FileDownload;

import java.util.List;

public class DownloadsListFragment extends Fragment {
    private List<FileDownload> downloadList;
    private View view;
    public RecyclerView downloadView;

//    public DownloadsListFragment() {
////        this.downloadList = fileDownloadList;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("TAG", "onCreateView event : ");
        View view = inflater.inflate(R.layout.fragment_download_cards_list, container, false);
        downloadView = view.findViewById(R.id.recyclerView);
        downloadView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

}
