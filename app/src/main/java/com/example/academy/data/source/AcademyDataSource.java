package com.example.academy.data.source;

import androidx.lifecycle.LiveData;

import com.example.academy.data.CourseEntity;
import com.example.academy.data.CourseWithModule;
import com.example.academy.data.ModuleEntity;
import com.example.academy.vo.Resource;

import java.util.List;

public interface AcademyDataSource {

    LiveData<Resource<List<CourseEntity>>> getAllCourses();

    LiveData<Resource<CourseWithModule>> getCourseWithModules(String courseId);

    LiveData<Resource<List<ModuleEntity>>> getAllModulesByCourse(String courseId);

    LiveData<Resource<ModuleEntity>> getContent(String moduleId);

    LiveData<List<CourseEntity>> getBookmarkedCourses();

    void setCourseBookmark(CourseEntity course, boolean state);
    void setReadModule(ModuleEntity module);
}
