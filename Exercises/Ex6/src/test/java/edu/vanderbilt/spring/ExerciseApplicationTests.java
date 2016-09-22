package edu.vanderbilt.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.assertj.core.internal.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExerciseApplicationTests {

	@Autowired
	VideoService videoService;
	
	@Test
	public void contextLoads() {	
	}
	
	@Test
	public void videoServiceLoaded() {
		assertNotNull(videoService);
	}
	
	//Testing addVideo()
	@Test
	public void addVideoReturnsAListOfVideos() {
		Video video = new Video();
		Iterable<Video> videos = videoService.addVideo(video);
		assertNotNull(videos);
		assertTrue(videos.iterator().hasNext());
	}
	
	//Testing getVideo()
	@Test
	public void testGetVideo()
	{
		Video video = new Video();
		Iterable<Video> videos = videoService.addVideo(video);
		long id = video.getId();
		assertNotNull(videoService.getVideo(id));
	}
	
	//Testing getVideos()
	@Test
	public void testGetVideos()
	{
		Video v1 = new Video();
		v1.setName("test1");
		videoService.addVideo(v1);
		Iterable<Video> videos = videoService.getVideos();
		boolean present = false;
		Iterator<Video> it = videos.iterator();
		while(it.hasNext())
		{
			if(it.next().getName()=="test1")
				present=true;
		}
		assertTrue(present);
	}
	
	//Testing updateVideo()
	@Test
	public void testUpdateVideo()
	{
		Video video = new Video();
		videoService.addVideo(video);
		video.setName("testing_update");
		videoService.updateVideo(video.getId(), video);
		Video returned_video = videoService.getVideo(video.getId());
		assertEquals(returned_video.getName(), "testing_update");
	}
	
	//Testing deleteVideo()
	//Test must be run first so that no other videos exist in storage when testing
	@Test(expected=NoSuchElementException.class)
	public void testDeleteVideo()
	{
		Video video = new Video();
		videoService.addVideo(video);
		videoService.deleteVideo(video.getId());
		Iterator<Video> it = videoService.getVideos().iterator();
		it.next();
	}

}
