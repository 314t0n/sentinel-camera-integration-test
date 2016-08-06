package io.hajnal.david.sentinel_integration_test.settings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.hajnal.david.sentinel.settings.FileSystemConfigLoader;
import io.hajnal.david.sentinel.settings.ImagelogSettings;
import io.hajnal.david.sentinel.settings.MotionDetectSettings;
import io.hajnal.david.sentinel.settings.SystemConfig;

@RunWith(MockitoJUnitRunner.class)
public class FileSystemConfigLoaderTest {

	private FileSystemConfigLoader underTest;
	
	private SystemConfig systemConfig;
	private ImagelogSettings imagelogSettings;
	private MotionDetectSettings motionDetectSettings;

	@Before
	public void setUp() {
		underTest = new FileSystemConfigLoader();
		systemConfig = new SystemConfig();
		systemConfig.setCameraId(0);
		systemConfig.setDeviceId("test");
		systemConfig.setFps(5);
		systemConfig.setHost("localhost");
		systemConfig.setPort(3000);
		systemConfig.setStandalone(false);
		
		imagelogSettings = new ImagelogSettings();
		imagelogSettings.setIntervalInSeconds(1);
		imagelogSettings.setStatus(false);
		imagelogSettings.setStoreImageOnDevice(false);
		imagelogSettings.setStoreOnDeviceInDays(1);
		
		motionDetectSettings = new MotionDetectSettings();
		motionDetectSettings.setMogHistory(5);
		motionDetectSettings.setStatus(true);
		motionDetectSettings.setThreshold(5000);
		motionDetectSettings.setStoreImageOnDevice(true);
		motionDetectSettings.setStoreOnDeviceInDays(3);
		systemConfig.setMotionDetectSettings(motionDetectSettings);
		systemConfig.setImagelogSettings(imagelogSettings);
	}

	@Test
	public void savedAndLoadedConfigShouldBeTheSame() {
		// Given
		// When
		underTest.save(systemConfig);
		SystemConfig result = underTest.load();
		// Then
		Assert.assertEquals("cameraId", systemConfig.getCameraId(), result.getCameraId());
		Assert.assertEquals("deviceId", systemConfig.getDeviceId(), result.getDeviceId());
		Assert.assertEquals("fps", systemConfig.getFps(), result.getFps());
		Assert.assertEquals("host", systemConfig.getHost(), result.getHost());
		Assert.assertEquals("port", systemConfig.getPort(), result.getPort());
		Assert.assertEquals("standalone", systemConfig.isStandalone(), result.isStandalone());
		
		Assert.assertEquals("imglog.interval", systemConfig.getImagelogSettings().getIntervalInSeconds(), result.getImagelogSettings().getIntervalInSeconds());
		Assert.assertEquals("imglog.status", systemConfig.getImagelogSettings().isStatus(), result.getImagelogSettings().isStatus());
		Assert.assertEquals("imglog.storeimg", systemConfig.getImagelogSettings().isStoreImageOnDevice(), result.getImagelogSettings().isStoreImageOnDevice());
		Assert.assertEquals("imglog.storedays", systemConfig.getImagelogSettings().getStoreOnDeviceInDays(), result.getImagelogSettings().getStoreOnDeviceInDays());
		
		Assert.assertEquals("mdetect.moghistory", systemConfig.getMotionDetectSettings().getMogHistory(), result.getMotionDetectSettings().getMogHistory());
		Assert.assertEquals("mdetect.status", systemConfig.getMotionDetectSettings().isStatus(), result.getMotionDetectSettings().isStatus());
		Assert.assertEquals("mdetect.threshold", systemConfig.getMotionDetectSettings().getThreshold(), result.getMotionDetectSettings().getThreshold());
		Assert.assertEquals("mdetect.storeimg", systemConfig.getMotionDetectSettings().isStoreImageOnDevice(), result.getMotionDetectSettings().isStoreImageOnDevice());
		Assert.assertEquals("mdetect.storedays", systemConfig.getMotionDetectSettings().getStoreOnDeviceInDays(), result.getMotionDetectSettings().getStoreOnDeviceInDays());
	}

	@Test
	public void modifiedAnddLoadedConfigShouldBeTheSame() {
		// Given
		underTest.save(systemConfig);
		systemConfig = underTest.load();
		systemConfig.setCameraId(1);
		systemConfig.getImagelogSettings().setIntervalInSeconds(20);
		systemConfig.getMotionDetectSettings().setThreshold(6789);
		// When
		underTest.save(systemConfig);
		SystemConfig result = underTest.load();
		// Then
		Assert.assertEquals("cameraId", systemConfig.getCameraId(), result.getCameraId());
		Assert.assertEquals("deviceId", systemConfig.getDeviceId(), result.getDeviceId());
		Assert.assertEquals("fps", systemConfig.getFps(), result.getFps());
		Assert.assertEquals("host", systemConfig.getHost(), result.getHost());
		Assert.assertEquals("port", systemConfig.getPort(), result.getPort());
		Assert.assertEquals("standalone", systemConfig.isStandalone(), result.isStandalone());
		
		Assert.assertEquals("imglog.interval", systemConfig.getImagelogSettings().getIntervalInSeconds(), result.getImagelogSettings().getIntervalInSeconds());
		Assert.assertEquals("imglog.status", systemConfig.getImagelogSettings().isStatus(), result.getImagelogSettings().isStatus());
		Assert.assertEquals("imglog.storeimg", systemConfig.getImagelogSettings().isStoreImageOnDevice(), result.getImagelogSettings().isStoreImageOnDevice());
		Assert.assertEquals("imglog.storedays", systemConfig.getImagelogSettings().getStoreOnDeviceInDays(), result.getImagelogSettings().getStoreOnDeviceInDays());
		
		Assert.assertEquals("mdetect.moghistory", systemConfig.getMotionDetectSettings().getMogHistory(), result.getMotionDetectSettings().getMogHistory());
		Assert.assertEquals("mdetect.status", systemConfig.getMotionDetectSettings().isStatus(), result.getMotionDetectSettings().isStatus());
		Assert.assertEquals("mdetect.threshold", systemConfig.getMotionDetectSettings().getThreshold(), result.getMotionDetectSettings().getThreshold());
		Assert.assertEquals("mdetect.storeimg", systemConfig.getMotionDetectSettings().isStoreImageOnDevice(), result.getMotionDetectSettings().isStoreImageOnDevice());
		Assert.assertEquals("mdetect.storedays", systemConfig.getMotionDetectSettings().getStoreOnDeviceInDays(), result.getMotionDetectSettings().getStoreOnDeviceInDays());
	}

}
