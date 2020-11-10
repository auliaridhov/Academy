package com.example.academy.ui.academy;

import androidx.lifecycle.ViewModel;

import com.example.academy.data.CourseEntity;
import com.example.academy.data.source.AcademyRepository;
import com.example.academy.utils.DataDummy;

import java.util.List;

public class AcademyViewModel extends ViewModel {

    private AcademyRepository academyRepository;
    public AcademyViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public List<CourseEntity> getCourses() {
        return academyRepository.getAllCourses();
    }
}