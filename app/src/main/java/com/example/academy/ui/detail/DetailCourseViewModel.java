package com.example.academy.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.academy.data.CourseEntity;
import com.example.academy.data.CourseWithModule;
import com.example.academy.data.ModuleEntity;
import com.example.academy.data.source.AcademyRepository;
import com.example.academy.utils.DataDummy;
import com.example.academy.vo.Resource;

import java.util.List;

public class DetailCourseViewModel extends ViewModel {
    private MutableLiveData<String> courseId = new MutableLiveData<>();
    private AcademyRepository academyRepository;

    public DetailCourseViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public LiveData<Resource<CourseWithModule>> courseModule = Transformations.switchMap(courseId,
            mCourseId -> academyRepository.getCourseWithModules(mCourseId));

    public String getCourseId() {
        return courseId.getValue();
    }

    public void setCourseId(String courseId) {
        this.courseId.setValue(courseId);
    }

    void setBookmark() {
        Resource<CourseWithModule> moduleResource = courseModule.getValue();
        if (moduleResource != null) {
            CourseWithModule courseWithModule = moduleResource.data;
            if (courseWithModule != null) {
                CourseEntity courseEntity = courseWithModule.mCourse;
                final boolean newState = !courseEntity.isBookmarked();
                academyRepository.setCourseBookmark(courseEntity, newState);
            }
        }
    }
}
