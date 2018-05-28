package main.sleepapp.controller;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import main.sleepapp.model.StudentModel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class StudentModelAndroidUnitTest {

    public static final String TEST_USERNAME = "1";
    public static final String TEST_PASSWORD = "123";
    private StudentModel studentModel;

    @Before
    public void setUp() {
        studentModel = new StudentModel();
    }

    @Test
    public void studenModel_ParcelableWriteRead() {
        studentModel.setStudent_id(TEST_USERNAME);
        studentModel.setPassword(TEST_PASSWORD);
        Parcel parcel = Parcel.obtain();
        studentModel.writeToParcel(parcel, studentModel.describeContents());
        parcel.setDataPosition(0);
        StudentModel createdFromParcel = studentModel.CREATOR.createFromParcel(parcel);
        assertThat(createdFromParcel.getStudent_id(), is(TEST_USERNAME));
        assertThat(createdFromParcel.getPassword(), is(TEST_PASSWORD));
        }
}