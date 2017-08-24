package ing.gzq.service;

import ing.gzq.base.Result;
import ing.gzq.base.ResultCache;
import ing.gzq.dao.CourseDao;
import ing.gzq.model.Course;
import ing.gzq.model.Lesson;
import ing.gzq.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gzq on 17-7-20.
 */
@Service
public class CourseService {

    @Autowired
    CourseDao courseDao;


    public Result insertCourse(Course course) {
        try {
            courseDao.insertCourse(course);
            HashMap map = new HashMap<>();
            map.put("courseId", course.getId());
            return ResultCache.getDataOk(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultCache.getFailureDetail("插入课程失败");
        }
    }

    public Result getCourseByTeacherId(String teacherId) {
        List<Course> courseList = courseDao.getCourseByTeacherId(teacherId);
        return ResultCache.getDataOk(courseList);
    }

    public Result getCourseByStudentId(String studentId) {
        List<Course> courseList = courseDao.getCourseByStudentId(studentId);
        return ResultCache.getDataOk(courseList);
    }


//    public Result startNewLesson(Lesson lesson) {
//        try {
//            courseDao.insertLesson(lesson);
//            Map<String, Object> map = new HashMap<>();
//            map.put("lessonId", lesson.getId());
//            return ResultCache.getDataOk(map);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultCache.getFailureDetail(e.getMessage());
//        }
//    }

//    public Result endLesson(Long lessonId) {
//        try {
//            courseDao.updateLessonStateToZero(lessonId);
//            return ResultCache.OK;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultCache.getFailureDetail(e.getMessage());
//        }
//    }

    public Result search(String keyWord) {
        List<Course> courseList = courseDao.search("%" + keyWord + "%");
        return ResultCache.getDataOk(courseList);
    }

    public Result joinCourse(Long courseId) {
        try {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            courseDao.insertStudentToCourse(courseId,user.getUsername());
            return ResultCache.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultCache.getFailureDetail(e.getMessage());
        }
    }
}
