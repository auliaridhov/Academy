package com.example.academy.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.academy.data.CourseEntity;
import com.example.academy.data.ModuleEntity;
import com.example.academy.data.source.AcademyRepository;
import com.example.academy.utils.DataDummy;

import java.util.List;

public class DetailCourseViewModel extends ViewModel {
    private String courseId;
    private AcademyRepository academyRepository;
    public DetailCourseViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public void setSelectedCourse(String courseId) {
        this.courseId = courseId;
    }

    public LiveData<CourseEntity> getCourse() {
        return academyRepository.getCourseWithModules(courseId);
    }

    public LiveData<List<ModuleEntity>> getModules() {
        return academyRepository.getAllModulesByCourse(courseId);
    }
}
