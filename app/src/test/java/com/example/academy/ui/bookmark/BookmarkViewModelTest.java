package com.example.academy.ui.bookmark;

import com.example.academy.data.CourseEntity;
import com.example.academy.data.source.AcademyRepository;
import com.example.academy.utils.DataDummy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookmarkViewModelTest {
    private BookmarkViewModel viewModel;

    @Mock
    private AcademyRepository academyRepository;

    @Before
    public void setUp() {
        viewModel = new BookmarkViewModel(academyRepository);
    }

    @Test
    public void getBookmark() {
        when(academyRepository.getBookmarkedCourses()).thenReturn((ArrayList<CourseEntity>) DataDummy.generateDummyCourses());
        List<CourseEntity> courseEntities = viewModel.getBookmarks();
        verify(academyRepository).getBookmarkedCourses();
        assertNotNull(courseEntities);
        assertEquals(5, courseEntities.size());
    }
}