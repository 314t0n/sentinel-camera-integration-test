package io.hajnal.david.sentinel_integration_test.utils;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.springframework.test.annotation.Timed;

import io.hajnal.david.sentinel.util.FileStorage;
import io.hajnal.david.sentinel.util.OpenCVCameraImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestFileStorage {

	@Mock
	private FileStorage underTest;
	private OpenCVCameraImpl camera;

	@BeforeClass
	public static void setUpOnce() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Before
	public void setUp() {
		camera = new OpenCVCameraImpl(0);
		underTest = new FileStorage("");
	}

	@After
	public void tearDown() {
		try {
			camera.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveImageShouldSaveTheFrameToTheFileSystem() {
		// Given
		LocalDateTime timestamp = LocalDateTime.of(2011, 11, 11, 11, 11, 11, 11);
		Mat frame = camera.getFrame();
		// When
		underTest.saveImage(frame, timestamp);
		Mat result = underTest.readImage(timestamp);
		// Then
		Assert.assertEquals(frame.size(), result.size());
	}

}
