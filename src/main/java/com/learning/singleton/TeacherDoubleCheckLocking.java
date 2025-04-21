package com.learning.singleton;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file TeacherDoubleCheckLocking
 */
public class TeacherDoubleCheckLocking {

  private static volatile TeacherDoubleCheckLocking instance;

  private TeacherDoubleCheckLocking() {
  }

  public static TeacherDoubleCheckLocking getInstance() {
    if(instance == null) {   // 1
      synchronized (TeacherDoubleCheckLocking.class) {
        if(instance == null) {
          instance = new TeacherDoubleCheckLocking();  // 2
        }
      }
    }
    return instance;   //3
  }
  /**
  only one thread will enter the synchronized block and execute 2.
   assignment instruction involves three operations:
   1. allocate memory for the TeacherDoubleCheckLocking object
   2. initialized TeacherDoubleCheckLocking object
   3. assign the address to the instance variable

   JVM might reorder step 2 and step 3. So we might get an initialized object. The TeacherDoubleCheckLocking
   object is visible to all other threads because of the synchronized block. operation 1 is outside the synchronized block.
   Another thread might execute the null check while the first thread is still executing 2
   */
}
