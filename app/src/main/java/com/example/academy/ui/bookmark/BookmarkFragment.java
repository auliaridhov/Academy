package com.example.academy.ui.bookmark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.academy.R;
import com.example.academy.data.CourseEntity;
import com.example.academy.viewmodel.ViewModelFactory;

import java.util.List;


public class BookmarkFragment extends Fragment implements BookmarkFragmentCallback {
    private RecyclerView rvBookmark;
    private ProgressBar progressBar;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookmark = view.findViewById(R.id.rv_bookmark);
        progressBar = view.findViewById(R.id.progress_bar);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            BookmarkViewModel viewModel = new ViewModelProvider(this, factory).get(BookmarkViewModel.class);

            BookmarkAdapter adapter = new BookmarkAdapter(this);
            progressBar.setVisibility(View.VISIBLE);
            viewModel.getBookmarks().observe(this, courses -> {
                progressBar.setVisibility(View.GONE);
                adapter.setCourses(courses);
                adapter.notifyDataSetChanged();
            });

            rvBookmark.setLayoutManager(new LinearLayoutManager(getContext()));
            rvBookmark.setHasFixedSize(true);
            rvBookmark.setAdapter(adapter);
        }
    }
    @Override
    public void onShareClick(CourseEntity course) {
        if (getActivity() != null) {
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType(mimeType)
                    .setChooserTitle("Bagikan aplikasi ini sekarang.")
                    .setText(getResources().getString(R.string.share_text, course.getTitle()))
                    .startChooser();
        }
    }
}