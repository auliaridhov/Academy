package com.example.academy.ui.detail;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.academy.ui.reader.CourseReaderActivity;
import com.example.academy.R;
import com.example.academy.data.CourseEntity;
import com.example.academy.data.ModuleEntity;
import com.example.academy.viewmodel.ViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DetailCourseActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE = "extra_course";
    private Button btnStart;
    private TextView textTitle;
    private TextView textDesc;
    private TextView textDate;
    private ImageView imagePoster;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progress_bar);
        btnStart = findViewById(R.id.btn_start);
        textTitle = findViewById(R.id.text_title);
        textDesc = findViewById(R.id.text_description);
        textDate = findViewById(R.id.text_date);
        RecyclerView rvModule = findViewById(R.id.rv_module);
        imagePoster = findViewById(R.id.image_poster);

        DetailCourseAdapter adapter = new DetailCourseAdapter();
        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        DetailCourseViewModel viewModel = new ViewModelProvider(this, factory).get(DetailCourseViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String courseId = extras.getString(EXTRA_COURSE);
            if (courseId != null) {
                //viewModel.setSelectedCourse(courseId);
                viewModel.courseModule.observe(this, courseWithModuleResource -> {
                    if (courseWithModuleResource != null) {
                        switch (courseWithModuleResource.status) {
                            case LOADING:
                                progressBar.setVisibility(View.VISIBLE);
                                break;
                            case SUCCESS:
                                if (courseWithModuleResource.data != null) {
                                    progressBar.setVisibility(View.GONE);
                                    adapter.setModules(courseWithModuleResource.data.mModules);
                                    adapter.notifyDataSetChanged();
                                    populateCourse(courseWithModuleResource.data.mCourse);
                                }
                                break;
                            case ERROR:
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
        }

        rvModule.setNestedScrollingEnabled(false);
        rvModule.setLayoutManager(new LinearLayoutManager(this));
        rvModule.setHasFixedSize(true);
        rvModule.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvModule.getContext(), DividerItemDecoration.VERTICAL);
        rvModule.addItemDecoration(dividerItemDecoration);
    }

    private void populateCourse(CourseEntity courseEntity) {
        textTitle.setText(courseEntity.getTitle());
        textDesc.setText(courseEntity.getDescription());
        textDate.setText(getResources().getString(R.string.deadline_date, courseEntity.getDeadline()));

        Glide.with(this)
                .load(courseEntity.getImagePath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(imagePoster);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(DetailCourseActivity.this, CourseReaderActivity.class);
            intent.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, courseEntity.getCourseId());
            startActivity(intent);
        });
    }
}