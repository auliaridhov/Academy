package com.example.academy.data.source.local;

import androidx.lifecycle.LiveData;

import com.example.academy.data.CourseEntity;
import com.example.academy.data.CourseWithModule;
import com.example.academy.data.ModuleEntity;
import com.example.academy.data.source.local.room.AcademyDao;

import java.util.List;

public class LocalDataSource {

    private static LocalDataSource INSTANCE;
    private final AcademyDao mAcademyDao;

    private LocalDataSource(AcademyDao mAcademyDao) {
        this.mAcademyDao = mAcademyDao;
    }

    public static LocalDataSource getInstance(AcademyDao academyDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(academyDao);
        }
        return INSTANCE;
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return mAcademyDao.getCourses();
    }

    public LiveData<List<CourseEntity>> getBookmarkedCourses() {
        return mAcademyDao.getBookmarkedCourse();
    }

    public LiveData<CourseWithModule> getCourseWithModules(final String courseId) {
        return mAcademyDao.getCourseWithModuleById(courseId);
    }

    public LiveData<List<ModuleEntity>> getAllModulesByCourse(String courseId) {
        return mAcademyDao.getModulesByCourseId(courseId);
    }

    public void insertCourses(List<CourseEntity> courses) {
        mAcademyDao.insertCourses(courses);
    }

    public void insertModules(List<ModuleEntity> modules) {
        mAcademyDao.insertModules(modules);
    }

    public void setCourseBookmark(CourseEntity course, boolean newState) {
        course.setBookmarked(newState);
        mAcademyDao.updateCourse(course);
    }

    public LiveData<ModuleEntity> getModuleWithContent(String moduleId) {
        return mAcademyDao.getModuleById(moduleId);
    }

    public void updateContent(String content, String moduleId) {
        mAcademyDao.updateModuleByContent(content, moduleId);
    }

    public void setReadModule(final ModuleEntity module) {
        module.setRead(true);
        mAcademyDao.updateModule(module);
    }
}
